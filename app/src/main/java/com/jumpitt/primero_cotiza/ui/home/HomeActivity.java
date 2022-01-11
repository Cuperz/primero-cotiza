package com.jumpitt.primero_cotiza.ui.home;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.adapter.HomePagerAdapter;
import com.jumpitt.primero_cotiza.ui.clients.ClientsFragment;
import com.jumpitt.primero_cotiza.ui.companies.CompaniesFragment;
import com.jumpitt.primero_cotiza.ui.profile.ProfileFragment;
import com.jumpitt.primero_cotiza.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.bottomNavBar)
    BottomNavigationView bottomNavBar;
    @BindView(R.id.main_pager)
    ViewPager mainPager;
    @BindView(R.id.home_layout)
    ConstraintLayout mConstraint;

    private static final String TAG = HomeActivity.class.getSimpleName();

    //Fragments ID
    public static final int FRM_COMPANIES = 0;
    public static final int FRM_CLIENTS = 1;
    public static final int FRM_PROFILE = 2;
    public static final int MAIN_FRAGMENT = FRM_CLIENTS;

    private Fragment companiesFragment;
    private Fragment clientsFragment;
    private Fragment profileFragment;
    private boolean onBackPress = true;

    private HomeContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mPresenter = new HomePresenter(this);

        final HomePagerAdapter mAdapter = new HomePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        companiesFragment = CompaniesFragment.newInstance();
        clientsFragment = ClientsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        mAdapter.addFragment(companiesFragment);
        mAdapter.addFragment(clientsFragment);
        mAdapter.addFragment(profileFragment);

        mainPager.setAdapter(mAdapter);
        mainPager.setCurrentItem(MAIN_FRAGMENT);
        mainPager.setOffscreenPageLimit(2);

        bottomNavBar.getMenu().getItem(MAIN_FRAGMENT).setChecked(false);

        bottomNavBar.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.navigation_companies:
                    mainPager.setCurrentItem(HomeActivity.FRM_COMPANIES);
                    break;
                case R.id.navigation_clients:
                    mainPager.setCurrentItem(HomeActivity.FRM_CLIENTS);
                    break;
                case R.id.navigation_profile:
                    mainPager.setCurrentItem(HomeActivity.FRM_PROFILE);
                    break;
            }
            return false;
        });

        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (bottomNavBar != null)
                    bottomNavBar.getMenu().getItem(position).setChecked(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed(){
        if(onBackPress){
            if(mainPager.getCurrentItem() == FRM_CLIENTS)
                finishAffinity();
            else
                mainPager.setCurrentItem(FRM_CLIENTS);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter = null;
    }

}
