package com.example.OnTheGo.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.OnTheGo.data.RegisterRepository;
import com.example.OnTheGo.data.Result;
import com.example.OnTheGo.data.model.RegisteredUser;
import com.example.OnTheGo.R;

import java.util.Objects;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String firstname, String lastname, String username, String usernameConfirm, String password, String passwordConfirm) {
        // can be launched in a separate asynchronous job
        Result<RegisteredUser> result = registerRepository.register(firstname, lastname, username, usernameConfirm, password, passwordConfirm);

        if (result instanceof Result.Success) {
            RegisteredUser data = ((Result.Success<RegisteredUser>) result).getData();
            registerResult.setValue(new RegisterResult(new RegisteredUserView(data.getDisplayName())));
        } else {
            registerResult.setValue(new RegisterResult(R.string.register_failed));
        }
    }

    public void registerDataChanged(String firstname, String lastname, String username, String usernameConfirm, String password, String passwordConfirm) {
        if (!isFirstNameValid(firstname)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_firstname, null, null, null, null, null));
        } else if (!isLastNameValid(lastname)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_lastname, null, null, null, null));
        } else if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_username, null, null, null));
        } else if (!userNameConfirmMatches(username, usernameConfirm)){
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.username_match_error, null, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.invalid_password, null));
        } else if (!passwordConfirmMatches(password, passwordConfirm)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, null, R.string.password_match_error));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    private boolean isFirstNameValid(String firstname) {
        if (firstname == null) {
            return false;
        }
        if (firstname.matches(".*\\d.*") || !firstname.matches("[a-zA-Z]+")) {
            return false;
        } else {
            return !firstname.trim().isEmpty();
        }
    }

    private boolean isLastNameValid(String lastname) {
        if (lastname == null) {
            return false;
        }
        if (lastname.matches(".*\\d.*") || !lastname.matches("[a-zA-Z]+")) {
            return false;
        } else {
            return !lastname.trim().isEmpty();
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean userNameConfirmMatches(String username, String usernameConfirm) {
        return Objects.equals(username, usernameConfirm);
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
//        return password != null;
    }

    private boolean passwordConfirmMatches(String password, String passwordConfirm) {
        return Objects.equals(password, passwordConfirm);
    }
}
