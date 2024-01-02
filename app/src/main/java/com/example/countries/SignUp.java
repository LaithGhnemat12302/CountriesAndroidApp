package com.example.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    public static final String USERNAME = "USERNAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String PASSWORD = "PASSWORD";
    public static final String CONFIRMPASSWORD = "CONFIRMPASSWORD";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPhoneNumber;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private TextView txtSignIn;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    // ________________________________________________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupViews();
        setupSharedPrefs();

        chkPreferences();
    }
    // ________________________________________________________________________________________________________________
    private void setupViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        txtSignIn = findViewById(R.id.txtSignIn);
    }
    // ________________________________________________________________________________________________________________
    private void setupSharedPrefs() {
        preference = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preference.edit();
    }
    // ________________________________________________________________________________________________________________
    private void chkPreferences() {
        flag = preference.getBoolean(FLAG, false);

        if(flag){
            String userName = preference.getString(USERNAME,  "");
            String email = preference.getString(EMAIL, "");
            String phone = preference.getString(PHONE, "");
            String password = preference.getString(PASSWORD, "");
            String confirmPassword = preference.getString(CONFIRMPASSWORD, "");

            edtUsername.setText(userName);
            edtEmail.setText(email);
            edtPhoneNumber.setText(phone);
            edtPassword.setText(password);
            edtConfirmPassword.setText(confirmPassword);
        }
    }
    // ________________________________________________________________________________________________________________
    public void btnSignUp(View view) {
        String userName = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString();
        String phone = edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString();

        boolean validData = true;

        if (userName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Sorry, you have to fill all fields", Toast.LENGTH_SHORT).show();
            validData = false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Sorry, the passwords aren't the same", Toast.LENGTH_SHORT).show();
            validData = false;
        }

        if (!validData)
            return;

        if (!flag) {
            editor.putString(USERNAME, userName);
            editor.putString(EMAIL, email);
            editor.putString(PHONE, phone);
            editor.putString(PASSWORD, password);
            editor.putString(CONFIRMPASSWORD, confirmPassword);
            editor.putBoolean(FLAG, true);
            editor.commit();
        }

        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    // ________________________________________________________________________________________________________________
    public void onSignInClick(View view) {
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    // ________________________________________________________________________________________________________________
    @Override
    public void onStop() {
        super.onStop();
        String userName = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String phone = edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        editor.putString(USERNAME, userName);
        editor.putString(EMAIL, email);
        editor.putString(PHONE, phone);
        editor.putString(PASSWORD, password);
        editor.putString(CONFIRMPASSWORD, confirmPassword);
        editor.putBoolean(FLAG, true);
        editor.commit();
    }
}