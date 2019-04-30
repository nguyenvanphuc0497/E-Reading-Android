package com.dtu.capstone2.ereading.ui.newfeed.translate

import android.text.SpannableStringBuilder
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed
import com.dtu.capstone2.ereading.ui.model.TypeContent
import com.dtu.capstone2.ereading.ui.model.VocabularySelected
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.jsoup.Jsoup

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
internal class TranslateNewFeedViewModel(private val mReadingRepository: EReadingRepository, private val mLocalRepository: LocalRepository) {
    var urlNewFeed: String? = null
    private val mListVocabularyRefresh = mutableListOf<VocabularySelected>()
    private val mListVocabularyAddFavorite = mutableListOf<VocabularySelected>()
    private val mTextSpannableResultsContent = SpannableStringBuilder()
    private val mTextSpannableResultsTitle = SpannableStringBuilder()
    private val mTextSpannableResultsIntrodustion = SpannableStringBuilder()
    private var mNameListDialogShowing: String = ""

    // TRường hợp với báo BBC text ok
    fun getDataFromHTMLAndOnNextDetectWord(): Observable<LineContentNewFeed> = Observable.create(ObservableOnSubscribe<LineContentNewFeed> { emitter ->
        with(Jsoup.connect(urlNewFeed).get()) {
            emitter.onNext(LineContentNewFeed(TypeContent.TITLE, this.title()))

            with(this.getElementsByTag("p")) contentElem@{
                this@contentElem.forEach { element ->
                    if (element.hasClass(TypeContent.INTRODUCTION.valueType)) {
                        emitter.onNext(LineContentNewFeed(TypeContent.INTRODUCTION, element.text()))
                    } else if (element.hasClass(TypeContent.HEADER.valueType)) {
                        emitter.onNext(LineContentNewFeed(TypeContent.HEADER, element.text()))
                    } else if (element.hasClass(TypeContent.ITEM.valueType)) {
                        emitter.onNext(LineContentNewFeed(TypeContent.ITEM, element.text()))
                    } else if (element.attributes().size() == 0) {
                        emitter.onNext(LineContentNewFeed(TypeContent.TEXT, element.text()))
                    }
                }
            }
        }
        emitter.onComplete()
    }).flatMapSingle { (typeContent, textContent) ->
        mReadingRepository.GetDataStringReponse(textContent, mLocalRepository.nameLevelUser)
                .map { (stringData, listVocabulary, listVocabularyNotTranslate) ->
                    LineContentNewFeed(typeContent,
                            stringData,
                            listVocabulary,
                            listVocabularyNotTranslate)
                }
    }

    fun getSizeListRefresh() = mListVocabularyRefresh.size

    fun getSizeListAddFavorite() = mListVocabularyAddFavorite.size

    fun addOrRemoveVocabularyToListRefresh(vocabulary: String) {
        with(VocabularySelected(vocabulary.toLowerCase())) {
            if (mListVocabularyRefresh.contains(this)) {
                mListVocabularyRefresh.remove(this)
            } else {
                mListVocabularyRefresh.add(this)
            }
        }
    }

    fun getmTextSpannableResultsContent(): SpannableStringBuilder = mTextSpannableResultsContent

    fun getmTextSpannableResultsTitle(): SpannableStringBuilder = mTextSpannableResultsTitle

    fun getmTextSpannableResultsIntrudustion() = mTextSpannableResultsIntrodustion

    fun addOrRemoveVocabularyToListAddFavorite(vocabulary: String) {
        with(VocabularySelected(vocabulary.toLowerCase())) {
            if (mListVocabularyAddFavorite.contains(this)) {
                mListVocabularyAddFavorite.remove(this)
            } else {
                mListVocabularyAddFavorite.add(this)
            }
        }
    }

    fun getArrayWordRefresh() = mListVocabularyRefresh.map {
        it.word
    }.toTypedArray()

    fun getArrayWordAddFavorite() = mListVocabularyAddFavorite.map {
        it.word
    }.toTypedArray()

    fun getArraySelectedRefresh() = mListVocabularyRefresh.map {
        it.isChecked
    }.toBooleanArray()

    fun getArraySelectedAddFavorite() = mListVocabularyAddFavorite.map {
        it.isChecked
    }.toBooleanArray()

    fun resetListVocabularyRefresh() {
        mListVocabularyRefresh.clear()
    }

    fun resetListVocabularyAddFavorite() {
        mListVocabularyAddFavorite.clear()
    }

    fun getNameListDialogShowing() = mNameListDialogShowing

    fun setNameListDialogShowing(name: String) {
        mNameListDialogShowing = name
    }
}
