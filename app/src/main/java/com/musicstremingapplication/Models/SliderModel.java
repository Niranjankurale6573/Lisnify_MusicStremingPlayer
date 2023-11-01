package com.musicstremingapplication.Models;

public class SliderModel {
    private int image;
    private String sliderName;

    public SliderModel(int image, String sliderName) {
        this.image = image;
        this.sliderName = sliderName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }
}
