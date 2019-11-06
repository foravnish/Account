package com.accountapp.accounts.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object Prefences {

    val SHARED_PREF_NAME = "preferences_name"

    lateinit var userId: SharedPreferences
    lateinit var isLogin: SharedPreferences
    lateinit var userName: SharedPreferences
    lateinit var userMobile: SharedPreferences
    lateinit var userEmailId: SharedPreferences
    lateinit var userImage: SharedPreferences
    lateinit var usgerGender: SharedPreferences
    lateinit var userAge: SharedPreferences
    lateinit var pinCode: SharedPreferences
    lateinit var pinCodeTemp: SharedPreferences
    lateinit var appLock: SharedPreferences

    var USER_ID = "USER_ID"
    var IS_LOGIN = "IS_LOGIN"
    var USER_NAME = "USER_NAME"
    var USER_MOBILE = "USER_MOBILE"
    var USER_EMAIL_ID = "USER_EMAIL_ID"
    var USER_IMAGE = "USER_IMAGE"
    var USER_GENDER = "USER_GENDER"
    var USER_AGE = "USER_AGE"
    var PIN_CODE = "PIN_CODE"
    var PIN_CODE_TEMP = "PIN_CODE_TEMP"
    var APP_LOCK_PASSWORD = "APP_LOCK_PASSWORD"


    fun resetUserData(ctx: Context){
        setIsLogin(ctx,false)
        setUserId(ctx,"")
        setUserEmailId(ctx,"")
        setUserMobile(ctx,"")
        setCompany(ctx,"")
        setGST_No(ctx,"")
        setUserImage(ctx,"")
        setAddress(ctx, "")
        setCity(ctx, "")
    }


    // pref is Login
    fun setIsLogin(context: Context, iss: Boolean) {
        isLogin = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = isLogin.edit()
        editor.putBoolean(IS_LOGIN, iss)
        editor.commit()
    }

    @JvmStatic
    fun getIsLogin(context: Context): Boolean {
        isLogin = PreferenceManager.getDefaultSharedPreferences(context)
        return isLogin.getBoolean(IS_LOGIN, false)
    }




    // pref UserId
    fun setUserId(context: Context, `is`: String) {
        userId = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = userId.edit()
        editor.putString(USER_ID, `is`)
        editor.commit()
    }

    fun getUserId(context: Context): String? {
        userId = PreferenceManager.getDefaultSharedPreferences(context)
        return userId.getString(USER_ID, "")
    }

    // pref User Name
    fun setUserName(context: Context, `is`: String) {
        userName = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = userName.edit()
        editor.putString(USER_NAME, `is`)
        editor.commit()
    }

    fun getUserName(context: Context): String? {
        userName = PreferenceManager.getDefaultSharedPreferences(context)
        return userName.getString(USER_NAME, "")
    }

    // pref User Mobile
    fun setUserMobile(context: Context, `is`: String) {
        userMobile = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = userMobile.edit()
        editor.putString(USER_MOBILE, `is`)
        editor.commit()
    }

    fun getUserMobile(context: Context): String? {
        userMobile = PreferenceManager.getDefaultSharedPreferences(context)
        return userMobile.getString(USER_MOBILE, "")
    }

    // pref User Email ID
    fun setUserEmailId(context: Context, `is`: String) {
        userEmailId = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = userEmailId.edit()
        editor.putString(USER_EMAIL_ID, `is`)
        editor.commit()
    }

    fun getUserEmailId(context: Context): String? {
        userEmailId = PreferenceManager.getDefaultSharedPreferences(context)
        return userEmailId.getString(USER_EMAIL_ID, "")
    }

    // pref Image
    fun setUserImage(context: Context, `is`: String) {
        userImage = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = userImage.edit()
        editor.putString(USER_IMAGE, `is`)
        editor.commit()
    }

    fun getUserImage(context: Context): String? {
        userImage = PreferenceManager.getDefaultSharedPreferences(context)
        return userImage.getString(USER_IMAGE, "")
    }

    // pref City
    fun setCity(context: Context, `is`: String) {
        usgerGender = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = usgerGender.edit()
        editor.putString(USER_GENDER, `is`)
        editor.commit()
    }

    fun getCity(context: Context): String? {
        usgerGender = PreferenceManager.getDefaultSharedPreferences(context)
        return usgerGender.getString(USER_GENDER, "")
    }

    // pref GST No
    fun setGST_No(context: Context, `is`: String) {
        userAge = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = userAge.edit()
        editor.putString(USER_AGE, `is`)
        editor.commit()
    }

    fun getGST_No(context: Context): String? {
        userAge = PreferenceManager.getDefaultSharedPreferences(context)
        return userAge.getString(USER_AGE, "")
    }

    // pref Address Value
    fun setAddress(context: Context, `is`: String) {
        pinCode = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pinCode.edit()
        editor.putString(PIN_CODE, `is`)
        editor.commit()
    }

    fun getAddress(context: Context): String? {
        pinCode = PreferenceManager.getDefaultSharedPreferences(context)
        return pinCode.getString(PIN_CODE, "")
    }


//    Temp Only
    // pref Company Value
    fun setCompany(context: Context, `is`: String) {
        pinCodeTemp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pinCodeTemp.edit()
        editor.putString(PIN_CODE_TEMP, `is`)
        editor.commit()
    }

    fun getCompany(context: Context): String? {
        pinCodeTemp = PreferenceManager.getDefaultSharedPreferences(context)
        return pinCodeTemp.getString(PIN_CODE_TEMP, "")
    }


// Set App Lock Password
    fun setAppLock(context: Context, iss: Boolean) {
    appLock = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = appLock.edit()
        editor.putBoolean(APP_LOCK_PASSWORD, iss)
        editor.commit()
    }

    fun getAppLock(context: Context): Boolean {
        appLock = PreferenceManager.getDefaultSharedPreferences(context)
        return appLock.getBoolean(APP_LOCK_PASSWORD, true)
    }



}