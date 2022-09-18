package com.example.database;

public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private String mText3;
    private String mText4;
    private String mText5;
    private String mText6;

    public ExampleItem (int imageResource, String tex1 , String text2 , String tex3 , String text4 , String tex5 , String text6 ){
        mImageResource=imageResource;
        mText1=tex1;
        mText2=text2;
        mText3=tex3;
        mText4=text4;
        mText5=tex5;
        mText6=text6;

    }

    public int getImageResource() {
        return mImageResource;
    }
    public String getText1(){
        return mText1;
    }
    public String getText2(){
        return mText2;
    }
    public String getText3(){
        return mText3;
    }
    public String getText4(){
        return mText4;
    }
    public String getText5(){
        return mText5;
    }
    public String getText6(){
        return mText6;
    }
}
