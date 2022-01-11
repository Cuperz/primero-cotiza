package com.jumpitt.primero_cotiza.network;

import androidx.annotation.NonNull;

import com.jumpitt.primero_cotiza.manager.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCallback<T> implements Callback<T> {
    private NetworkManager.Listener<T> mListener;

    public NetworkCallback(NetworkManager.Listener<T> mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            mListener.onSuccess(response.body());
        }
        else if(response.code() ==401){
            mListener.onSesionExpired();
        }
        else {
            mListener.onError(response.code(), response.message(), null);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) { mListener.onError(-1, null, t); }
}
