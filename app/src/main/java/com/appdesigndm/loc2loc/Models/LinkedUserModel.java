package com.appdesigndm.loc2loc.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class LinkedUserModel implements Parcelable {

    private String id;
    private String email;

    private String idLinkedUser;
    private String emailLinkeduser;


    public LinkedUserModel(String id, String email, String idLinkedUser, String emailLinkeduser) {
        this.id = id;
        this.email = email;
        this.idLinkedUser = idLinkedUser;
        this.emailLinkeduser = emailLinkeduser;
    }

    protected LinkedUserModel(Parcel in) {
        id = in.readString();
        email = in.readString();
        idLinkedUser = in.readString();
        emailLinkeduser = in.readString();
    }

    public static final Creator<LinkedUserModel> CREATOR = new Creator<LinkedUserModel>() {
        @Override
        public LinkedUserModel createFromParcel(Parcel in) {
            return new LinkedUserModel(in);
        }

        @Override
        public LinkedUserModel[] newArray(int size) {
            return new LinkedUserModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdLinkedUser() {
        return idLinkedUser;
    }

    public void setIdLinkedUser(String idLinkedUser) {
        this.idLinkedUser = idLinkedUser;
    }

    public String getEmailLinkeduser() {
        return emailLinkeduser;
    }

    public void setEmailLinkeduser(String emailLinkeduser) {
        this.emailLinkeduser = emailLinkeduser;
    }

    @Override
    public String toString() {
        return "LinkedUserModel{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", idLinkedUser='" + idLinkedUser + '\'' +
                ", emailLinkeduser='" + emailLinkeduser + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(idLinkedUser);
        dest.writeString(emailLinkeduser);
    }
}
