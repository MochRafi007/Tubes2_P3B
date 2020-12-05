package com.example.pianotiles;

public class MainPresenter {
    FragmentListener fragmentListener;
    IMainActivity ui;

    public MainPresenter(IMainActivity activity, FragmentListener listener){
        this.fragmentListener = listener;
        this.ui = activity;
    }

    public void resetScore(){

    }
}
