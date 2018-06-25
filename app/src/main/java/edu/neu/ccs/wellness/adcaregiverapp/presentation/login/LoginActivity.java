package edu.neu.ccs.wellness.adcaregiverapp.presentation.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ActivityLoginBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.UseCase;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase.LoginUser;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

/**
 * Created by amritanshtripathi on 6/14/18.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String SHAREDPREF_NAME = "wellness_user";
    private static final String SHARED_PREFS = "WELLNESS";

    private ActivityLoginBinding binding;
    private LoginUser loginUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isUserLoggedIn()){
            navigateToMainActivity();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init(){
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser = new LoginUser(new UseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        saveUser();
                        binding.loginProgress.setVisibility(View.GONE);
                        navigateToMainActivity();
                    }

                    @Override
                    public void onError(Object response) {
                        binding.loginProgress.setVisibility(View.GONE);
                        Log.d("Login", "error");
                    }
                });

                loginUser.setRequestValues(new LoginUser.RequestValues(binding.username.getText().toString(), binding.password.getText().toString()));
                loginUser.run();
                binding.loginProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.contains(SHAREDPREF_NAME);
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHAREDPREF_NAME, true);
        editor.commit();
    }
}
