package com.dtu.capstone2.ereading.ui.newfeed.displayanewfeed;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Create by Nguyen Van Phuc on 4/7/19
 */
class DisplayNewFeedViewModel {
    private String urlNewFeed;

    String getUrlNewFeed() {
        return urlNewFeed;
    }

    void setUrlNewFeed(String urlNewFeed) {
        this.urlNewFeed = urlNewFeed;
    }

    Observable<String> getDataFromHTML() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Document doc = Jsoup.connect("https://edition.cnn.com/2019/04/07/us/florida-huge-python-captured-trnd/index.html").get();
                Document doc = Jsoup.connect("https://www.bbc.com/news/world-us-canada-47848619").get();
                emitter.onNext(doc.title());
                // Get meta info
                Elements contents = doc.getElementsByTag("p");


                for (Element metaElem : contents) {
                    if (metaElem.attributes().size() == 0) {
                        String content = metaElem.text();
                        emitter.onNext(content);
                    }
                    String introduction = metaElem.attr("story-body__introduction");
                }
            }
        });
    }
}
