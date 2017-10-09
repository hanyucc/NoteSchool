package io.github.leniumc.noteschool;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 陈涵宇 on 2017/9/23.
 */

class AttachmentData implements Parcelable {
    private String attachmentName;
    private String attachmentSize;

    public AttachmentData(String attachmentName, String attachmentSize) {
        this.attachmentName = attachmentName;
        this.attachmentSize = attachmentSize;
    }

    private AttachmentData(Parcel in) {
        attachmentName = in.readString();
        attachmentSize = in.readString();
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentSize() {
        return attachmentSize;
    }

    public void setAttachmentSize(String attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attachmentName);
        dest.writeString(attachmentSize);
    }

    public static final Parcelable.Creator<AttachmentData> CREATOR = new Parcelable.Creator<AttachmentData>() {
        public AttachmentData createFromParcel(Parcel in) {
            return new AttachmentData(in);
        }

        public AttachmentData[] newArray(int size) {
            return new AttachmentData[size];
        }
    };
}
