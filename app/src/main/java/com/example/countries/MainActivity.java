package com.example.countries;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chk;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    // ________________________________________________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupSharedPrefs();
        checkPreferences();
    }
    // ________________________________________________________________________________________________________________
    private void checkPreferences() {
        flag = preference.getBoolean(FLAG, false);

        if(flag){
            String password = preference.getString(SignUp.PASSWORD, "");
            edtUsername.setText(SignUp.USERNAME);
            edtPassword.setText(password);
            chk.setChecked(true);
        }
    }
    // ________________________________________________________________________________________________________________
    private void setupSharedPrefs() {
        preference= PreferenceManager.getDefaultSharedPreferences(this);
        editor = preference.edit();
    }
    // ________________________________________________________________________________________________________________
    private void setupViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chk = findViewById(R.id.chk);
    }
    // ________________________________________________________________________________________________________________
    public void btnSignIn(View view) {
//        String username = edtUsername.getText().toString();
        String username = SignUp.USERNAME;
        String password = edtPassword.getText().toString();

        if(chk.isChecked()){
            if(!flag) {
                editor.putString(SignUp.USERNAME, username);
                editor.putString(SignUp.PASSWORD, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }
        }

        if(username.isEmpty() || password.isEmpty())
            Toast.makeText(this, "Fill the fields please", Toast.LENGTH_SHORT).show();

        else {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            finish();
        }
    }
    // ________________________________________________________________________________________________________________
    public void onSignUpClick(View view) {
        Intent intent = new Intent(MainActivity.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}