package com.jumpitt.primero_cotiza.ui.lead_alert;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.utils.BaseActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LeadAlertActivity extends BaseActivity implements LeadAlertContract.View {

    private static final String TAG = LeadAlertActivity.class.getSimpleName();
    private LeadAlertContract.Presenter mPresenter;
    private boolean changer = true;
    private MediaPlayer mRing;
    private Vibrator mVib;
    private CountDownTimer timer;

    @BindView(R.id.lead_accept)
    ImageView leadAccept;
    @BindView(R.id.reject_button)
    MaterialButton leadReject;
    @BindView(R.id.countdown_timer)
    TextView counter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_alert);
        ButterKnife.bind(this);



        mPresenter = new LeadAlertPresenter(this);
        mPresenter.getTimer();

        final Animation zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        final Animation zoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

        leadAccept.startAnimation(zoomIn);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        zoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                leadAccept.startAnimation(zoomOut);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        zoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                leadAccept.startAnimation(zoomIn);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        leadAccept.setOnClickListener(v -> {
            leadAccept.setEnabled(false);
            changer = false;
            mPresenter.acceptLead();
        });

        leadReject.setOnClickListener(v -> {
            leadReject.setEnabled(false);
            changer = false;
            mPresenter.leadRejected();
        });

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void timerBegin(int timeleft){

        mRing = MediaPlayer.create(this,R.raw.bell);
        mRing.start();

        mVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        timer = new CountDownTimer(timeleft*1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Objects.requireNonNull(mVib).vibrate(500);
                String timer;
                if (millisUntilFinished < 10000) {
                    timer = getResources().getString(R.string.timer_one) + millisUntilFinished / 1000;
                    counter.setText(timer);
                }
                else {
                    timer = getResources().getString(R.string.timer_two)+millisUntilFinished/1000;
                    counter.setText(timer);
                }

            }
            @Override
            public void onFinish() {
                counter.setText(R.string.timer_finish);
                if (changer) {
                    mPresenter.toLostLead();
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        mRing.stop();
        mVib.cancel();
        super.onDestroy();
    }
}
