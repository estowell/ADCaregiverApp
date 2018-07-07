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

        if (viewModel.isUserLoggedIn()) {
            navigateToMainActivity();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {

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
                        binding.loginProgress.setVisibility(View.GONE);
                        showErrorToast(responseValue);
                    default:
                        Log.d(this.getClass().getSimpleName(), "Unknown Error");

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


    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showErrorToast(LoginUser.ResponseValue responseValue) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.username.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.password.getWindowToken(), 0);
        Toast.makeText(this, responseValue.getMessage(), Toast.LENGTH_LONG).show();
    }

}
