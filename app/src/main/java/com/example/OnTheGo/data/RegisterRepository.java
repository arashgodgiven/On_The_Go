package com.example.OnTheGo.data;

import com.example.OnTheGo.data.model.RegisteredUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of register status and user credentials information.
 */
public class RegisterRepository {
    private static volatile RegisterRepository instance;

    private RegisterDataSource dataSource;

    private RegisteredUser user = null;

    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public boolean isRegistered() {
        return user != null;
    }

    private void setRegisteredUser(RegisteredUser user) {
        this.user = user;
    }

    public Result<RegisteredUser> register(String firstname, String lastname, String username, String usernameConfirm, String password, String passwordConfirm) {
        Result<RegisteredUser> result = dataSource.register(firstname, lastname, username, usernameConfirm, password, passwordConfirm);
        if (result instanceof Result.Success) {
            setRegisteredUser(((Result.Success<RegisteredUser>) result).getData());
        }
        return result;
    }
}
