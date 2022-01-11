package com.jumpitt.primero_cotiza.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.model.UserProfile;
import com.jumpitt.primero_cotiza.ui.home.HomeActivity;
import com.orhanobut.hawk.Hawk;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_BODY;
import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_NEGATIVE;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter mPresenter;
    private static final String TAG = ProfileFragment.class.getSimpleName();


    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.profile_logout_button)
    MaterialButton logOutButton;

    public static ProfileFragment newInstance(){
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePresenter(this);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstaceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter.getProfileData();
        return rootView;
    }

    @Override
    public void profileFailed(String message) {
        View view = this.getView();
        if (getActivity() != null) {
            ((HomeActivity) Objects.requireNonNull(getActivity())).showSbMessage(view, message, TYPE_NEGATIVE);
        }
    }

    @Override
    public void setUser(){
            UserProfile userProfile = Hawk.get(HAWK_USER_BODY);
            String name = userProfile.getForenames()+" "+userProfile.getSurnames();
            profileName.setText(name);
            profileEmail.setText(userProfile.getEmail());

            Glide.with(Objects.requireNonNull(getContext()))
                    .load(userProfile.getImage())
                    .apply(
                            new RequestOptions()
                                    .centerCrop()
                                    .error(R.drawable.ic_default_profile)
                    ).into(profileImage);
    }

    @Override
    public void onLogOutError() {
        View view = this.getView();
        if (getActivity() != null) {
            ((HomeActivity) Objects.requireNonNull(getActivity())).showSbMessage(view, App.getContext().getResources().getString(R.string.profile_logout_error), TYPE_NEGATIVE);
        }
        logOutButton.setEnabled(true);
    }

    @OnClick(R.id.profile_logout_button)
    void logoutPressed(){
        logOutButton.setEnabled(false);
        mPresenter.doLogout();

    }

}
