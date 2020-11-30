package com.example.pianotiles;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    public FragmentListener listener;

    protected Button btNewGame,btQuitGame;
    public MainMenuFragment(){

    }

    public static MainMenuFragment newInstance(String title){
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        args.putString("title",title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main_menu,container,false);
        this.btNewGame = view.findViewById(R.id.bt_playNewGame);
        this.btQuitGame = view.findViewById(R.id.bt_quitGame);
        this.btNewGame.setOnClickListener(this);
        this.btQuitGame.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        if (v.getId() == this.btNewGame.getId()){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.changePage(2);
        }else if (v.getId() == this.btQuitGame.getId()){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.closeApplication();
        }

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
}
