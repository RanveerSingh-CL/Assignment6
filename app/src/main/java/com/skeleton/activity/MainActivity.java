package com.skeleton.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.skeleton.R;
import com.skeleton.util.customview.CustomViewPager;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity {
    private CustomViewPager vpPager;
    private com.skeleton.adapter.PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * initialization
     */
    private void init() {
        vpPager = (CustomViewPager) findViewById(R.id.vpPager);
        pagerAdapter = new com.skeleton.adapter.PagerAdapter(getSupportFragmentManager(), this);
        vpPager.setAdapter(pagerAdapter);
        //tab Layout
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

    }
}
