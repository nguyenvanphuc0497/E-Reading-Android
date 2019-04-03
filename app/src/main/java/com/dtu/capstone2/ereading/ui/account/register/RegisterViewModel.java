package com.dtu.capstone2.ereading.ui.account.register;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.AccountRegisterRequest;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 4/2/19
 */
public class RegisterViewModel {
    private EReadingRepository mEReadingRepository;

    RegisterViewModel(EReadingRepository eReadingRepository) {
        mEReadingRepository = eReadingRepository;
    }

    public Single<AccountRegisterRequest> createNewAccount(AccountRegisterRequest accountRegisterRequest) {
        return mEReadingRepository.registerNewAccount(accountRegisterRequest);
    }
}
