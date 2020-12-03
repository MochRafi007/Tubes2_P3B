package com.example.pianotiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class PlayGameFragment extends Fragment implements View.OnClickListener{
    public FragmentListener listener;
    protected Button btnStart;
    protected Bitmap mBitmap;
    protected ImageView ivCanvas;
    protected Canvas mCanvas;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected Tiles tiles;
    protected TilesThread tilesThread;
    protected boolean canvasIsClean;
    protected TextView tvScore;
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
        this.ivCanvas = view.findViewById(R.id.iv_canvas);

        this.btnStart.setOnClickListener(this);

        this.canvasIsClean = true;

        this.uiThreadedWrapper = new UIThreadedWrapper(this);

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
    }

    public void createFirstTiles(){
        this.tiles = new Tiles(0,0,this.ivCanvas.getWidth()/4,450,this.ivCanvas.getHeight());
        this.tilesThread = new TilesThread(this.uiThreadedWrapper,this.tiles,this.ivCanvas.getWidth()/4);
        mBitmap = Bitmap.createBitmap(this.ivCanvas.getWidth(),this.ivCanvas.getHeight(),Bitmap.Config.ARGB_8888);

        this.ivCanvas.setImageBitmap(this.mBitmap);

        this.mCanvas = new Canvas(this.mBitmap);

        Paint paint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        paint.setColor(mColorTest);

        this.mCanvas.drawRect(tiles.getLeft(),tiles.getTop(),tiles.getRight(),tiles.getBottom(),paint);

        this.ivCanvas.invalidate();
    }

    public void createTiles(Tiles tiles){
        this.deleteTiles();
        Paint paint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(),R.color.colorPrimary,null);
        paint.setColor(mColorTest);
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
}
