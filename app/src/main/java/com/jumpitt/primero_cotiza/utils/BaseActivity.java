package com.jumpitt.primero_cotiza.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.jumpitt.primero_cotiza.R;

import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_NEGATIVE;
import static com.jumpitt.primero_cotiza.utils.Constants.TYPE_POSITIVE;


public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public void showSbMessage(View view, String message, int type){

        Snackbar snackbar = Snackbar.make(view, ""+message,Snackbar.LENGTH_LONG);


        if (type == TYPE_POSITIVE){
            View snackView = snackbar.getView();
            TextView snackText = snackView.findViewById(R.id.snackbar_text);

            snackView.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.purple_snackbar));
            snackText.setTextColor(getResources().getColor(R.color.white));
        }

        snackbar.setText(message);
        snackbar.show();
    }
}
