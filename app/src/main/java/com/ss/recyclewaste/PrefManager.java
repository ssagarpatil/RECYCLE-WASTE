package com.ss.recyclewaste;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String PREF_NAME       = "RechargeMasterPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_OWNER_ID     = "ownerId";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        pref   = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /* ---------- SAVE / READ SESSION ---------- */

    /** Call this after successful login */
    public void saveLogin(String ownerMobile) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_OWNER_ID, ownerMobile);
        editor.apply();
    }

    /** Compatibility alias (old code can still call this) */
    public void setOwnerLoggedIn(String ownerMobile) {
        saveLogin(ownerMobile);
    }

    /** Returns true if an owner is currently logged-in */
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /** Returns the logged-in owner’s mobile number (or empty string) */
    public String getOwnerId() {
        return pref.getString(KEY_OWNER_ID, "");
    }

    /** Clears all stored session data */
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
