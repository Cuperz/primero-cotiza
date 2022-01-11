package com.jumpitt.primero_cotiza.ui.profile;


import android.content.Intent;
import com.jumpitt.primero_cotiza.ui.login.LoginActivity;

import java.util.Objects;

public class ProfileRouter implements ProfileContract.Router {

    private static final String TAG = ProfileRouter.class.getSimpleName();


    private ProfileFragment mFragment;

    public ProfileRouter(ProfileFragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void doLogout(){
        Intent i = new Intent( mFragment.getActivity(), LoginActivity.class);
        mFragment.startActivity(i);
        Objects.requireNonNull(mFragment.getActivity()).finishAffinity();
    }
}
