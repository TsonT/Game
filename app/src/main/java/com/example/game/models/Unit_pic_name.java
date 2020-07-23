package com.example.game.models;

public class Unit_pic_name {
    public int Image_unit;
    public String Text_name;

    public Unit_pic_name(String text_name, int image_unit) {
       Image_unit = image_unit;
       Text_name = text_name;
    }

    public int getImage() {
        return Image_unit;
    }

    public void setImage(int image_unit) {
        Image_unit = image_unit;
    }

    public String getName() {
        return Text_name;
    }

    public void setName(String text_name) {
        Text_name = text_name;
    }
}
