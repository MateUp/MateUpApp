package com.example.mateup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    public Context mContext;
    public ArrayList<UsersPosts> arrayList;

    public PostAdapter(Context context,ArrayList<UsersPosts> postList){
        mContext = context;
        arrayList = postList;

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.all_posts_layout,parent,false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {
        UsersPosts curentPost = arrayList.get(position);

        String imageUrl = curentPost.getImgUrl();
        String postDescription = curentPost.getDescription();
        String postDate = curentPost.getDate();
        String firstName = curentPost.getUserName();
        String lastName = curentPost.getUserLastName();




        if (imageUrl != null && postDescription != null && postDate != null && firstName !=null && lastName!=null  ){
        postViewHolder.postDescription.setText(postDescription);
        Picasso.get().load(imageUrl).fit().centerInside().into(postViewHolder.postImage);
        postViewHolder.dateOfPost.setText(postDate);
        postViewHolder.nameOfUser.setText(firstName+" "+lastName);}






    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        public ImageView postImage;
        public TextView postDescription;
        public TextView dateOfPost;
        public TextView nameOfUser;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.post_image);
            postDescription = itemView.findViewById(R.id.post_description);
            dateOfPost = itemView.findViewById(R.id.post_date);
            nameOfUser = itemView.findViewById(R.id.post_user_name);


        }
    }
}
