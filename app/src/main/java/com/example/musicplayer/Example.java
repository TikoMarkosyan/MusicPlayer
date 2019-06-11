package com.example.musicplayer;

public class Example {
    private int mImageResurse;
    private String mtext1;
    private String mtext2;
    public Example(int ImageResurse,String text1,String text2){
        mImageResurse = ImageResurse;
        mtext1 = text1;
        mtext2 = text2;
    }
    public void changeText1(String text){
        mtext1 = text;
    }
    public int getmImageResurse() {
        return mImageResurse;
    }

    public String getMtext1() {
        return mtext1;
    }
    public String getMtext2() {
        return mtext2;
    }
}
