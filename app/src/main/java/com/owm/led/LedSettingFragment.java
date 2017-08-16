package com.owm.led;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.owm.led.util.BrightnessUtils;

/**
 *
 * Created by owm on 2017/8/15.
 */

public class LedSettingFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private CheckBox cbSystemBrightness;
    private SeekBar sbBrightness;

    private TextView tvBrightness;

    private LinearLayout llBrightness;

    private int systemBrightness;
    private boolean isAutoBrightnessFromSystem;

    private int userBrightness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_led_setting, container, false);
        initView(view);
        setListener(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initView(View view) {
        tvBrightness = (TextView) view.findViewById(R.id.tv_brightness);

        llBrightness = (LinearLayout) view.findViewById(R.id.ll_brightness);
        cbSystemBrightness = (CheckBox) view.findViewById(R.id.cb_system_brightness);
        sbBrightness = (SeekBar) view.findViewById(R.id.sb_brightness);
    }

    private void setListener(View view) {
        view.setOnClickListener(this);
        cbSystemBrightness.setOnCheckedChangeListener(this);
        sbBrightness.setOnSeekBarChangeListener(this);
        tvBrightness.setOnClickListener(this);
    }

    private void initData() {
        isAutoBrightnessFromSystem = BrightnessUtils.isAutoBrightness(getActivity());
        cbSystemBrightness.setChecked(isAutoBrightnessFromSystem);
        systemBrightness = BrightnessUtils.getScreenBrightness(getActivity());

        userBrightness = systemBrightness;
        sbBrightness.setProgress(userBrightness);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.rl_root:
                hide();
                break;
            case R.id.tv_brightness:
                llBrightness.setVisibility(llBrightness.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
        }
    }

    public void hide() {
        resetView();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(this);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void resetView() {
        llBrightness.setVisibility(View.GONE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sbBrightness.setEnabled(!isChecked);
        if (isChecked) {
            if (isAutoBrightnessFromSystem) {
                BrightnessUtils.startAutoBrightness(getActivity());
            }
            BrightnessUtils.setBrightness(getActivity(), systemBrightness);
        } else {
            BrightnessUtils.stopAutoBrightness(getActivity());
            BrightnessUtils.setBrightness(getActivity(), sbBrightness.getProgress());
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        BrightnessUtils.setBrightness(getActivity(), progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
