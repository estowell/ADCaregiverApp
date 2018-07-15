package edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

public class Activities implements Comparable<Activities>, Parcelable {

    private Date date;

    private int steps;


    public Activities(Date date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.steps);
    }

    protected Activities(Parcel in) {
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.steps = in.readInt();
    }

    public static final Creator<Activities> CREATOR = new Creator<Activities>() {
        @Override
        public Activities createFromParcel(Parcel source) {
            return new Activities(source);
        }

        @Override
        public Activities[] newArray(int size) {
            return new Activities[size];
        }
    };

    @Override
    public int compareTo(@NonNull Activities o) {
        return getDate().compareTo(o.getDate());
    }
}
