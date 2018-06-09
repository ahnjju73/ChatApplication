package com.example.joohonga.chatapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joohonga.chatapplication.Friend;
import com.example.joohonga.chatapplication.R;
import com.example.joohonga.chatapplication.adapter.FriendAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FriendsListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FriendAdapter friendAdapter;
    List<Friend> friendList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends_list,container,false);

        recyclerView = v.findViewById(R.id.friend_list);
        recyclerView.setHasFixedSize(true);

        friendAdapter = new FriendAdapter(friendList, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendAdapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot temp: dataSnapshot.getChildren()){

                    Friend friend = temp.getValue(Friend.class);


                    friendList.add(friend);
                    friendAdapter.notifyItemChanged(friendList.size()-1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }
}
