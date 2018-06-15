package com.example.joohonga.chatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joohonga.chatapplication.adapter.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    private String email;                   //current User Email

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String counterpartUid;

    List<Chat> dataSet = new ArrayList<>();  //
    private EditText editText;
    private Button sendButton;
    private FirebaseDatabase database;
    private Button finishBtn;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Intent intent = getIntent();
        counterpartUid = intent.getStringExtra("key");
        key = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("userid", counterpartUid);

        database = FirebaseDatabase.getInstance();

        //take current user email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            email = user.getEmail();


        finishBtn = findViewById(R.id.end_btn);
        recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.edtext);       //type message
        sendButton = findViewById(R.id.send_btn);    //send Button


        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });     //finish btn
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = editText.getText().toString();
                if (msg.isEmpty() || msg.equals("")) {
                    Toast.makeText(ChattingActivity.this, "Type Please", Toast.LENGTH_SHORT).show();
                } else {
                    

                    if (database.getReference("message") == null) {
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s");
                        DatabaseReference dbRef = database.getReference("message").child(key + counterpartUid)
                                .child(dateFormatter.format(new Date()));


                        Map<String, String> message = new HashMap<>();
                        message.put("key", key);
                        message.put("email", email);
                        message.put("message", msg);
                        dbRef.setValue(message);
                        editText.setText("");
                    } else {
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s");
                        DatabaseReference dbRef = database.getReference("message").child(counterpartUid + key)
                                .child(dateFormatter.format(new Date()));


                        Map<String, String> message = new HashMap<>();
                        message.put("key", key);
                        message.put("email", email);
                        message.put("message", msg);
                        dbRef.setValue(message);
                        editText.setText("");
                    }
                }
            }
        });

        DatabaseReference dbRef = database.getReference("message");
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals(counterpartUid + key)) {
                    Log.d("dataSnapShotCheck", dataSnapshot.getKey());

                    for (DataSnapshot temp : dataSnapshot.getChildren()) {
                        Chat chat = temp.getValue(Chat.class);
                        // [START_EXCLUDE]
                        // Update RecyclerView
                        dataSet.add(chat);
                        adapter.notifyItemInserted(dataSet.size() - 1);
                    }

                } else if (dataSnapshot.getKey().equals(key + counterpartUid)) {
                    Log.d("dataSnapShotCheck", dataSnapshot.getKey());


                    for (DataSnapshot temp : dataSnapshot.getChildren()) {
                        Chat chat = temp.getValue(Chat.class);
                        // [START_EXCLUDE]
                        // Update RecyclerView
                        dataSet.add(chat);
                        adapter.notifyItemInserted(dataSet.size() - 1);
                    }
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
