package com.example.demoapplication.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.support.v4.app.INotificationSideChannel;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapplication.Models.Users;
import com.example.demoapplication.R;
import com.example.demoapplication.databinding.FragmentEnrollBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EnrollFragment extends Fragment {



    private Uri imageUri;

    public EnrollFragment() {
        // Required empty public constructor
    }

   ProgressDialog dialog;
    FragmentEnrollBinding binding;
    FirebaseDatabase database;
    private  FirebaseStorage storage;
    private StorageReference storageReference;

    EditText date;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEnrollBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        date=binding.etDob;


//        TextWatcher tw = new TextWatcher() {
//
//            private String current = "";
//            private String ddmmyyyy = "DDMMYYYY";
//            private Calendar cal = Calendar.getInstance();
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (!charSequence.toString().equals(current)) {
//                    String clean = charSequence.toString().replaceAll("[^\\d.]|\\.", "");
//                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");
//
//                    int cl = clean.length();
//                    int sel = cl;
//                    for (int j = 2; j <= cl && j < 6; j += 2) {
//                        sel++;
//                    }
//                    //Fix for pressing delete next to a forward slash
//                    if (clean.equals(cleanC)) sel--;
//
//                    if (clean.length() < 8) {
//                        clean = clean + ddmmyyyy.substring(clean.length());
//                    } else {
//                        //This part makes sure that when we finish entering numbers
//                        //the date is correct, fixing it otherwise
//                        int day = Integer.parseInt(clean.substring(0, 2));
//                        int mon = Integer.parseInt(clean.substring(2, 4));
//                        int year = Integer.parseInt(clean.substring(4, 8));
//
//                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
//                        cal.set(Calendar.MONTH, mon - 1);
//                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
//                        cal.set(Calendar.YEAR, year);
//                        // ^ first set year for the line below to work correctly
//                        //with leap years - otherwise, date e.g. 29/02/2012
//                        //would be automatically corrected to 28/02/2012
//
//                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
//                        clean = String.format("%02d%02d%02d", day, mon, year);
//                    }
//
//                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
//                            clean.substring(2, 4),
//                            clean.substring(4, 8));
//
//                    sel = sel < 0 ? 0 : sel;
//                    current = clean;
//                    date.setText(current);
//                    date.setSelection(sel < current.length() ? sel : current.length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        };



        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Adding User");
        dialog.setMessage("user is being added...");
        binding.etProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });







        binding.adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.show();

                final String firstname = binding.etFirstname.getText().toString().trim();
                final String secondname= binding.etSecondname.getText().toString();
                String dob = binding.etDob.getText().toString();
                String gender = binding.etGender.getText().toString().toLowerCase();
                String country = binding.etCountry.getText().toString();
                String state = binding.etState.getText().toString();
                String hometown = binding.etHometown.getText().toString();
                String phn = binding.etPhn.getText().toString();
                String telphn = binding.etTelphn.getText().toString();





                String date = binding.etDob.getText().toString();
                String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(date);
                boolean bool = matcher.matches();
                if(!bool){
                    dialog.dismiss();
                    binding.etDob.setError("enter valid format dd/mm/yyyy");

                }

                String regx = "^(?:male|Male|female|Female)$";
                Pattern pattern1 =Pattern.compile(regx);
                Matcher matcher1 = pattern1.matcher(gender);
                boolean bool1=matcher1.matches();
                if(!bool1){
                    dialog.dismiss();
                    binding.etGender.setError("gender type not valid");
                }


                String regx1 ="[A-Z][a-z]*";
             //   String regx2 = "[A-Z][a-z]*";
                Pattern pattern2=Pattern.compile(regx1);
             //   Pattern pattern3=Pattern.compile(regx2);
                Matcher matcher2=pattern2.matcher(firstname);
              //  Matcher matcher3=pattern3.matcher(secondname);
                boolean bool2=matcher2.matches();
               // boolean bool3=matcher3.matches();

                if(!bool2){
                    dialog.dismiss();
                    binding.etFirstname.setError("firstname does not met naming requirements");
                }
                if(TextUtils.isEmpty(firstname)){
                    dialog.dismiss();
                    binding.etFirstname.setError("enter your first name");
                }
                if(TextUtils.isEmpty(secondname)){
                    dialog.dismiss();
                    binding.etSecondname.setError("enter your second name");
                }
                if(TextUtils.isEmpty(dob)){
                    dialog.dismiss();
                    binding.etDob.setError("enter your date-of-birth");
                }
                if(TextUtils.isEmpty(gender)){
                    dialog.dismiss();
                    binding.etGender.setError("enter your gender");
                }



                if(TextUtils.isEmpty(country)){
                    dialog.dismiss();
                    binding.etCountry.setError("enter country name");
                }


                if(TextUtils.isEmpty(state)){
                    dialog.dismiss();
                    binding.etState.setError("enter your state name");
                }
                if(TextUtils.isEmpty(hometown)){
                    dialog.dismiss();
                    binding.etHometown.setError("enter your home town");
                }
                if(TextUtils.isEmpty(phn)){
                    dialog.dismiss();
                    binding.etPhn.setError("enter your phone number");
                }
                if(TextUtils.isEmpty(telphn)){
                    dialog.dismiss();
                    binding.etTelphn.setError("enter your telephone number");
                }
                if(TextUtils.getTrimmedLength(phn)<10){
                    dialog.dismiss();
                    binding.etPhn.setError("enter valid phone number");

                }
                if(TextUtils.getTrimmedLength(telphn)<10){
                    dialog.dismiss();
                    binding.etTelphn.setError("enter valid phone number");
                }
                if(phn.equals(telphn)){
                    dialog.dismiss();
                    binding.etTelphn.setError("telephone number should be different from phone");
                }

                if(imageUri==null){
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "please upload  your image", Toast.LENGTH_SHORT).show();
                }


                else{

                    uploadToFirebase(imageUri);
                }


            }


        });



        return binding.getRoot();
    }



    private  void uploadToFirebase(Uri uri){

       // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        final String randomKey = UUID.randomUUID().toString();
        final StorageReference riversRef = storageReference.child("images/"+randomKey);
        riversRef.putFile(imageUri).

                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        final String id = UUID.randomUUID().toString();
                        Long tsLong = System.currentTimeMillis()/1000;
                        String ts = tsLong.toString();

                        Users users = new Users(ts,uri.toString(),binding.etFirstname.getText().toString(),
                                binding.etSecondname.getText().toString(),
                                binding.etDob.getText().toString(),
                                binding.etGender.getText().toString(),
                                binding.etCountry.getText().toString(),
                                binding.etState.getText().toString(),
                                binding.etHometown.getText().toString(),
                                binding.etPhn.getText().toString(),
                                binding.etTelphn.getText().toString());




                        database.getReference().child("Users").child(ts).setValue(users);


                        dialog.dismiss();
                        Toast.makeText(getContext(), "user added successfully", Toast.LENGTH_SHORT).show();
                        binding.etFirstname.getText().clear();
                        binding.etSecondname.getText().clear();
                                binding.etDob.getText().clear();
                                binding.etGender.getText().clear();
                                binding.etCountry.getText().clear();
                                binding.etState.getText().clear();
                                binding.etHometown.getText().clear();
                                binding.etPhn.getText().clear();
                                binding.etTelphn.getText().clear();



                    }
                });

                // ...
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                  double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                dialog.setMessage("Percentage:   "+ (int)progressPercent+"%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(getContext(), "failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode ==Activity.RESULT_OK && data!=null &&data.getData()!=null){
            imageUri =data.getData();
            binding.etProfilePic.setImageURI(imageUri);

        }
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}