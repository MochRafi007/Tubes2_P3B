package com.example.pianotiles;

public class Tiles {
    protected int left,top,right,bottom,canvasWidth,lives;
    protected boolean isClicked;

    public Tiles(int left, int top, int right, int bottom,int canvasWidth){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.canvasWidth = canvasWidth;
        this.isClicked = false;
        this.lives = 3;
    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public int getLives(){
        return  this.lives;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setNewCoordinate(){
        this.top +=10;
        this.bottom +=10;
    }

    public void setFirstCoordinate(int left, int top, int right, int bottom) {
        this.bottom = bottom;
        this.left = left;
        this.top = top;
        this.right = right;
    }

    public boolean canMove(){
        if (this.left<=this.canvasWidth && !isClicked){
            return true;
        }
        else{
            return false;
        }
    }
}

