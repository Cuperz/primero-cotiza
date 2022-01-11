package com.jumpitt.primero_cotiza.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.TransitionManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.utils.BaseActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_NEGATIVE;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter mPresenter;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ConstraintSet finalConstraint;
    private View rootView;
    public static final int ORIGIN_PUSH = 0;
    public static final int SESION_EXPIRED = 9;


    ProgressDialog progressDialog;

    @BindView(R.id.input_email)
    TextInputLayout inputEmail;
    @BindView(R.id.input_password)
    TextInputLayout inputPassword;
    @BindView(R.id.login_splash)
    ConstraintLayout loginSplash;
    @BindView(R.id.login_button)
    MaterialButton loginButton;
    @BindView(R.id.edit_text)
    TextInputEditText editText;
    @BindView(R.id.recover_password)
    TextView recoverPassword;
    @BindView(R.id.texto_primero_cotiza)
    TextView tittle;
    @BindView(R.id.email_editText)
    TextView emailText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this);

        if(!mPresenter.validateActivity(getIntent().getExtras())) {

            rootView = getWindow().getDecorView().getRootView();
            inputPassword.setTypeface(ResourcesCompat.getFont(this, R.font.quicksand));
            new Handler().postDelayed(() -> mPresenter.isLogged(), 1000);
        }

    }

    @Override
    public void beginTransformation() {
        new Handler().postDelayed(() -> {
            TransitionManager.beginDelayedTransition(loginSplash);
            finalConstraint.applyTo(loginSplash);
            loginSplash.setBackgroundColor(0xffffff);
        }, 1000);

    }



    @Override
    public void animate() {
        if (!getEmail().equals("")) emailText.setText(getEmail());
        finalConstraint = new ConstraintSet();
        finalConstraint.clone(this, R.layout.activity_login_final);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(500);
        beginTransformation();
    }

    @OnClick(R.id.recover_password)
    void onRecoverPressed(){
        mPresenter.torRecoverPass();
    }

    @OnClick(R.id.login_button)
    void onLoginPressed() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(loginButton.getWindowToken(), 0);
        loginButton.setEnabled(false);
        String email = Objects.requireNonNull(inputEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(inputPassword.getEditText()).getText().toString();
        loginDialog();
        mPresenter.userSubmit(email, password);
        loginButton.setEnabled(true);

    }

    @Override
    public void showInputError() {
        showSbMessage(loginSplash, getResources().getString(R.string.login_error_msg), TYPE_NEGATIVE);
    }

    @Override
    public void showError(String message) {
        showSbMessage(loginSplash, message,TYPE_NEGATIVE);

    }

    private String getEmail(){
        return mPresenter.getEmail();
    }

    public void loginDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.validating));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

}