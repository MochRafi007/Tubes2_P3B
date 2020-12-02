package com.example.pianotiles;

public class TilesThread implements Runnable{
    protected Thread thread;
    protected Tiles tiles;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean gameOver;
    protected double tilesWidth;
    protected int speed;

    public TilesThread(UIThreadedWrapper uiThreadedWrapper, Tiles tiles, double tilesWidth){
        this.uiThreadedWrapper = uiThreadedWrapper;
        this.thread = new Thread(this);
        this.tiles = tiles;
        this.gameOver = false;
        this.tilesWidth = tilesWidth;
        this.speed = 20;
    }

    public void moveTiles(){
        this.thread.start();
    }

    public void setStop(boolean isStoped){
        this.gameOver = isStoped;
    }

    @Override
    public void run() {
        while(this.tiles.canMove()&&!this.gameOver){
            this.tiles.setNewCoordinate();
            this.uiThreadedWrapper.setNexTiles(this.tiles);
            try
            {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

