package org.commcare.commcaresupportlibrary.identity.model;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public class Registration implements Parcelable {

    private String guid;

    public Registration(String guid) {
        this.guid = guid;
    }

    protected Registration(Parcel in) {
        guid = in.readString();
    }

    public static final Creator<Registration> CREATOR = new Creator<Registration>() {
        @Override
        public Registration createFromParcel(Parcel in) {
            return new Registration(in);
        }

        @Override
        public Registration[] newArray(int size) {
            return new Registration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(guid);
    }

    public String getGuid() {
        return guid;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) o;
        if (!guid.equals(other.guid)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = guid.hashCode();
        return hash;
    }
}
