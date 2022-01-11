package com.jumpitt.primero_cotiza.utils;


import android.content.Intent;


import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jumpitt.primero_cotiza.ui.login.LoginActivity;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.ui.login.LoginActivity.ORIGIN_PUSH;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;

public class PushMessage extends FirebaseMessagingService {

    private static final String TAG = PushMessage.class.getSimpleName();


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Hawk.contains(HAWK_FIREBASE_TOKEN) && Hawk.contains(HAWK_TOKEN)) {

            Intent intent = new Intent(this, LoginActivity.class).putExtra("from", ORIGIN_PUSH);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }
}