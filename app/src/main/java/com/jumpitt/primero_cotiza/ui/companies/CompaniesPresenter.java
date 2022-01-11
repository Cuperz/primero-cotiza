package com.jumpitt.primero_cotiza.ui.companies;

import com.jumpitt.primero_cotiza.model.Company;

import java.util.List;

import static com.jumpitt.primero_cotiza.utils.Constants.COMPANY_RESULT_SUCCESS;

public class CompaniesPresenter implements CompaniesContract.Presenter, CompaniesContract.InteractorOutput {

    private static final String TAG = CompaniesPresenter.class.getSimpleName();

    private CompaniesContract.View mFragment;
    private CompaniesContract.Interactor mInteractor;
    private CompaniesContract.Router mRouter;

    public CompaniesPresenter(CompaniesFragment fragment) {
        mInteractor = new CompaniesInteractor(this);
        this.mFragment = fragment;
        this.mRouter = new CompaniesRouter(fragment) {
        };

    }

    @Override
    public void getCompanies() {
        mInteractor.getCompanies();
    }

    @Override
    public void companyEnabled(int companyId) {
        mInteractor.companyEnabled(companyId);
    }

    @Override
    public void companyDisabled(int companyId) {
        mInteractor.companyDisabled(companyId);
    }


    //--------------------IntOut-----------------------------------

    @Override
    public void setCompanies(List<Company> companyList) {
            mFragment.setCompanies(companyList);
    }

    @Override
    public void companiesFailed(String message) {
        mFragment.companiesFailed(message);

    }

    @Override
    public void enableResult(int result) {
        if (result == COMPANY_RESULT_SUCCESS) {
            getCompanies();
            mFragment.enableResult(result);
        } else {
            getCompanies();
            mFragment.enableResult(result);
        }

    }

    @Override
    public void disableResult(int result) {
        if (result == COMPANY_RESULT_SUCCESS) {
            getCompanies();
            mFragment.disableResult(result);
        } else {
            getCompanies();
            mFragment.disableResult(result);
        }
    }

    @Override
    public void refreshLogin() {
        mInteractor.finishSesion();
        mRouter.toLogin();
    }
}
