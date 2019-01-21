package com.skyz.noteit.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import com.skyz.noteit.model.UserModel;
import com.skyz.noteit.repository.user.UserRepository;

public class MainViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<UserModel> loggedInUser = new MutableLiveData<>();

    @Inject
    public MainViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MutableLiveData<UserModel> getLoggedInUser() {
        loggedInUser.postValue(userRepository.getLoggedInUser());

        return loggedInUser;
    }

    public void logoutUser() {
        userRepository.logoutUser();

        getLoggedInUser();
    }
}
