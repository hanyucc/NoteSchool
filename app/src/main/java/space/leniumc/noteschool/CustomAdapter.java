package space.leniumc.noteschool;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.util.List;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

/**
 * Created by 陈涵宇 on 2017/8/16.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<PostData> dataList;
    private int[] favPosts, upvotePosts;

    public CustomAdapter(Context context, List<PostData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_post, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(holder.avatarView, dataList.get(position).getAvatarImageUrl(),
                dataList.get(position).getUserName());

        holder.nameTextView.setText(dataList.get(position).getUserName());
        holder.gradeTextView.setText(dataList.get(position).getUserGrade());
        holder.descriptionTextView.setText(dataList.get(position).getPostDescription());
        holder.attachmentCountTextView.setText(String.valueOf(dataList.get(position).getAttachmentCount()));

        holder.favButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                int postId = dataList.get(holder.getAdapterPosition()).getPostId();
                // TODO: add user to fav in database and add post to user fav
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                int postId = dataList.get(holder.getAdapterPosition()).getPostId();
                // TODO: remove user from fav in database and remove post from user fav
            }
        });
        holder.upvoteButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                int postId = dataList.get(holder.getAdapterPosition()).getPostId();
                // TODO: add user to up in database
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                int postId = dataList.get(holder.getAdapterPosition()).getPostId();
                // TODO: remove user from up in database
            }
        });
        holder.postCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                PostData postData = dataList.get(holder.getAdapterPosition());
                intent.putExtra("everything", postData);
                context.startActivity(intent);
            }
        });

        // TODO: get fav and upvote posts from database
        favPosts = new int[0];
        upvotePosts = new int[0];
        int currentId = dataList.get(position).getPostId();
        for (int favPost: favPosts) {
            if (favPost == currentId) {
                holder.favButton.isLiked();
            }
        }
        for (int upvotePost: upvotePosts) {
            if (upvotePost == currentId) {
                holder.upvoteButton.isLiked();
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AvatarView avatarView;
        public TextView nameTextView, gradeTextView, descriptionTextView, attachmentCountTextView;
        public LikeButton favButton, upvoteButton;
        public CardView postCard;

        public ViewHolder(final View itemView) {
            super(itemView);
            avatarView = (AvatarView) itemView.findViewById(R.id.avatar_view_post);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            gradeTextView = (TextView) itemView.findViewById(R.id.grade_text_view);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description_text_view);
            attachmentCountTextView = (TextView) itemView.
                    findViewById(R.id.attachment_count_text_view);
            favButton = (LikeButton) itemView.findViewById(R.id.fav_button);
            upvoteButton = (LikeButton) itemView.findViewById(R.id.upvote_button);
            postCard = (CardView) itemView.findViewById(R.id.card_post);
        }
    }
}
