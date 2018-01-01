package io.github.leniumc.noteschool;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 陈涵宇 on 2017/12/13.
 */

class RankingData implements Parcelable {
    private String avatarImageUrl, userName;
    private int userPoint, userRanking;

    public RankingData(String avatarImageUrl, String userName, int userPoint, int userRanking) {
        this.avatarImageUrl = avatarImageUrl;
        this.userName = userName;
        this.userPoint = userPoint;
        this.userRanking = userRanking;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarImageUrl);
        dest.writeString(userName);
        dest.writeInt(userPoint);
        dest.writeInt(userRanking);
    }

    public static final Creator<RankingData> CREATOR = new Creator<RankingData>() {
        public RankingData createFromParcel(Parcel in) {
            return new RankingData(in);
        }
        public RankingData[] newArray(int size) {
            return new RankingData[size];
        }
    };

    private RankingData(Parcel in) {
        avatarImageUrl = in.readString();
        userName = in.readString();
        userPoint = in.readInt();
        userRanking = in.readInt();
    }

    public String getAvatarImageUrl() {
        return avatarImageUrl;
    }

    public void setAvatarImageUrl(String avatarImageUrl) {
        this.avatarImageUrl = avatarImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getUserRanking() {
        return userRanking;
    }

    public void setUserRanking(int userRanking) {
        this.userRanking = userRanking;
    }

}
