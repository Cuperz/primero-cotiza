package com.jumpitt.primero_cotiza.manager;

import com.jumpitt.primero_cotiza.network.NetworkCallback;
import com.jumpitt.primero_cotiza.network.RestClient;
import com.jumpitt.primero_cotiza.network.request.LoginRequest;
import com.jumpitt.primero_cotiza.network.request.RefreshTkRequest;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.ClientsResponse;
import com.jumpitt.primero_cotiza.network.response.CompaniesResponse;
import com.jumpitt.primero_cotiza.network.response.LeadCheckResponse;
import com.jumpitt.primero_cotiza.network.response.LoginResponse;
import com.jumpitt.primero_cotiza.network.response.OfferResponse;
import com.jumpitt.primero_cotiza.network.response.ProfileResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.jumpitt.primero_cotiza.network.response.TimerResponse;
import com.orhanobut.hawk.Hawk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_REFRESH_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;

public class NetworkManager {

    private static final String Tag = NetworkManager.class.getSimpleName();

    public NetworkManager() {
    }

    public void doLogin(String email, String password, Listener<LoginResponse> listener) {

        final LoginRequest loginRequest = new LoginRequest(email, password);

        Call<LoginResponse> request = RestClient.get().signIn(loginRequest);
        request.enqueue(new NetworkCallback<>(listener));

    }

    public void getCompanies(Listener<CompaniesResponse> listener) {

        String token = Hawk.get(HAWK_TOKEN);

        Call<CompaniesResponse> getCompanyList = RestClient.get().getCompanies("Bearer " + token);
        getCompanyList.enqueue(new NetworkCallback<>(listener));

    }


    public void getProfile(Listener<ProfileResponse> listener) {

        String token = Hawk.get(HAWK_TOKEN);

        Call<ProfileResponse> getProfileData = RestClient.get().getProfile("Bearer " + token);
        getProfileData.enqueue(new NetworkCallback<>(listener));

    }

    public void getClients(Listener<ClientsResponse> listener) {

        String token = Hawk.get(HAWK_TOKEN);

        Call<ClientsResponse> getClientList = RestClient.get().getClients("Bearer " + token);
        getClientList.enqueue(new NetworkCallback<>(listener));
    }

    public void logOut(Listener<BaseResponse> listener) {

        String token = Hawk.get(HAWK_TOKEN);

        Call<BaseResponse> logOut = RestClient.get().logOut("Bearer " + token);
        logOut.enqueue(new NetworkCallback<>(listener));
    }


    public void companyState(int companyID, Listener<BaseResponse> listener) {

        String token = Hawk.get(HAWK_TOKEN);

        Call<BaseResponse> onOff = RestClient.get().onOffCompany("Bearer " + token, companyID);
        onOff.enqueue(new NetworkCallback<>(listener));

    }

    public void sendFbToken(Listener<BaseResponse> listener) {

        String token = Hawk.get(HAWK_TOKEN);
        String fbToken = Hawk.get(HAWK_FIREBASE_TOKEN);

        Call<BaseResponse> sendFbToken = RestClient.get().sendFbToken("Bearer " + token, fbToken);
        sendFbToken.enqueue(new NetworkCallback<>(listener));
    }

    public void getOffer(int offerId, Listener<OfferResponse> listener) {
        String token = Hawk.get(HAWK_TOKEN);

        Call<OfferResponse> offer = RestClient.get().getOffer("Bearer " + token, offerId);
        offer.enqueue(new NetworkCallback<>(listener));

    }

    public void getLeadId(Listener<LeadCheckResponse> listener) {
        String token = Hawk.get(HAWK_TOKEN);

        Call<LeadCheckResponse> leadId = RestClient.get().getLeadId("Bearer " + token);
        leadId.enqueue(new NetworkCallback<>(listener));
    }

    public void acceptLead(int offerId, Listener<BaseResponse> listener) {
        String token = Hawk.get(HAWK_TOKEN);

        Call<BaseResponse> acceptLead = RestClient.get().acceptLead("Bearer " + token, offerId);
        acceptLead.enqueue(new NetworkCallback<>(listener));
    }

    public void rejectLead(int offerId, Listener<BaseResponse> listener) {
        String token = Hawk.get(HAWK_TOKEN);

        Call<BaseResponse> rejectLead = RestClient.get().rejectLead("Bearer " + token, offerId);
        rejectLead.enqueue(new NetworkCallback<>(listener));
    }

    public void getTimer(Listener<TimerResponse> listener){
        String token = Hawk.get(HAWK_TOKEN);

        Call<TimerResponse> getTimer = RestClient.get().getTimer("Bearer "+token);
        getTimer.enqueue(new NetworkCallback<>(listener));

    }

    public void refreshTk(RefreshListener<RefreshTkResponse> listener) {

        String refreshTk = Hawk.get(HAWK_REFRESH_TOKEN);

        final RefreshTkRequest mRefreshTk = new RefreshTkRequest(refreshTk);

        Call<RefreshTkResponse> tokenRefresh = RestClient.get().refreshTk(mRefreshTk);
        tokenRefresh.enqueue(new Callback<RefreshTkResponse>() {
            @Override
            public void onResponse(Call<RefreshTkResponse> call, Response<RefreshTkResponse> response) {
                if (response.code() == 200) {

                    Hawk.put(HAWK_TOKEN, response.body().getAccToken());
                    Hawk.put(HAWK_REFRESH_TOKEN, response.body().getRefreshToken());
                    listener.onSuccess(response.body());

                } else
                    listener.onError(response.code(), response.message(), null);
            }

            @Override
            public void onFailure(Call<RefreshTkResponse> call, Throwable t) {
                listener.onError(-1, null, t);

            }
        });
    }

    public interface Listener<T> {

        void onSuccess(T response);

        void onError(int code, String message, Throwable t);

        void onSesionExpired();
    }

    public interface RefreshListener<T> {

        void onSuccess(T response);

        void onError(int code, String message, Throwable t);

    }


}
