package com.example.game.objects;

import android.app.Application;

public class Game extends Application {

    private Profile profile = new Profile("Default", "");

    public Profile getProfile()
    {
        return  profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }
}
