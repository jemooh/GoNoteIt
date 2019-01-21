package com.skyz.noteit.ui.login;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.apollographql.apollo.api.Response;

import javax.inject.Inject;

import com.skyz.noteit.AuthenticateMutation;
import com.skyz.noteit.repository.Resource;
import com.skyz.noteit.repository.user.UserRepository;
import com.skyz.noteit.rx.RxSchedulers;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    private UserRepository userRepository;
    private UserValidator userValidator;
    private RxSchedulers rxSchedulers;

    private String login, password;

    private MutableLiveData<Boolean> inputsValid = new MutableLiveData<>();
    private MutableLiveData<Resource<AuthenticateMutation.Data>> authenticationResource = new MutableLiveData<>();

    @Inject
    public LoginViewModel(UserRepository userRepository, UserValidator userValidator, RxSchedulers rxSchedulers) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.rxSchedulers = rxSchedulers;

        init();
    }

    private void init() {
        validate();
    }

    public LiveData<Boolean> areInputsValid() {
        return inputsValid;
    }

    public void setLogin(String login) {
        this.login = login;
        validate();
    }

    private void validate() {
        boolean valid = userValidator.isUserValid(login, password);
        Timber.d("Validated input %s and %s. Result: %b", login, password, valid);

        inputsValid.postValue(valid);
    }

    public void setPassword(String password) {
        this.password = password;
        validate();
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<Resource<AuthenticateMutation.Data>> login() {
        userRepository
                .authenticateUser(login, password)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.androidMainThread())
                .doOnSubscribe(it -> authenticationResource.postValue(Resource.loading(null)))
                .subscribe(
                        this::processAuthResponse,
                        error -> {
                            authenticationResource.postValue(Resource.error(error));
                            Timber.d("Login error: %s", error.getLocalizedMessage());
                        });

        return authenticationResource;
    }

    private void processAuthResponse(Response<AuthenticateMutation.Data> authMutation) {
        AuthenticateMutation.TokenAuth tokenAuth = authMutation.data().tokenAuth();

        if (tokenAuth != null && userValidator.isTokenValid(tokenAuth.token())) {
            authenticationResource.postValue(Resource.success(authMutation.data()));
            userRepository.saveUserAuthData(login, tokenAuth.token());
        } else {
            authenticationResource.postValue(Resource.error(authMutation.errors().get(0)));
        }
    }
}