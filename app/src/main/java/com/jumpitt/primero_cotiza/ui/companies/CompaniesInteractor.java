package com.jumpitt.primero_cotiza.ui.companies;

import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.manager.NetworkManager;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.CompaniesResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.utils.Constants.COMPANY_RESULT_FAILED;
import static com.jumpitt.primero_cotiza.utils.Constants.COMPANY_RESULT_SUCCESS;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_REFRESH_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_BODY;


public class CompaniesInteractor implements CompaniesContract.Interactor {

    private static final String TAG = CompaniesInteractor.class.getSimpleName();

    private CompaniesContract.InteractorOutput mInteractorOutput;
    NetworkManager mNetworkManager = new NetworkManager();


    public CompaniesInteractor(CompaniesContract.InteractorOutput output) {
        this.mInteractorOutput = output;
    }

    @Override
    public void getCompanies() {
        mNetworkManager.getCompanies(new NetworkManager.Listener<CompaniesResponse>() {

            @Override
            public void onSuccess(CompaniesResponse response) {
                mInteractorOutput.setCompanies(response.getData());
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                if (code == -1)
                    mInteractorOutput.companiesFailed(App.getContext().getResources().getString(R.string.conection_error));
                else
                    mInteractorOutput.companiesFailed(message);

            }

            @Override
            public void onSesionExpired() {
                mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                    @Override
                    public void onSuccess(RefreshTkResponse response) {
                        getCompanies();
                    }

                    @Override
                    public void onError(int code, String message, Throwable t) {
                        mInteractorOutput.refreshLogin();
                    }
                });

            }

        });
    }

    @Override
    public void companyEnabled(int companyId) {
        mNetworkManager.companyState(companyId, new NetworkManager.Listener<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response) {
                if (response.getCode() == 200)
                    mInteractorOutput.enableResult(COMPANY_RESULT_SUCCESS);
                else
                    mInteractorOutput.enableResult(COMPANY_RESULT_FAILED);
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.enableResult(COMPANY_RESULT_FAILED);

            }

            @Override
            public void onSesionExpired() {
                mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                    @Override
                    public void onSuccess(RefreshTkResponse response) {
                        companyEnabled(companyId);
                    }

                    @Override
                    public void onError(int code, String message, Throwable t) {
                        mInteractorOutput.refreshLogin();
                    }
                });

            }
        });

    }

    @Override
    public void companyDisabled(int companyId) {
        mNetworkManager.companyState(companyId, new NetworkManager.Listener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response.getCode() == 200)
                    mInteractorOutput.disableResult(COMPANY_RESULT_SUCCESS);
                else
                    mInteractorOutput.disableResult(COMPANY_RESULT_FAILED);
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.disableResult(COMPANY_RESULT_FAILED);
            }

            @Override
            public void onSesionExpired() {

                mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                    @Override
                    public void onSuccess(RefreshTkResponse response) {
                        companyDisabled(companyId);
                    }

                    @Override
                    public void onError(int code, String message, Throwable t) {
                        mInteractorOutput.refreshLogin();
                    }
                });

            }
        });
    }

    @Override
    public void finishSesion() {
        Hawk.delete(HAWK_USER_BODY);
        Hawk.delete(HAWK_TOKEN);
        Hawk.delete(HAWK_REFRESH_TOKEN);
        Hawk.delete(HAWK_FIREBASE_TOKEN);
    }
}
