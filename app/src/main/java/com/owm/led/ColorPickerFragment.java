package com.owm.led;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

/**
 *
 * Created by owm on 2017/8/15.
 */

public class ColorPickerFragment extends Fragment implements ColorPicker.OnColorSelectedListener, ColorPicker.OnColorChangedListener {

    private ColorPicker cpPicker;
    private TextView tvColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        cpPicker = (ColorPicker) view.findViewById(R.id.cp_picker);
        tvColor = (TextView) view.findViewById(R.id.tv_color);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        cpPicker.setOnColorSelectedListener(this);
        cpPicker.setOnColorChangedListener(this);
    }


    @Override
    public void onColorSelected(int color) {
        tvColor.setTextColor(color);
    }

    @Override
    public void onColorChanged(int color) {
        tvColor.setTextColor(color);
    }
}
