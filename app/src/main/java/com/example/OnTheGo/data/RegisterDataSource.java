package com.example.OnTheGo.data;

import com.example.OnTheGo.data.model.RegisteredUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

    public Result<RegisteredUser> register(String firstname, String lastname, String username, String usernameConfirm, String password, String passwordConfirm) {

        try {
            // TODO: handle registeredUser authentication
            RegisteredUser fakeUser =
                    new RegisteredUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error registering user", e));
        }
    }
}
