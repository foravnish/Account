package com.accountapp.accounts.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.accountapp.accounts.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    private final Context mContext;

    public static boolean isNoValid(EditText textView, String msg) {
        String source = textView.getText().toString().trim();
        if (source.length() < 8 || source.length() > 16) {
            showAlert(textView, ALERT_TYPE.SNACK_BAR, msg);
            return false;
        }
        return true;
    }

    public static boolean isNoValidWithNumber(EditText textView, String msg, int min,int max) {
        String source = textView.getText().toString().trim();
        if (source.length() < min || source.length() > max) {
            showAlert(textView, ALERT_TYPE.SNACK_BAR, msg);
            return false;
        }
        return true;
    }

    public static boolean isValidPassword(EditText textView) {
        String password = textView.getText().toString().trim();
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()_=+~%?])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    public enum ALERT_TYPE {TOAST, SNACK_BAR}

    public ValidationHelper(Context context) {
        mContext = context;
    }

    public static boolean isBlank(@NonNull TextView targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        if (source.isEmpty()) {
            showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
            return true;
        }
        return false;
    }

    public static boolean isFieldBlank(@NonNull TextView targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        if (source.isEmpty()) {
            showToast(targetEditText.getContext(), msg);
            return true;
        }
        return false;
    }

    public static boolean isImageBlank(@NonNull ImageView imageview, String msg) {
        Drawable source = imageview.getDrawable();
        if (source == null) {
            showAlert(imageview, ALERT_TYPE.SNACK_BAR, msg);
            return true;
        }
        return false;
    }

    public static boolean isImageEmpty(@NonNull ImageView imageview, String msg) {
        Drawable source = imageview.getDrawable();
        if (source == null) {
            showToast(imageview.getContext(), msg);
            return true;
        }
        return false;
    }

    public static boolean isBlank(@NonNull TextView textView, String msg, boolean showToast) {
        String source = textView.getText().toString().trim();
        if (source.isEmpty() && showToast) {
            showToast(textView.getContext(), msg);
            return true;
        }
        return false;
    }

    public static boolean isDataFilled(EditText editText, String errorMessage, View view) {
        if (editText != null && view != null) {
            String inputText = editText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                showAlert(editText, ALERT_TYPE.SNACK_BAR, errorMessage);
                return false;
            } else
                return true;
        }
        return false;
    }

    public static boolean isDataFilled(TextView txtVw, String errorMessage, View view) {
        if (txtVw != null && view != null) {
            String inputText = txtVw.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                showAlert(txtVw, ALERT_TYPE.SNACK_BAR, errorMessage);
                return false;
            } else
                return true;
        }
        return false;
    }


    /**
     * This method returns true if a edit text contains valid email ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isEmailValid(@NonNull EditText targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
        return false;
    }

    public static boolean isEmailValid(@NonNull EditText targetEditText, String msg, boolean showToast) {
        String source = targetEditText.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        if (showToast)
            showAlert(targetEditText, ALERT_TYPE.TOAST, msg);
        return false;
    }

    public static boolean isEmailValid(String source) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmailValid(@NonNull EditText targetEditText, String errorMessage, View view) {
        String source = targetEditText.getText().toString();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, errorMessage);
        return false;
    }

    /**
     * This method returns true if a edit text contains any digit in it ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isContainDigit(@NonNull EditText targetEditText, ALERT_TYPE alertType, String msg, boolean msgType) {
        String pattern = ".*\\d.*";
        String source = targetEditText.getText().toString().trim();
        if (source.matches(pattern)) {
            if (msgType) {
                showAlert(targetEditText, alertType, msg);
            }
            return true;
        } else {
            if (!msgType) {
                showAlert(targetEditText, alertType, msg);
            }
            return false;
        }
    }

    public static boolean isEqual(@NonNull EditText sourceEditText, @NonNull EditText destinationEditText, ALERT_TYPE alertType, String msg, boolean msgType) {

        String source = sourceEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        if (source.equalsIgnoreCase(destination)) {
            if (msgType) {
                showAlert(destinationEditText, alertType, msg);
            }
            return true;
        } else {
            if (!msgType) {
                showAlert(destinationEditText, alertType, msg);
            }
            return false;
        }

    }

    public static void showToast(Context context, String msg) {
        if (context != null)
            Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*private static void showAlert(EditText targetEditText, View parentLayout, String msg) {
        View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
        targetEditText.requestFocus();
        *//*YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn((View) targetEditText.getParent());*//*
        showSnackBar(v, msg);
    }*/

    private static void showAlert(Context context, String msg) {

    }

    public static void showAlert(TextView targetEditText, ALERT_TYPE alertType, String msg) {
        Utility.Companion.closeKeyboard(targetEditText, targetEditText.getContext());
        //View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
        targetEditText.requestFocus();
        if (alertType == ALERT_TYPE.TOAST) {
            showToast(targetEditText.getContext(), msg);
        } else if (alertType == ALERT_TYPE.SNACK_BAR) {
            showSnackBar(targetEditText, msg);
        }


       /* YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn((View) targetEditText.getParent());*/

    }

    private static void showAlert(ImageView targetEditText, ALERT_TYPE alertType, String msg) {
        //View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
        targetEditText.requestFocus();
        if (alertType == ALERT_TYPE.TOAST) {
            showToast(targetEditText.getContext(), msg);
        } else if (alertType == ALERT_TYPE.SNACK_BAR) {
            showSnackBar(targetEditText, msg);
        }



        
       /* YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn((View) targetEditText.getParent());*/

    }

    private static void showAlertWithoutFocus(TextView targetEditText, ALERT_TYPE alertType, String msg) {
        //View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
        if (alertType == ALERT_TYPE.TOAST) {
            showToast(targetEditText.getContext(), msg);
        } else if (alertType == ALERT_TYPE.SNACK_BAR) {
            showSnackBar(targetEditText, msg);
        }

       /* YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn((View) targetEditText.getParent());*/

    }

    public static void showSnackBar(View parentLayout, String msg) {
        if (parentLayout != null) {
            final Snackbar snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT);
            snackBar.setActionTextColor(Color.WHITE);
            View view = snackBar.getView();
            TextView tv = (TextView)
                    view.findViewById(R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackBar.show();
        }
    }

    public static boolean hasMinimumLength(String source, int length) {
        if (source.trim().length() >= length)
            return true;
        return false;
    }

    public static boolean hasMinimumLength(EditText editText, int length, String message) {
        if (!hasMinimumLength(editText.getText().toString().trim(), length)) {
            showAlert(editText, ALERT_TYPE.SNACK_BAR, message);
            return false;
        }
        return true;

    }

    public static InputFilter getBlockedSpecialCharacterFilter() {
        final String blockCharacterSet = "~#^|$%&*!@+_-1234567890";
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };

    }

    public static boolean isValidName(TextView textView, String message) {
        String targetString = textView.getText().toString().trim();
        String regx = "^[\\p{L} .'-]+$";
        if (Pattern.matches(regx, targetString)) {
            return true;
        }
        showAlert(textView, ALERT_TYPE.SNACK_BAR, message);
        return false;
    }

    public static boolean hasMinimumwords(EditText editText, ALERT_TYPE alertType, int length, String message) {
        if (editText.getText().toString().trim().length() >= length) {
            showAlert(editText, alertType, message);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidURL(EditText mFeedEditText, ALERT_TYPE alertType, String msg) {

        String url = mFeedEditText.getText().toString().toLowerCase();
        if (Patterns.WEB_URL.matcher(url).matches()) {
            return true;
        } else {
            showAlert(mFeedEditText, alertType, msg);
            return false;
        }
    }

    public boolean isInputFieldEmpty(String emailid, String password) {
        boolean empty = false;
        if (emailid.isEmpty() || password.isEmpty()) {
            empty = true;
        }
        return empty;
    }

    public boolean isTextFieldEmpty(TextView ed, String msg) {
        if (ed != null) {

            if (ed.getVisibility() != View.GONE) {
                String uname = ed.getText().toString().trim();
                if (uname.equals("") || uname.length() <= 0) {
                    showAlert(ed, ALERT_TYPE.SNACK_BAR, msg);

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean validatePasswordSameFields(EditText password, EditText confPassword, String message, View view) {
        boolean status = false;
        status = password.getText().toString().equals(confPassword.getText().toString());
        if (!status) {
            showAlert(password, ALERT_TYPE.SNACK_BAR, message);
        }
        return status;
    }



    public boolean isValidUserName(EditText ed, String msg, boolean isFocus) {

        String s = ed.getText().toString().trim();
        String patternAlphabet = ".*[a-zA-Z]+.*";
        String patternNumber = ".*\\d+.*";


        if (!(s.length() > 5 && s.length() < 21)) {
            if (isFocus)
                showAlert(ed, ALERT_TYPE.SNACK_BAR, "Username must be between 6-20 characters.");
            else
                showAlertWithoutFocus(ed, ALERT_TYPE.SNACK_BAR, "Username must be between 6-20 characters.");
        } else if (s.contains(" ")) {
            if (isFocus)
                showAlert(ed, ALERT_TYPE.SNACK_BAR, "Space is not allowed between username.");
            else
                showAlertWithoutFocus(ed, ALERT_TYPE.SNACK_BAR, "Space is not allowed between username.");
        } else if (!(s.matches(patternNumber) && s.matches(patternAlphabet))) {
            if (isFocus)
                showAlert(ed, ALERT_TYPE.SNACK_BAR, "Username can only consists of alphabets and numbers.");
            else
                showAlertWithoutFocus(ed, ALERT_TYPE.SNACK_BAR, "Username can only consists of alphabets and numbers.");

        } else
            return true;

        return false;


    }

    public boolean isValidProfileName(EditText ed) {

        String s = ed.getText().toString().trim();
        String patternAlphabet = ".*[a-zA-Z]+.*";
        String patternNumber = ".*\\d+.*";


        if (!(s.length() > 1 && s.length() < 61)) {
            showAlert(ed, ALERT_TYPE.SNACK_BAR, "Name must be between 2-60 alphabets.");
        } else if (!s.matches(patternAlphabet)) {
            showAlert(ed, ALERT_TYPE.SNACK_BAR, "Only alphabets are allowed in Name.");

        } else
            return true;

        return false;


    }

}
