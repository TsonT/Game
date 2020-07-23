package com.example.game.functions;

import android.app.Activity;
import android.content.Context;

import com.example.game.FormationCreator;
import com.example.game.MainMenu;
import com.example.game.objects.Game;
import com.example.game.objects.Profile;

public class GlobalProfile {

    public static Profile getGlobalProfile(Activity activity)
    {
        return  ((Game) activity.getApplication()).getProfile();
    }

    public static void setGlobalProfile(Activity activity, Profile profile)
    {
        ((Game) activity.getApplication()).setProfile(profile);
    }

}
