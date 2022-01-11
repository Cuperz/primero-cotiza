package com.jumpitt.primero_cotiza.ui.companies;

import android.content.Intent;

import com.jumpitt.primero_cotiza.ui.login.LoginActivity;

import java.util.Objects;

import static com.jumpitt.primero_cotiza.ui.login.LoginActivity.SESION_EXPIRED;

public class CompaniesRouter implements CompaniesContract.Router {

    private static final String TAG = CompaniesRouter.class.getSimpleName();

    CompaniesFragment mFragment;

    public CompaniesRouter(CompaniesFragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void toLogin() {
        Intent i = new Intent(mFragment.getContext() , LoginActivity.class);
        i.putExtra("from",SESION_EXPIRED);
        mFragment.startActivity(i);
        Objects.requireNonNull(mFragment.getActivity()).finishAffinity();
    }
}
