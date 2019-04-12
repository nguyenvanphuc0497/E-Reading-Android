package com.dtu.capstone2.ereading.datasource.repository;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by Nguyen Van Phuc on 2/20/19
 */
public class LocalRepository {
    private final static String E_READING_SHARED_PREFERENCE = "e_reading_shared_preference";
    private final static String KEY_TOKEN_USER = "key_token_user";

    private Context mContext;
    private SharedPreferences mShaPre;

    public LocalRepository(Context context) {
        mContext = context;
        mShaPre = context.getSharedPreferences(E_READING_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    void saveTokenUser(String token) {
        SharedPreferences.Editor editor = mShaPre.edit();
        editor.putString(KEY_TOKEN_USER, "Token " + token);
        editor.apply();
    }

    public String getTokenUser() {
        return mShaPre.getString(KEY_TOKEN_USER, "");
    }

    void clearTokenUser() {
        mShaPre.edit().remove(KEY_TOKEN_USER).apply();
    }
}
