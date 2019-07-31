package com.harry.finkers.intro;

public class IntroItem {

    String Description;
    int Introing;

    public IntroItem(String description, int introing) {
        Description = description;
        Introing = introing;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getIntroing() {
        return Introing;
    }

    public void setIntroing(int introing) {
        Introing = introing;
    }
}

