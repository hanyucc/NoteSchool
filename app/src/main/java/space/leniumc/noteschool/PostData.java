package space.leniumc.noteschool;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 陈涵宇 on 2017/8/16.
 */

class PostData implements Parcelable {
    private String avatarImageUrl, userName, userGrade, postDescription;
    private String[] attachmentUrls;
    private int attachmentCount, postId;

    public PostData(String avatarImageUrl, String userName, String userGrade, String postDescription, String[] attachmentUrls, int attachmentCount, int postId) {
        this.avatarImageUrl = avatarImageUrl;
        this.userName = userName;
        this.userGrade = userGrade;
        this.postDescription = postDescription;
        this.attachmentUrls = attachmentUrls;
        this.attachmentCount = attachmentCount;
        this.postId = postId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarImageUrl);
        dest.writeString(userName);
        dest.writeString(userGrade);
        dest.writeString(postDescription);
        dest.writeStringArray(attachmentUrls);
        dest.writeInt(attachmentCount);
        dest.writeInt(postId);
    }

    public static final Parcelable.Creator<PostData> CREATOR = new Parcelable.Creator<PostData>() {
        public PostData createFromParcel(Parcel in) {
            return new PostData(in);
        }

        public PostData[] newArray(int size) {
            return new PostData[size];
        }
    };

    private PostData(Parcel in) {
        avatarImageUrl = in.readString();
        userName = in.readString();
        userGrade = in.readString();
        postDescription = in.readString();
        attachmentUrls = in.createStringArray();
        attachmentCount = in.readInt();
        postId = in.readInt();
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

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String[] getAttachmentUrls() {
        return attachmentUrls;
    }

    public void setAttachmentUrls(String[] attachmentUrls) {
        this.attachmentUrls = attachmentUrls;
    }

    public int getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(int attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
