package com.dtu.capstone2.ereadingandroid.datasource.repository;

import com.dtu.capstone2.ereadingandroid.network.remote.EReadingRemoteDataSource;
import com.dtu.capstone2.ereadingandroid.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereadingandroid.network.response.Token;

import io.reactivex.Single;

/**
 * Create by Nguyen Van Phuc on 3/11/19
 */
public class EReadingRepository {
    private EReadingRemoteDataSource eReadingRemoteDataSource = new EReadingRemoteDataSource();

    public Single<Token> login(AccountLoginRequest account) {
        return eReadingRemoteDataSource.login(account);
    }
}
