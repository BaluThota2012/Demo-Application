package com.example.demoapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapplication.Models.Users;
import com.example.demoapplication.R;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    ArrayList<Users> list ;
    private OnItemClickListner mListener;
   // ArrayList<MainModel> list;
    // Context context;

    public interface OnItemClickListner{
        void onItemClick(int position);
        void onDeleteItem(int position);

    }

    public void setOnItemClickListener(OnItemClickListner listener){
        mListener=listener;
    }
  //  Context context;

    public UsersAdapter(ArrayList<Users> list) {
        this.list = list;
        //this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_user,parent,false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Users users = list.get(position);

        String ageh=getAge(users.getDob());

        Picasso.get().load(users.getProficPic()).placeholder(R.drawable.ic_user).into(holder.image);
       holder.username.setText(users.getFirstname()+" "+users.getLastname());
        holder.gender.setText(users.getGender());


        holder.age.setText(ageh);
        holder.hometown.setText(users.getHometown());



    }

    private String getAge(String dob) {

        String[] age =dob.split("/");
        Calendar dob1 = Calendar.getInstance();
        Calendar today = Calendar.getInstance();


        String day = dob.substring(0,2);
        String month = dob.substring(3,5);
        String year = dob.substring(6,10);


        int year1 = Integer.parseInt(year);
        int month1 = Integer.parseInt(month);
        int day1 = Integer.parseInt(day);

        dob1.set(year1,month1,day1);

        int age1= today.get(Calendar.YEAR) - dob1.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob1.get(Calendar.DAY_OF_YEAR)){
            age1--;
        }

        Integer ageInt = new Integer(age1);
        String ageS = ageInt.toString();


        return ageS;
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image,bin;
        TextView username,gender,age,hometown;
        public ViewHolder(@NonNull View itemView, final OnItemClickListner mListener) {
            super(itemView);
            image=itemView.findViewById(R.id.userprofile);
            bin =itemView.findViewById(R.id.bin);
            username =itemView.findViewById(R.id.username);
            gender = itemView.findViewById(R.id.user_gender);
            age =itemView.findViewById(R.id.user_age);
            hometown =itemView.findViewById(R.id.user_hometown);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null){
                        int position= getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }

                }
            });

            bin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mListener!=null){
                        int position= getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            mListener.onDeleteItem(position);
                        }
                    }
                }
            });







        }
    }
}
