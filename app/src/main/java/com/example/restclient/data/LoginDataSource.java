package com.example.restclient.data;

import com.example.restclient.RestClientUtils;
import com.example.restclient.data.model.LoggedInUser;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {

            // Call Rest API to verify user and password
            //RestClientUtils.get("countries/LC", null, new JsonHttpResponseHandler());

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}