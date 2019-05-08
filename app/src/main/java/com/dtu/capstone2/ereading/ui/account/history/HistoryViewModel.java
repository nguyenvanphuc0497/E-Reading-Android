package com.dtu.capstone2.ereading.ui.account.history;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.response.ListHistoryResponse;
import io.reactivex.Single;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
class HistoryViewModel {
    EReadingRepository eReadingRepository = new EReadingRepository();

    Single<ListHistoryResponse> getListHistory() {
        return eReadingRepository.getListHistory();
    }
}
