package edu.neu.ccs.wellness.adcaregiverapp.network.services.model;

import android.support.annotation.Nullable;

public class Member {
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member(@Nullable String name) {
        this.name = name;
    }

    public Member() {
    }

    @Nullable
    private String name;

    @Nullable
    public String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
