package com.dtu.capstone2.ereading.ui.account.history;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.response.HistoryNewFeed;
import com.dtu.capstone2.ereading.network.response.ListHistoryResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Create by Huynh Vu Ha Lan on 06/05/2019
 */
class HistoryViewModel {
    private EReadingRepository eReadingRepository = new EReadingRepository();
    private List<HistoryNewFeed> mListHistory = new ArrayList<>();

    Single<ListHistoryResponse> getListHistoryFromServer() {
        return eReadingRepository.getListHistory()
                .doOnSuccess(new Consumer<ListHistoryResponse>() {
                    @Override
                    public void accept(ListHistoryResponse listHistoryResponse) throws Exception {
                        mListHistory.clear();
                        mListHistory.addAll(listHistoryResponse.getListData());
                    }
                });
    }

    List<HistoryNewFeed> getListHistory() {
        return mListHistory;
    }
}
