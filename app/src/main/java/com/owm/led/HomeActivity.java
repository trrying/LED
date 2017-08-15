package com.owm.led;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by owm on 2017/8/15.
 */

public class HomeActivity extends AppCompatActivity{

    private ViewPager vp_content;
    private ViewPagerAdapter mAdapter;
    private List<Fragment> mData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        if (mAdapter == null) {
            mData = new ArrayList<>();
            mData.add(new ColorPickerFragment());
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mData);
            vp_content = (ViewPager) findViewById(R.id.vp_content);
            vp_content.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
