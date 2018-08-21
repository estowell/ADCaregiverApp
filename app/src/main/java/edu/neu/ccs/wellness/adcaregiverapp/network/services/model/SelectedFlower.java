package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SelectedFlower implements Parcelable {

    String name;
    int stage;

    public SelectedFlower() {
    }

    public SelectedFlower(String name, int stage) {
        this.name = name;
        this.stage = stage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.stage);
    }

    protected SelectedFlower(Parcel in) {
        this.name = in.readString();
        this.stage = in.readInt();
    }

    public static final Creator<SelectedFlower> CREATOR = new Creator<SelectedFlower>() {
        @Override
        public SelectedFlower createFromParcel(Parcel source) {
            return new SelectedFlower(source);
        }

        @Override
        public SelectedFlower[] newArray(int size) {
            return new SelectedFlower[size];
        }
    };
}
