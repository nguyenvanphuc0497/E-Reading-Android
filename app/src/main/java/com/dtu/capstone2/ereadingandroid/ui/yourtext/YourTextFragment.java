package com.dtu.capstone2.ereadingandroid.ui.yourtext;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.capstone2.ereadingandroid.R;


/**
 * Create By Huynh Vu Ha Lan on 20/03/2019
 */
public class YourTextFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_your_text, container, false);
        return view;
    }
}
