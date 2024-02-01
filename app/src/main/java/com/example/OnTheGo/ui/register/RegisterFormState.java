package com.example.OnTheGo.ui.register;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegisterFormState {
    @Nullable
    private Integer firstnameError;
    @Nullable
    private Integer lastnameError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer usernameMatchError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer passwordMatchError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer firstnameError, @Nullable Integer lastnameError, @Nullable Integer usernameError, @Nullable Integer usernameMatchError, @Nullable Integer passwordError, @Nullable Integer passwordMatchError) {
        this.firstnameError = firstnameError;
        this.lastnameError = lastnameError;
        this.usernameError = usernameError;
        this.usernameMatchError = usernameMatchError;
        this.passwordError = passwordError;
        this.passwordMatchError = passwordMatchError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getFirstnameError() {
        return firstnameError;
    }

    @Nullable
    Integer getLastnameError() {
        return lastnameError;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getUsernameMatchError() {
        return usernameMatchError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getPasswordMatchError() {
        return passwordMatchError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
