package com.example.joohonga.chatapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Chat> mDataset ;   //all Messages
    private String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();    //CurrentUser Email
    private final int RIGHT_BUBBLE =1;
    private final int LEFT_BUBBLE = 2;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_view);
            mTextView = itemView.findViewById(R.id.textview);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Chat> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        if(currentEmail.equals(mDataset.get(position).getEmail()))
            return RIGHT_BUBBLE;
        else
            return LEFT_BUBBLE;
        }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v;

        if(viewType == RIGHT_BUBBLE) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.left_item_list, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);
        }
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getMessage());
        //holder.mImageView.setImageBitmap();     //bitmap resource from db
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
