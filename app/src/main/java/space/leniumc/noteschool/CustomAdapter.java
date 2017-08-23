package space.leniumc.noteschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

/**
 * Created by 陈涵宇 on 2017/8/16.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private Context context;
    private List<PostData> dataList;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(holder.avatarView, dataList.get(position).getAvatarImageUrl(),
                dataList.get(position).getUserName());

        holder.nameTextView.setText(dataList.get(position).getUserName());
        holder.gradeTextView.setText(dataList.get(position).getUserGrade());
        holder.descriptionTextView.setText(dataList.get(position).getPostDescription());
        holder.attachmentCountTextView.setText(String.valueOf(dataList.get(position).getAttachmentCount()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AvatarView avatarView;
        public TextView nameTextView, gradeTextView, descriptionTextView, attachmentCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarView = (AvatarView) itemView.findViewById(R.id.avatar_view_post);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            gradeTextView = (TextView) itemView.findViewById(R.id.grade_text_view);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description_text_view);
            attachmentCountTextView = (TextView) itemView.
                    findViewById(R.id.attachment_count_text_view);
        }
    }
}
