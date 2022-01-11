package com.jumpitt.primero_cotiza.ui.companies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.adapter.CompaniesAdapter;
import com.jumpitt.primero_cotiza.model.Company;
import com.jumpitt.primero_cotiza.ui.home.HomeActivity;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jumpitt.primero_cotiza.utils.Constants.COMPANY_RESULT_SUCCESS;
import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_NEGATIVE;
import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_POSITIVE;

public class CompaniesFragment extends Fragment implements CompaniesContract.View {

    private static final String TAG = CompaniesFragment.class.getSimpleName();
    private CompaniesContract.Presenter mPresenter;
    private CompaniesAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<Company> mCompanyList;
    private Boolean loading = false;

    @BindView(R.id.companies_recycler)
    ShimmerRecyclerView compRecycler;
    @BindView(R.id.company_switch)
    SwitchCompat mSwitch;


    public static CompaniesFragment newInstance() {
        CompaniesFragment fragment = new CompaniesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CompaniesPresenter(this);
    }


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstaceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_companies, container, false);
        ButterKnife.bind(this, rootView);

        compRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());

        mAdapter = new CompaniesAdapter(new ArrayList<>(), this);
        compRecycler.setLayoutManager(layoutManager);
        compRecycler.setAdapter(mAdapter);

        if (!loading) {
            compRecycler.showShimmer();
            mPresenter.getCompanies();
            loading = true;
        }


        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setCompanies(List<Company> companyList) {
        this.mCompanyList = companyList;
        compRecycler.hideShimmer();
        loading = false;
        if (!companyList.isEmpty()) {
            mAdapter.setDataList(companyList);
            mSwitch.setChecked(companyList.get(0).isOnline());
        }
        mSwitch.setOnClickListener(v -> {
            if (mSwitch.isChecked()) {
                companyOn();
            } else {
                companyOff();
            }
        });

    }

    @Override
    public void companiesFailed(String message) {
        View view = this.getView();
        if (getActivity() != null) {
            ((HomeActivity) Objects.requireNonNull(getActivity())).showSbMessage(view, message, TYPE_NEGATIVE);
        }
    }


    private void enabledMessage(String message, int type) {
        View view = this.getView();
        if (getActivity() != null) {
            if (type == TYPE_POSITIVE) {
                ((HomeActivity) Objects.requireNonNull(getActivity())).showSbMessage(view, message, TYPE_POSITIVE);
            } else {
                ((HomeActivity) Objects.requireNonNull(getActivity())).showSbMessage(view, message, TYPE_NEGATIVE);
            }
        }
    }

    private void companyOn() {
        if (!mCompanyList.isEmpty()) {
            mPresenter.companyEnabled(mCompanyList.get(0).getAccountId());
        }
    }

    private void companyOff() {
        if (!mCompanyList.isEmpty()) {
            mPresenter.companyDisabled(mCompanyList.get(0).getAccountId());
        }
    }

    @Override
    public void enableResult(int result) {
        if (result == COMPANY_RESULT_SUCCESS) {
            enabledMessage(getString(R.string.company_enabled), TYPE_POSITIVE);
        } else {
            enabledMessage(getString(R.string.company_enabled_error), TYPE_NEGATIVE);
        }
    }

    @Override
    public void disableResult(int result) {
        if (result == COMPANY_RESULT_SUCCESS) {
            enabledMessage(getString(R.string.company_disabled), TYPE_NEGATIVE);
        } else {
            enabledMessage(getString(R.string.company_disabled_error), TYPE_NEGATIVE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!loading) {
            mPresenter.getCompanies();
        }
    }

}
