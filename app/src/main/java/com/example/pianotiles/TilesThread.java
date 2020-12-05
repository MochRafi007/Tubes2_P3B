package com.example.pianotiles;

import android.content.res.Resources;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.time.LocalTime;
import java.util.Random;

public class TilesThread implements Runnable{
    protected Thread thread;
    protected Tiles tiles;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean gameOver,isTilesClicked;
    protected double tilesWidth, canvasHeight,newLeft,newRight;
    protected int speed;
    protected PlayGameFragment playGameFragment;

    public TilesThread(UIThreadedWrapper uiThreadedWrapper, Tiles tiles, double tilesWidth,double canvasHeight){
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.thread = new Thread(this);
        this.tiles = tiles;
        this.gameOver = false;
        this.tilesWidth = tilesWidth;
        this.speed = 100;
        this.canvasHeight = canvasHeight;
        this.isTilesClicked = false;
        this.playGameFragment = new PlayGameFragment();
    }

    public void randPos(){
        double [] randCoor = {0,this.tilesWidth,this.tilesWidth*2,this.tilesWidth*3};
        int rand = new Random().nextInt(randCoor.length);
        this.newLeft = randCoor[rand];
        this.newRight = randCoor[rand]+this.tilesWidth;
    }

    public void setTilesClicked(boolean click){
        this.isTilesClicked = click;
    }

    public boolean getIsTilesClidked(){
        return this.isTilesClicked;
    }
    public void moveTiles(){
        this.thread.start();
    }

    public void setStop(boolean isStoped){
        this.gameOver = isStoped;
    }

    @Override
    public void run() {
        while(this.tiles.canMove()&&!this.tiles.isGameOver()){
            if (this.tiles.getTop()<=this.canvasHeight&&!this.tiles.isGameOver()){
                this.tiles.setNewCoordinate();
                this.uiThreadedWrapper.setNexTiles(this.tiles);
                try
                {
                    if(this.tiles.getScore()>=1 && this.tiles.getScore()<2){
                        Log.d("1", "run: "+ this.speed);
                        Thread.sleep(50);
                    }
                    else if(this.tiles.getScore()>=2 && this.tiles.getScore()<4){
                        Log.d("2","run: "+ this.speed);
                        Thread.sleep(25);
                    }
                    else if(this.tiles.getScore()>=4){
                        Log.d("3","run: "+ this.speed);
                        Thread.sleep(10);
                    }
                    else {
                        Log.d("anjing", "run: " + this.tiles.getScore());
                        Thread.sleep(this.speed);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{ //balik lagi keatas
                if (!this.isTilesClicked){ //cek sebelumnya kelo ga di klik//nyawanya berkurang
                    this.tiles.setLives(this.tiles.getLives()-1);
                }
                if(this.tiles.getLives()>=0) { //kalo masih bernyawa, jalanin
                    this.randPos();
                    this.setTilesClicked(false);
//                    this.tiles.isClicked(true);
                    this.tiles.setFirstCoordinate((int) this.newLeft, -450, (int) this.newRight, 0);
                    this.tiles.setNewCoordinate();
                    this.uiThreadedWrapper.setNexTiles(this.tiles);
                    try {
                        Thread.sleep(this.speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (this.tiles.getLives() <1){
                this.tiles.setIsGameOver(true);
                Log.d("game", "run: " + this.tiles.isGameOver());
            }
        }
    }
}

