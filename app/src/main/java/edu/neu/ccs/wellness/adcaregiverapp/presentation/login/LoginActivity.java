package edu.neu.ccs.wellness.adcaregiverapp.presentation.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.services.model.LoginResponse;

/**
 * Created by amritanshtripathi on 6/14/18.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String SHAREDPREF_NAME = "wellness_user";
    private static final String SHARED_PREFS = "WELLNESS";

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        if (isUserLoggedIn()) {
            navigateToMainActivity();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {

        final Observer<LoginResponse> loginResponseObserver = new Observer<LoginResponse>() {
            @Override
            public void onChanged(@Nullable LoginResponse loginResponse) {
                switch (loginResponse.getReponse()) {
                    case SUCCESS: {
                        binding.loginProgress.setVisibility(View.GONE);
                        loginResponse.getUser().saveInstance(getApplicationContext());
                        navigateToMainActivity();
                        break;
                    }
                    case WRONG_CREDENTIALS: {
                        Log.d(this.getClass().getSimpleName(), "Wrong Credentials");
                        break;
                    }
                    case IO_ERROR: {
                        Log.d(this.getClass().getSimpleName(), "I/O Error");
                    }
                    default: Log.d(this.getClass().getSimpleName(), "Unknown Error");

                }
            }
        };

        viewModel.getLiveData().observe(this, loginResponseObserver);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loginProgress.setVisibility(View.VISIBLE);
                viewModel.onLogin(
                        binding.username.getText().toString(),
                        binding.password.getText().toString());
            }
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return preferences.contains(SHAREDPREF_NAME);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
