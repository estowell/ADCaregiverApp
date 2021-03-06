package edu.neu.ccs.wellness.adcaregiverapp.presentation.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ActivityLoginBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.usecase.LoginUser;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

/**
 * Created by amritanshtripathi on 6/14/18.
 */

public class LoginActivity extends DaggerAppCompatActivity {


    private ActivityLoginBinding binding;
    LoginViewModel viewModel;

    @Inject
    LoginViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {
        binding.loginProgress.setVisibility(View.VISIBLE);
        final Observer<LoginUser.ResponseValue> loginResponseObserver = new Observer<LoginUser.ResponseValue>() {
            @Override
            public void onChanged(@Nullable LoginUser.ResponseValue responseValue) {
                assert responseValue != null;
                assert responseValue.getResponse() != null;
                switch (responseValue.getResponse().getReponse()) {
                    case SUCCESS: {
                        binding.loginProgress.setVisibility(View.GONE);
                        navigateToMainActivity();
                        break;
                    }
                    case Error:

                        showErrorToast(responseValue.getMessage());
                    default:
                        Log.d(this.getClass().getSimpleName(), "Unknown Error");

                }
            }
        };

        Observer<Boolean> isUserLoggedIn = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    binding.loginProgress.setVisibility(View.GONE);
                    navigateToMainActivity();
                } else {
                    binding.loginProgress.setVisibility(View.GONE);
                }

            }
        };

        viewModel.getUserLoggedInLiveData().observe(this, isUserLoggedIn);
        viewModel.getLiveData().observe(this, loginResponseObserver);
        viewModel.isUserLoggedIn();
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loginProgress.setVisibility(View.VISIBLE);
                if (binding.password.getText().toString().equals("") || binding.username.getText().toString().equals("")) {
                    showErrorToast("Username or Password cannot be empty");
                } else {
                    viewModel.onLogin(
                            binding.username.getText().toString(),
                            binding.password.getText().toString());
                }
            }
        });
    }


    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showErrorToast(String message) {
        binding.loginProgress.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.username.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.password.getWindowToken(), 0);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
