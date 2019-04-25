package com.dtu.capstone2.ereading.ui.account;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository;
import com.dtu.capstone2.ereading.ui.model.LevelEnglish;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Create by Nguyen Van Phuc on 4/12/19
 */
class PageAccountViewModel {
    private EReadingRepository mEReadingRepository;
    private LocalRepository mLocalRepository;
    private List<String> nameLevels = new ArrayList<>();

    PageAccountViewModel(EReadingRepository eReadingRepository, LocalRepository localRepository) {
        mEReadingRepository = eReadingRepository;
        mLocalRepository = localRepository;
    }

    Single<List<String>> getListLevelFromServer() {
        if (nameLevels.isEmpty()) {
            return mEReadingRepository.getLevelEnglishFromServer().doOnSuccess(new Consumer<List<LevelEnglish>>() {
                @Override
                public void accept(List<LevelEnglish> levelEnglishes) throws Exception {
                    nameLevels.clear();
                    for (LevelEnglish e : levelEnglishes) {
                        nameLevels.add(e.getName());
                    }
                }
            }).map(new Function<List<LevelEnglish>, List<String>>() {
                @Override
                public List<String> apply(List<LevelEnglish> levelEnglishes) throws Exception {
                    List<String> lists = new ArrayList<>();
                    for (LevelEnglish e : levelEnglishes) {
                        lists.add(e.getName());
                    }
                    return lists;
                }
            });
        } else {
            return Single.just(nameLevels);
        }
    }
    public void clearToken()
    {
        mLocalRepository.clearTokenUser();
    }
    public void clearEmail(){
        mLocalRepository.clearEmailUser();
    }
    String getEmailFromLocal() {
        return mLocalRepository.getEmailUser();
    }

    Single<LevelEnglish> setLevelOfUserToServer(int position) {
        return mEReadingRepository.setLevelEnglishForUserToServer(position).doOnSuccess(new Consumer<LevelEnglish>() {
            @Override
            public void accept(LevelEnglish levelEnglish) throws Exception {
                mLocalRepository.saveNameLevelUser(levelEnglish.getName());
            }
        });
    }
}
