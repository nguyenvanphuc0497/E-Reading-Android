package com.dtu.capstone2.ereading.ui.home;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;

/**
 * Create by Vo The Doan on 4/5/2019
 */
public class HomeFragmentViewModal {
    EReadingRepository eReadingRepository = new EReadingRepository();
    private String strReponse;
    private List<ListWord> listWord;

    public Single<DataStringReponse> getDataStringReponse(String para)
    {
        return eReadingRepository.GetDataStringReponse(para);
    }
}
