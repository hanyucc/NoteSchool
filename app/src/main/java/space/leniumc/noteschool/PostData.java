package space.leniumc.noteschool;

import android.media.Image;

/**
 * Created by 陈涵宇 on 2017/8/16.
 */

class PostData {
    private String avatarImageUrl, userName, userGrade, postDescription;
    private String[] attachmentUrls;
    private int attachmentCount;

    public PostData(String avatarImageUrl, String userName, String userGrade, String postDescription, String[] attachmentUrls, int attachmentCount) {
        this.avatarImageUrl = avatarImageUrl;
        this.userName = userName;
        this.userGrade = userGrade;
        this.postDescription = postDescription;
        this.attachmentUrls = attachmentUrls;
        this.attachmentCount = attachmentCount;
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
}
