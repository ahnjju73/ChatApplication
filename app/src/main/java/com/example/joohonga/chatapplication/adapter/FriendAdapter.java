package com.example.joohonga.chatapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joohonga.chatapplication.ChattingActivity;
import com.example.joohonga.chatapplication.Friend;
import com.example.joohonga.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> mDataset ;   //all Messages
    Context mContext;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView userTv;
        public ImageView userIv;
        public Button chatButton;


        public ViewHolder(View itemView) {
            super(itemView);
            userIv = itemView.findViewById(R.id.iv_user);
            userTv = itemView.findViewById(R.id.tv_user);
            chatButton = itemView.findViewById(R.id.chat_btn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendAdapter(List<Friend> myDataset, Context context) {
        mContext = context;
        mDataset = myDataset;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_friends,parent,true);



        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.get().load(mDataset.get(position).getPhoto()).into(holder.userIv);
        holder.userTv.setText(mDataset.get(position).getEmail());
        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(mContext, ChattingActivity.class);
                 mContext.startActivity(intent);
            }
        });


    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
