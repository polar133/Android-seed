package com.polar.android_seed.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.polar.android_seed.activities.LoginActivity;


public class LogoutHelper {


    private static LogoutHelper instance;
    Context context;
    SharedPreferences preferencias;

    public LogoutHelper(Context context) {
        this.context = context;
    }

    public static void init(Context ctx) {
        if (null == instance) {
            instance = new LogoutHelper(ctx);
        }
    }

    public static LogoutHelper getInstance() {
        return instance;
    }


    public void CerrarSesion() {
        preferencias = context.getSharedPreferences("AppPref", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefEditor = preferencias.edit();
        prefEditor.clear();
        prefEditor.apply();
        DBHelper.getInstance().onDelete();


        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("EXIT", true);
        context.startActivity(intent);
    }
}
