package com.lynn.pojo;

public class DxomarkScore {

   int score_id;
   String phone_model;
   int score_camera;
   int score_selfie;
   int score_audio;
   int update_date;

    public int getScore_id() {
        return score_id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }

    public String getPhone_model() {
        return phone_model;
    }

    public void setPhone_model(String phone_model) {
        this.phone_model = phone_model;
    }

    public int getScore_camera() {
        return score_camera;
    }

    public void setScore_camera(int score_camera) {
        this.score_camera = score_camera;
    }

    public int getScore_selfie() {
        return score_selfie;
    }

    public void setScore_selfie(int score_selfie) {
        this.score_selfie = score_selfie;
    }

    public int getScore_audio() {
        return score_audio;
    }

    public void setScore_audio(int score_audio) {
        this.score_audio = score_audio;
    }

    public int getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(int update_date) {
        this.update_date = update_date;
    }
}
