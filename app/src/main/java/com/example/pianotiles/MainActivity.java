package com.example.pianotiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FragmentListener {
    public FragmentManager fragmentManager;
    protected MainMenuFragment mainMenuFragment;
    protected LevelPickerFragment levelPickerFragment;
    protected PlayGameFragment playGameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainMenuFragment = MainMenuFragment.newInstance("New Menu Fragment");
        this.levelPickerFragment = LevelPickerFragment.newInstance("New Level Fragment");
        this.playGameFragment = PlayGameFragment.newInstance("New Play Fragment");
        this.fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container,this.mainMenuFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void changePage(int page){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if (page==1){
            if (this.mainMenuFragment.isAdded()){
                ft.show(this.mainMenuFragment);
            }else{
                ft.add(R.id.fragment_container,this.mainMenuFragment);
            }
            if (this.levelPickerFragment.isAdded()){
                ft.hide(this.levelPickerFragment);
            }
            else if (this.playGameFragment.isAdded()) {
                ft.hide(this.playGameFragment);
            }
        }else if(page==2){
            if(this.levelPickerFragment.isAdded()){
                ft.show(this.levelPickerFragment);
            }else{
                ft.add(R.id.fragment_container,this.levelPickerFragment);
            }
            if(this.mainMenuFragment.isAdded()){
                ft.hide(this.mainMenuFragment);
            }
            else if (this.playGameFragment.isAdded()) {
                ft.hide(this.playGameFragment);
            }
        }else if(page==3){
            if(this.playGameFragment.isAdded()){
                ft.show(this.playGameFragment);
            }else{
                ft.add(R.id.fragment_container,this.playGameFragment).addToBackStack(null);
            }
            if(this.mainMenuFragment.isAdded()){
                ft.hide(this.mainMenuFragment);
            }
            else if (this.levelPickerFragment.isAdded()) {
                ft.hide(this.levelPickerFragment);
            }
        }
        ft.commit();
    }

    @Override
    public void closeApplication(){
        this.moveTaskToBack(true);
        this.finish();
    }
}