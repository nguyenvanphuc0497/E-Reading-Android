package com.dtu.capstone2.ereading.ui.newfeed.translate;

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereading.network.request.DataStringReponse;
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed;
import com.dtu.capstone2.ereading.ui.model.TypeContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
class TranslateNewFeedViewModel {
    private String urlNewFeed;
    private EReadingRepository mReadingRepository;

    TranslateNewFeedViewModel(EReadingRepository eReadingRepository) {
        mReadingRepository = eReadingRepository;
    }

    String getUrlNewFeed() {
        return urlNewFeed;
    }

    void setUrlNewFeed(String urlNewFeed) {
        this.urlNewFeed = urlNewFeed;
    }

    Observable<LineContentNewFeed> getDataFromHTMLAndOnNextDetectWord() {
        // TRường hợp với báo BBC text ok
        return Observable.create(new ObservableOnSubscribe<LineContentNewFeed>() {
            @Override
            public void subscribe(ObservableEmitter<LineContentNewFeed> emitter) throws Exception {
                Document doc = Jsoup.connect(urlNewFeed).get();
                emitter.onNext(new LineContentNewFeed(TypeContent.TITLE, doc.title()));

                Elements contents = doc.getElementsByTag("p");

                for (Element contentElem : contents) {
                    if (contentElem.hasClass(TypeContent.INTRODUCTION.getValueType())) {
                        emitter.onNext(new LineContentNewFeed(TypeContent.INTRODUCTION, contentElem.text()));
                    } else if (contentElem.hasClass(TypeContent.HEADER.getValueType())) {
                        emitter.onNext(new LineContentNewFeed(TypeContent.HEADER, contentElem.text()));
                    } else if (contentElem.hasClass(TypeContent.ITEM.getValueType())) {
                        emitter.onNext(new LineContentNewFeed(TypeContent.ITEM, contentElem.text()));
                    } else if (contentElem.attributes().size() == 0) {
                        emitter.onNext(new LineContentNewFeed(TypeContent.TEXT, contentElem.text()));
                    }
                }

                emitter.onComplete();
            }
        }).flatMapSingle(new Function<LineContentNewFeed, Single<LineContentNewFeed>>() {
            @Override
            public Single<LineContentNewFeed> apply(final LineContentNewFeed lineContentNewFeed) throws Exception {
                return mReadingRepository.GetDataStringReponse(lineContentNewFeed.getTextContent()).map(new Function<DataStringReponse, LineContentNewFeed>() {
                    @Override
                    public LineContentNewFeed apply(DataStringReponse dataStringReponse) throws Exception {
                        return new LineContentNewFeed(lineContentNewFeed.getTypeContent(), dataStringReponse.getStringData());
                    }
                });
            }
        });
    }
}
