package io.github.leniumc.noteschool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 陈涵宇 on 2017/8/16.
 */

class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {
    private Context context;
    private List<AttachmentData> dataList;

    public AttachmentAdapter(Context context, List<AttachmentData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.simple_attachment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameTextView.setText(dataList.get(position).getAttachmentName());
        holder.sizeTextView.setText(dataList.get(position).getAttachmentSize());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, sizeTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.attachment_name);
            sizeTextView = itemView.findViewById(R.id.attachment_size);
        }
    }
}
