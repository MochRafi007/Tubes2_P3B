package com.example.pianotiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class LevelPickerFragment extends Fragment implements View.OnClickListener {
    protected Button lv1,lv2,lv3,btBack;
    public LevelPickerFragment(){

    }

    public static LevelPickerFragment newInstance(String title){
        LevelPickerFragment fragment = new LevelPickerFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_level_picker,container,false);
        this.lv1 = view.findViewById(R.id.bt_levelSatu);
        this.lv2 = view.findViewById(R.id.bt_levelDua);
        this.lv3 = view.findViewById(R.id.bt_levelTiga);
        this.btBack = view.findViewById(R.id.bt_backToMenu);
        this.btBack.setOnClickListener(this);
        this.lv1.setOnClickListener(this);
        this.lv2.setOnClickListener(this);
        this.lv3.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == this.btBack.getId()){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.changePage(1);
        }else if(v.getId() == this.lv1.getId()){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.changePage(3);
        }
    }

}
