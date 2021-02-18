package com.example.demoapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demoapplication.Adapters.UsersAdapter;
import com.example.demoapplication.Models.Users;
import com.example.demoapplication.R;
import com.example.demoapplication.databinding.FragmentUsersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class UsersFragment extends Fragment {


    public UsersFragment() {
        // Required empty public constructor
    }
FragmentUsersBinding binding;
    ArrayList<Users> list =new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_users, container, false);
        binding = FragmentUsersBinding.inflate(inflater,container,false);
        final UsersAdapter adapter = new UsersAdapter(list);
        binding.userrecyclerview.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.userrecyclerview.setLayoutManager(layoutManager);



        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    list.add(users);
                }

                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        adapter.setOnItemClickListener(new UsersAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "item clicked at "+(position+1)+" postion", Toast.LENGTH_SHORT).show();
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onDeleteItem(int position) {

                list.remove(position);
                Toast.makeText(getContext(), "item removed at "+(position+1)+" postion", Toast.LENGTH_SHORT).show();
                adapter.notifyItemRemoved(position);
            }
        });





        return binding.getRoot();
    }
}