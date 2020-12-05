package com.example.pianotiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class PlayGameFragment extends Fragment implements View.OnClickListener, GestureDetector.OnGestureListener{
    public FragmentListener listener;
    protected Button btnStart, btnRetry, btnExit;
    protected LinearLayout layout, layoutBackground;
    protected Bitmap mBitmap;
    protected ImageView ivCanvas;
    protected Canvas mCanvas;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected Tiles tiles;
    protected TilesThread tilesThread;
    protected boolean canvasIsClean,isStarted;
    protected TextView tvScore,tvLives,tvLastScore, tvHighScore;
    protected GestureDetector mDetector;
    protected int scoreGameOver;
    public PlayGameFragment(){

    }

    public static PlayGameFragment newInstance(String title){
        PlayGameFragment fragment = new PlayGameFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_play_game,container,false);
        this.btnStart = view.findViewById(R.id.btn_start);
        this.layout = view.findViewById(R.id.layout_gameover);
        this.layoutBackground = view.findViewById(R.id.layout_background);
        this.ivCanvas = view.findViewById(R.id.iv_canvas);
        this.btnExit = view.findViewById(R.id.btn_exit);
        this.btnRetry = view.findViewById(R.id.btn_retry);
        this.tvLastScore = view.findViewById(R.id.tv_score_gameover);
        this.tvHighScore = view.findViewById(R.id.tv_highscore_gameover);
        this.mDetector = new GestureDetector(this.getContext(),this);
        this.btnExit.setOnClickListener(this);
        this.btnRetry.setOnClickListener(this);
        this.btnStart.setOnClickListener(this);
        this.tvScore = view.findViewById(R.id.tv_score);
        this.tvLives = view.findViewById(R.id.tv_lives);
        this.canvasIsClean = true;
        this.isStarted = false;
        this.scoreGameOver = 0;
        this.uiThreadedWrapper = new UIThreadedWrapper(this);
        this.ivCanvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                return mDetector.onTouchEvent(motionEvent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof FragmentListener){
            this.listener = (FragmentListener)context;
        }else{
            throw new ClassCastException(context.toString() + " must implement FragmentListener");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.btnStart.getId()){
            if(this.canvasIsClean){
                this.createFirstTiles();
                this.tilesThread.moveTiles();
            }
        }
        else if(v.getId() == this.btnRetry.getId()){
            this.layout.setVisibility(v.GONE);
            this.layoutBackground.setBackgroundColor(getResources().getColor(android.R.color.white));
            this.createFirstTiles();
            this.tilesThread.moveTiles();
        }
    }
    public void createFirstTiles(){
        this.isStarted = true;
        Paint paint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        paint.setColor(mColorTest);
//        Paint paint1 = new Paint();
//        int mColorTouch = ResourcesCompat.getColor(getResources(),R.color.pressed, null);
//        paint1.setColor(mColorTouch);
        this.tiles = new Tiles(0,0,this.ivCanvas.getWidth()/4,450,this.ivCanvas.getHeight());
        this.tilesThread = new TilesThread(this.uiThreadedWrapper,this.tiles,this.ivCanvas.getWidth()/4,this.ivCanvas.getHeight());
        mBitmap = Bitmap.createBitmap(this.ivCanvas.getWidth(),this.ivCanvas.getHeight(),Bitmap.Config.ARGB_8888);

        this.ivCanvas.setImageBitmap(this.mBitmap);

        this.mCanvas = new Canvas(this.mBitmap);


        this.mCanvas.drawRect(tiles.getLeft(),tiles.getTop(),tiles.getRight(),tiles.getBottom(),paint);

        this.ivCanvas.invalidate();
    }

    public void createTiles(Tiles tiles){
        this.tvLives.setText(Integer.toString(this.tiles.getLives()));
        this.tvScore.setText(Integer.toString(this.tiles.getScore()));
        this.deleteTiles();
        Paint paint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        paint.setColor(mColorTest);
//        Paint paint1 = new Paint();
//        int mColorTouch = ResourcesCompat.getColor(getResources(),R.color.pressed, null);
//        paint1.setColor(mColorTouch);
        this.mCanvas.drawRect(tiles.getLeft(),tiles.getTop(),tiles.getRight(),tiles.getBottom(),paint);
    }

    public void deleteTiles(){
        this.resetCanvas();
    }

    public void resetCanvas(){
        int mColorBackground = ResourcesCompat.getColor(getResources(),R.color.deletecolor,null);
        mCanvas.drawColor(mColorBackground);

        this.ivCanvas.invalidate();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int bot = this.tiles.getBottom();
        int top = this.tiles.getTop();
        int left = this.tiles.getLeft();
        int right = this.tiles.getRight();
        Log.d("touch_listener",x+"-"+y+"------"+top+"-"+bot+"-"+left+"-"+right);
        if (this.isStarted && !this.tilesThread.getIsTilesClidked()){
            if (this.tiles.getLeft()<=x && this.tiles.getRight()>=x && this.tiles.getTop()<=y && this.tiles.getBottom()>=y){
                this.tiles.setScore(this.tiles.getScore()+1);
                this.tvScore.setText(Integer.toString(this.tiles.getScore()));
                this.tilesThread.setTilesClicked(true);
            }
            else if(this.tiles.isGameOver()){
                this.layout.setVisibility(View.VISIBLE);
                this.layoutBackground.setBackgroundColor(getResources().getColor(android.R.color.white));

            }
            else{
                this.tiles.setLives(this.tiles.getLives()-1);
            }
        }
        return false;
    }
//    public void showModal(){
//        if(this.tiles.isGameOver()){
//            this.layout.setVisibility(View.VISIBLE);
//            this.layoutBackground.setBackgroundColor(getResources().getColor(android.R.color.white));
//        }
//    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
