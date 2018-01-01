package io.github.leniumc.noteschool;

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
 * Created by 陈涵宇 on 2017/12/13.
 */

class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private Context context;
    private List<RankingData> dataList;

    public RankingAdapter(Context context, List<RankingData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ranking_strip, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.rankingTextView.setText(String.valueOf(dataList.get(position).getUserRanking()));
        holder.nameTextView.setText(dataList.get(position).getUserName());
        holder.pointTextView.setText(String.valueOf(dataList.get(position).getUserPoint()));
        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(holder.avatarView, dataList.get(position).getAvatarImageUrl(),
                dataList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AvatarView avatarView;
        public TextView nameTextView, pointTextView, rankingTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar_view_rank);
            nameTextView = itemView.findViewById(R.id.user_name_rank);
            pointTextView = itemView.findViewById(R.id.point_rank);
            rankingTextView = itemView.findViewById(R.id.ranking_text);
        }
    }
}
