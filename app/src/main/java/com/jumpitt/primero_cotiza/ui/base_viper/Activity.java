package com.jumpitt.primero_cotiza.ui.base_viper;


import android.os.Bundle;

import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.utils.BaseActivity;

import butterknife.ButterKnife;


public class Activity extends BaseActivity implements Contract.View {

    private Contract.Presenter presenter;
    private static final String TAG = Activity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new Presenter(this);

    }

}
