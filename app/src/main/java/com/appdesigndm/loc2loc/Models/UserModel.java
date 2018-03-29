package com.appdesigndm.loc2loc.Models;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {

    private String id;
    private String name;
    private String email;
    private Uri photo;

    public UserModel() {
    }

    public UserModel(String id, String name, String email, Uri photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    protected UserModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        photo = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeParcelable(photo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo=" + photo +
                '}';
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}