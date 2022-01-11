package com.jumpitt.primero_cotiza.utils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;

public class DeviceRegister {

    private static final String TAG = DeviceRegister.class.getSimpleName();
    private static final DeviceRegister ourInstance = new DeviceRegister();

    public static DeviceRegister getInstance(){
        return ourInstance;
    }

    private DeviceRegister(){}

    public void registerDevice(){
        if (!Hawk.contains(HAWK_FIREBASE_TOKEN))
            Hawk.put(HAWK_FIREBASE_TOKEN, FirebaseInstanceId.getInstance().getToken());
    }
}
