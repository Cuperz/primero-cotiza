package com.jumpitt.primero_cotiza.ui.lost_lead;


import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LostLeadActivity extends BaseActivity implements LostLeadContract.View {

    private static final String TAG = LostLeadActivity.class.getSimpleName();
    private LostLeadContract.Presenter mPresenter;

    @BindView(R.id.lost_lead_button)
    MaterialButton mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_lead);
        ButterKnife.bind(this);

        mPresenter = new LostLeadPresenter(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mButton.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mPresenter.toHome();
    }

}
