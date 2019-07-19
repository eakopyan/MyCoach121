package com.thuasnelab.mycoach121;


import android.os.Parcel;
import android.os.Parcelable;

public class Session implements Parcelable {

    private String date;
    private String sport;
    private String title;
    private double distance;
    private int fc;
    private String duree;

    private int energy;
    private long id;


    public Session(String date, String sport, String title, double distance, int fc, String duree) {
        this.date = date;
        this.sport = sport;
        this.title = title;
        this.distance = distance;
        this.fc = fc;
        this.duree = duree;

        this.energy = 0;
        //this.id = 0;
    }

    protected Session(Parcel in) {
        date = in.readString();
        sport = in.readString();
        title = in.readString();
        distance = in.readDouble();
        fc = in.readInt();
        duree = in.readString();
        energy = in.readInt();
        id = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(sport);
        dest.writeString(title);
        dest.writeDouble(distance);
        dest.writeInt(fc);
        dest.writeString(duree);
        dest.writeInt(energy);
        dest.writeLong(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getSport() {
        return sport;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return this.id;
    }

    public double getDistance() {
        return distance;
    }

    public int getFc() {
        return fc;
    }

    public String getDuree() {
        return duree;
    }

    public int getEnergy() {
        return energy;
    }

    public int getImageId() {
        switch(this.sport){
            case "roller_hockey": return R.drawable.roller_hockey_2;
            case "roller_vitesse": return R.drawable.roller_vitesse;
            case "velo_route": return R.drawable.velo_route;
            case "danse": return R.drawable.hip_hop;
            default: return -1;
        }
    }

    public void setId(long position) {
        this.id = position;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
