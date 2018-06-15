package com.example.joohonga.chatapplication.fragment;

import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joohonga.chatapplication.User;
import com.example.joohonga.chatapplication.R;
import com.example.joohonga.chatapplication.adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
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

    UserAdapter userAdapter;
    List<User> userList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;
    String currentUid;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends_list,container,false);
        Log.d("FriendFragment","test");
        currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot temp: dataSnapshot.getChildren()){
                    User user = temp.getValue(User.class);
                    if(!user.getUid().equals(currentUid)) {
                        Log.d("FriendFragmentUserTest", user.toString());
                        userList.add(user);
                        userAdapter.notifyItemChanged(userList.size() - 1);
                        Log.d("FriendFragment", String.valueOf(userAdapter.getItemCount()));
                    }

                }
                recyclerView.setAdapter(userAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView = v.findViewById(R.id.friend_list);
        recyclerView.setHasFixedSize(true);
        Log.d("FriendFragment",String.valueOf(userList.size()));

        userAdapter = new UserAdapter(userList, getActivity());
        Log.d("FriendFragment",String.valueOf(userAdapter.getItemCount()));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        return v;
    }
}
