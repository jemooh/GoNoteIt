package com.skyz.noteit.repository.user;

import com.apollographql.apollo.api.Response;

import com.skyz.noteit.AuthenticateMutation;
import com.skyz.noteit.model.UserModel;
import io.reactivex.Observable;

public interface UserRepository {

    Observable<Response<AuthenticateMutation.Data>> authenticateUser(String login, String password);

    void saveUserAuthData(String login, String token);

    UserModel getLoggedInUser();

    void logoutUser();
}
