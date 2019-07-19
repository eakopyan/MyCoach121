package com.thuasnelab.mycoach121;

public class Sport {

    private int id;
    private String title;
    private int nbSessions;

    public Sport(String title, int nbSessions) {
        this.id = 0;
        this.title = title;
        this.nbSessions = nbSessions;
    }

    public String getTitle() {
        return this.title;
    }

    public int getCount() {
        return this.nbSessions;
    }

    public int getImageId() {
        switch(this.title){
            case "roller_hockey": return R.drawable.roller_hockey_2;
            case "roller_vitesse": return R.drawable.roller_vitesse;
            case "velo_route": return R.drawable.velo_route;
            case "danse": return R.drawable.hip_hop;
            default: return -1;
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int position) {
        this.id = position;
    }
}
