package com.jumpitt.primero_cotiza.ui.lead;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.adapter.LeadAdapter;
import com.jumpitt.primero_cotiza.model.LeadInfo;
import com.jumpitt.primero_cotiza.utils.BaseActivity;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_NEGATIVE;
import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_POSITIVE;


public class LeadActivity extends BaseActivity implements LeadContract.View {

    private static final String TAG = LeadActivity.class.getSimpleName();
    private static final int REQUEST_PHONE_CALL = 1;
    public static final int FROM_ALERT = 2;
    public static final int FROM_CLIENT = 3;
    private LeadContract.Presenter mPresenter;
    private LeadAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private Intent mIntent;
    private String mPhone;
    private String mEmail;
    private CountDownTimer mCounter;
    private int mClientId = -1;
    private Boolean call = true;
    public AlertDialog mAlertDialog;

    @BindView(R.id.lead_recycler)
    ShimmerRecyclerView mRecycler;
    @BindView(R.id.call_button_text)
    TextView mBtnText;
    @BindView(R.id.call_button)
    ConstraintLayout mCallBtn;
    @BindView(R.id.call_button_icon)
    ImageView mBtnIcon;
    @BindView(R.id.lead_name)
    TextView mName;
    @BindView(R.id.lead_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.send_whatsapp)
    MaterialButton mWspButton;
    @BindView(R.id.send_email)
    MaterialButton mEmailButton;
    @BindView(R.id.lead_layout)
    ConstraintLayout activityLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);


        mPresenter = new LeadPresenter(this);

        mRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new LeadAdapter(new ArrayList<>(), this);
        mRecycler.setAdapter(mAdapter);

        mCallBtn.setEnabled(false);
        mEmailButton.setEnabled(false);
        mWspButton.setEnabled(false);
        mRecycler.showShimmer();

        mIntent = getIntent();
        if (mIntent != null) {
            int mode = Objects.requireNonNull(mIntent.getExtras()).getInt("mode");
            if (mode == FROM_CLIENT) {
                setFromClient();
            } else {
                setFromAlert();
            }
        }
        mCallBtn.setOnClickListener(v -> {
            if (checkPermission()) {
                callClient();
            }
        });

        mWspButton.setOnClickListener(v -> sendWsp());
        mEmailButton.setOnClickListener(v -> sendEmail());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    @Override
    public void setInfo(String name, String email, String phone, List<LeadInfo> leadInfos) {
        mRecycler.hideShimmer();
        mName.setText(name);
        mEmail = email;
        mPhone = phone;
        mAdapter.setDataList(leadInfos);
        mCallBtn.setEnabled(true);
        mEmailButton.setEnabled(true);
        mWspButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (call) {
            mPresenter.toHome();
        } else {


            mAlertDialog = new AlertDialog.Builder(this).create();
            mAlertDialog.setMessage("    Perderás el lead al salir");
            mAlertDialog.setTitle("¿ Seguro que quieres salir ?");
            mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Volver",
                    ((dialog, which) ->
                            mAlertDialog.dismiss()
                    ));
            mAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Salir",
                    (dialog, which) -> {
                        mPresenter.toHome();
                    });
            mAlertDialog.setCancelable(false);
            mAlertDialog.show();

        }
    }

    public boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
            return false;
        }
    }

    private void starCountDown() {
        call = false;
        mCounter = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                String timer = getResources().getString(R.string.lead_remaining_1) + millisUntilFinished / 1000 + getResources().getString(R.string.lead_remaining_2);
                mBtnText.setText(timer);
            }

            @Override
            public void onFinish() {
                mBtnText.setText(getResources().getString(R.string.lead_remaining_full));
                mBtnText.setTextColor(getResources().getColor(R.color.darkGray));
                mCallBtn.setBackgroundColor(getResources().getColor(R.color.notSoDarkGray));
                mBtnIcon.setColorFilter(getResources().getColor(R.color.darkGray));
                mCallBtn.setEnabled(false);
                call = true;
            }
        }.start();

    }

    public void setFromClient() {
        mClientId = mIntent.getExtras() != null ? mIntent.getExtras().getInt("id") : -1;
        mClientId = Objects.requireNonNull(mIntent.getExtras()).getInt("id");
        mPresenter.getClient(mClientId);
        mBtnText.setText(getResources().getString(R.string.lead_call));
        checkPermission();


    }

    public void setFromAlert() {
        mClientId = -1;
        mPresenter.getInfo();
        checkPermission();
        mWspButton.setVisibility(View.GONE);
        mEmailButton.setVisibility(View.GONE);
        starCountDown();

    }

    public void callClient() {
        mCallBtn.setEnabled(false);
        if (mClientId != -1) {
            mPresenter.callClient(mPhone, mClientId);
            new Handler().postDelayed(() -> mCallBtn.setEnabled(true), 6000);
        } else {
            mPresenter.makeCall(mPhone);
        }

    }

    public void sendWsp() {
        mPresenter.sendWsp(mPhone);
    }

    public void sendEmail() {
        mPresenter.sendEmail(mEmail);
    }

    @Override
    public void onError(String message) {
        showSbMessage(activityLayout, message, TYPE_NEGATIVE);
    }

    @Override
    public void onSuccess(String message) {
        mCounter.cancel();
        mCounter = null;
        mBtnText.setText(getResources().getString(R.string.lead_accepted));
        mBtnText.setTextColor(getResources().getColor(R.color.white));
        mCallBtn.setBackgroundColor(getResources().getColor(R.color.basePurple));
        mBtnIcon.setVisibility(View.GONE);
        call = true;
    }

    @Override
    public void onAccepted() {
        call = true;
    }


}
