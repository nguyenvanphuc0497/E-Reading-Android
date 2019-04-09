package com.dtu.capstone2.ereading.ui.account.login;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;

import io.reactivex.Single;

/**
 * Create by Vo The Doan on 4/1/2019
 */
public class LoginViewModel {
    EReadingRepository ereadingrepository = new EReadingRepository();

    public Single<DataLoginRequest> GetDataLoginRequest(AccountLoginRequest accountLoginRequest)
    {
        return  ereadingrepository.GetDataLoginRequest(accountLoginRequest);
    }

}
