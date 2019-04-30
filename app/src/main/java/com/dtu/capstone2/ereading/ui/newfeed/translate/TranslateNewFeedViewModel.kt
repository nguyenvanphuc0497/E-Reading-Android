package com.dtu.capstone2.ereading.ui.newfeed.translate

import android.text.SpannableStringBuilder
import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.dtu.capstone2.ereading.network.request.Vocabulary
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed
import com.dtu.capstone2.ereading.ui.model.TypeContent
import com.dtu.capstone2.ereading.ui.model.VocabularyLocation
import com.dtu.capstone2.ereading.ui.model.VocabularySelected
import com.dtu.capstone2.ereading.ui.utils.RxBusTransport
import com.dtu.capstone2.ereading.ui.utils.Transport
import com.dtu.capstone2.ereading.ui.utils.TypeTransportBus
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
    private val mListVocabularyTranslateResponse = mutableListOf<Vocabulary>()
    private val mListVocabularyNotTranslateResponse = mutableListOf<Vocabulary>()

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
                .doOnSuccess {
                    mListVocabularyTranslateResponse.addAll(it.listVocabulary)
                    mListVocabularyNotTranslateResponse.addAll(it.listVocabularyNotTranslate)
                }
                .map { (stringData, listVocabulary, listVocabularyNotTranslate) ->
                    LineContentNewFeed(typeContent,
                            stringData,
                            listVocabulary,
                            listVocabularyNotTranslate)
                }
    }

    fun getSizeListRefresh() = mListVocabularyRefresh.size

    fun getSizeListAddFavorite() = mListVocabularyAddFavorite.size

    fun addOrRemoveVocabularyToListRefresh(vocabularyLocation: VocabularyLocation) {
        mListVocabularyNotTranslateResponse.firstOrNull {
            it.startIndex == vocabularyLocation.startIndex && it.endIndex == vocabularyLocation.endIndex
        }?.let {
            with(VocabularySelected(vocabulary = it)) {
                mListVocabularyRefresh.firstOrNull { vocabularySelected ->
                    vocabularySelected.vocabulary.word == this.vocabulary.word
                }.let { vocabularySelected ->
                    when {
                        vocabularySelected == null -> {
                            mListVocabularyRefresh.add(this)
                        }
                        vocabularySelected.vocabulary.type != it.type -> {
                            mListVocabularyRefresh.add(this)
                        }
                        vocabularySelected.vocabulary.startIndex != vocabularyLocation.startIndex &&
                                vocabularySelected.vocabulary.endIndex != vocabularyLocation.endIndex -> {
                            RxBusTransport.publish(Transport(TypeTransportBus.TOAST_WITH_MESSAGE_SELECT_WORD,
                                    message = vocabularySelected.vocabulary.word))
                        }
                        else -> {
                            mListVocabularyRefresh.remove(this)
                        }
                    }
                }
            }
        }
    }

    fun getmTextSpannableResultsContent(): SpannableStringBuilder = mTextSpannableResultsContent

    fun getmTextSpannableResultsTitle(): SpannableStringBuilder = mTextSpannableResultsTitle

    fun getmTextSpannableResultsIntrudustion() = mTextSpannableResultsIntrodustion

    fun addOrRemoveVocabularyToListAddFavoriteByLocationVocabulary(vocabularyLocation: VocabularyLocation) {
        mListVocabularyTranslateResponse.firstOrNull {
            it.startIndex == vocabularyLocation.startIndex && it.endIndex == vocabularyLocation.endIndex
        }?.let {
            with(VocabularySelected(vocabulary = it)) {
                mListVocabularyAddFavorite.firstOrNull { vocabularySelected ->
                    vocabularySelected.vocabulary.word == this.vocabulary.word
                }.let { vocabularySelected ->
                    when {
                        vocabularySelected == null -> {
                            mListVocabularyAddFavorite.add(this)
                        }
                        vocabularySelected.vocabulary.type != it.type -> {
                            mListVocabularyAddFavorite.add(this)
                        }
                        vocabularySelected.vocabulary.startIndex != vocabularyLocation.startIndex &&
                                vocabularySelected.vocabulary.endIndex != vocabularyLocation.endIndex -> {
                            RxBusTransport.publish(Transport(TypeTransportBus.TOAST_WITH_MESSAGE_SELECT_WORD, message = vocabularySelected.vocabulary.word))
                        }
                        else -> {
                            mListVocabularyAddFavorite.remove(this)
                        }
                    }
                }
            }
        }
    }

    fun getArrayWordRefresh() = mListVocabularyRefresh.map {
        it.vocabulary.word
    }.toTypedArray()

    fun getArrayWordAddFavorite() = mListVocabularyAddFavorite.map {
        it.vocabulary.word
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

//    fun setListVocabularyFavoriteToServer() = mReadingRepository.setListVocabularyFavorite()

}
