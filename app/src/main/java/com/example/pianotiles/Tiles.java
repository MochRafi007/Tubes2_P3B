package com.example.pianotiles;

import android.content.res.Resources;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

public class Tiles {
    protected int left,top,right,bottom,canvasWidth,lives, score;
    protected boolean isClicked, isGameOver;
//    private Paint paint, isPaint;

    public Tiles(int left, int top, int right, int bottom,int canvasWidth){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.canvasWidth = canvasWidth;
        this.isClicked = false;
        this.isGameOver = false;
        this.lives = 3;
        this.score = 0;
//        this.paint = painted;
//        this.isPaint = isPainted;

    }

    public void setLives(int lives){
        this.lives = lives;
    }

    public void setScore(int score) {this.score = score;}

    public int getScore(){ return  this.score;}

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

//    public void isClicked(boolean isClicked) {
//        this.isClicked = isClicked;
//    }
//
//    public void setPaint(Paint painted){
//        this.paint = painted;
//    }
//
//    public Paint getIsPaint(){
//        return this.isPaint;
//    }
    public boolean isGameOver(){
        return this.isGameOver;
    }

    public void setIsGameOver(boolean isGameOver){
        this.isGameOver = isGameOver;
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
        if (this.left<=this.canvasWidth && !isClicked && !isGameOver){
            return true;
        }
        else{
            return false;
        }
    }
}

