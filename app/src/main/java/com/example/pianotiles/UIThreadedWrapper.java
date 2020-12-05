package com.example.pianotiles;

import android.os.Message;

import android.os.Handler;
import android.os.Message;

public class UIThreadedWrapper extends Handler {
    protected PlayGameFragment mainActivity;
    protected final static int MSG_SET_IMAGEVIEW_OUTPUT=0;

    public UIThreadedWrapper(PlayGameFragment mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg){
        if (msg.what==UIThreadedWrapper.MSG_SET_IMAGEVIEW_OUTPUT){
            Tiles tiles = (Tiles) msg.obj;
            this.mainActivity.createTiles(tiles);
//            this.mainActivity.showModal();
        }
    }

    public void setNexTiles(Tiles tiles){
        Message msg = new Message();
        msg.what=MSG_SET_IMAGEVIEW_OUTPUT;
        msg.obj=tiles;
        this.sendMessage(msg);
    }
}

