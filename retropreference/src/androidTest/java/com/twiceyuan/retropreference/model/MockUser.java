package com.twiceyuan.retropreference.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by twiceYuan on 09/02/2017.
 *
 * 模拟用户信息
 */
public class MockUser implements Parcelable {

    public String username;
    public String password;
    public int age;
    public float score;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeInt(this.age);
        dest.writeFloat(this.score);
    }

    public MockUser() {
    }

    protected MockUser(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.age = in.readInt();
        this.score = in.readFloat();
    }

    public static final Parcelable.Creator<MockUser> CREATOR = new Parcelable.Creator<MockUser>() {
        @Override
        public MockUser createFromParcel(Parcel source) {
            return new MockUser(source);
        }

        @Override
        public MockUser[] newArray(int size) {
            return new MockUser[size];
        }
    };
}
