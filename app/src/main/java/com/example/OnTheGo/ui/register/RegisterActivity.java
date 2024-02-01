package com.example.OnTheGo.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.OnTheGo.R;
import com.example.OnTheGo.databinding.ActivityRegisterBinding;
import com.example.OnTheGo.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        final EditText firstnameChooseEditText = binding.firstnameChoose;
        final EditText lastnameChooseEditText = binding.lastnameChoose;
        final EditText usernameChooseEditText = binding.usernameChoose;
        final EditText usernameConfirmEditText = binding.usernameConfirm;
        final EditText passwordChooseEditText = binding.passwordChoose;
        final EditText passwordConfirmEditText = binding.passwordConfirm;
        final Button registerButton = binding.register;

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getFirstnameError() != null) {
                    firstnameChooseEditText.setError(getString(registerFormState.getFirstnameError()));
                }
                if (registerFormState.getLastnameError() != null) {
                    lastnameChooseEditText.setError(getString(registerFormState.getLastnameError()));
                }
                if (registerFormState.getUsernameError() != null) {
                    usernameChooseEditText.setError(getString(registerFormState.getUsernameError()));
//                    usernameConfirmEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getUsernameMatchError() != null) {
//                    usernameChooseEditText.setError(getString(registerFormState.getUsernameMatchError()));
                    usernameConfirmEditText.setError(getString(registerFormState.getUsernameMatchError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordChooseEditText.setError(getString(registerFormState.getPasswordError()));
//                    passwordConfirmEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getPasswordMatchError() != null) {
//                    passwordChooseEditText.setError(getString(registerFormState.getPasswordMatchError()));
                    passwordConfirmEditText.setError(getString(registerFormState.getPasswordMatchError()));
                }
            }
        });

        registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
            @Override
            public void onChanged(@Nullable RegisterResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                if (registerResult.getError() != null) {
                    showRegisterFailed(registerResult.getError());
                }
                if (registerResult.getSuccess() != null) {
                    updateUiWithUser(registerResult.getSuccess());


                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                    firstnameChooseEditText.getText().clear();
                    lastnameChooseEditText.getText().clear();
                    usernameChooseEditText.getText().clear();
                    usernameConfirmEditText.getText().clear();
                    passwordChooseEditText.getText().clear();
                    passwordConfirmEditText.getText().clear();

                    Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(firstnameChooseEditText.getText().toString(),
                        lastnameChooseEditText.getText().toString(),
                        usernameChooseEditText.getText().toString(),
                        usernameConfirmEditText.getText().toString(),
                        passwordChooseEditText.getText().toString(),
                        passwordConfirmEditText.getText().toString());
            }
        };
        firstnameChooseEditText.addTextChangedListener(afterTextChangedListener);
        lastnameChooseEditText.addTextChangedListener((afterTextChangedListener));
        usernameChooseEditText.addTextChangedListener(afterTextChangedListener);
        usernameConfirmEditText.addTextChangedListener(afterTextChangedListener);
        passwordChooseEditText.addTextChangedListener(afterTextChangedListener);
        passwordChooseEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(firstnameChooseEditText.getText().toString(),
                            lastnameChooseEditText.getText().toString(),
                            usernameChooseEditText.getText().toString(),
                            usernameConfirmEditText.getText().toString(),
                            passwordChooseEditText.getText().toString(),
                            passwordConfirmEditText.getText().toString());
                }
                return false;
            }
        });
        passwordConfirmEditText.addTextChangedListener(afterTextChangedListener);
        passwordConfirmEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(firstnameChooseEditText.getText().toString(),
                            lastnameChooseEditText.getText().toString(),
                            usernameChooseEditText.getText().toString(),
                            usernameConfirmEditText.getText().toString(),
                            passwordChooseEditText.getText().toString(),
                            passwordConfirmEditText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewModel.register(firstnameChooseEditText.getText().toString(),
                        lastnameChooseEditText.getText().toString(),
                        usernameChooseEditText.getText().toString(),
                        usernameConfirmEditText.getText().toString(),
                        passwordChooseEditText.getText().toString(),
                        passwordConfirmEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(RegisteredUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO: initiate successful registered experience
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
