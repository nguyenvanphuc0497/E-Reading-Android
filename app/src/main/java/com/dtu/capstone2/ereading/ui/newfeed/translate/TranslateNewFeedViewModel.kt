package com.dtu.capstone2.ereading.ui.newfeed.translate

import com.dtu.capstone2.ereading.datasource.repository.EReadingRepository
import com.dtu.capstone2.ereading.datasource.repository.LocalRepository
import com.dtu.capstone2.ereading.network.request.DataStringReponse
import com.dtu.capstone2.ereading.network.request.Vocabulary
import com.dtu.capstone2.ereading.network.response.DetailResponse
import com.dtu.capstone2.ereading.ui.model.LineContentNewFeed
import com.dtu.capstone2.ereading.ui.model.TypeContent
import com.dtu.capstone2.ereading.ui.model.VocabularyLocation
import com.dtu.capstone2.ereading.ui.model.VocabularySelected
import com.dtu.capstone2.ereading.ui.utils.RxBusTransport
import com.dtu.capstone2.ereading.ui.utils.Transport
import com.dtu.capstone2.ereading.ui.utils.TypeTransportBus
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import org.jsoup.Jsoup

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
internal class TranslateNewFeedViewModel(private val mReadingRepository: EReadingRepository, private val mLocalRepository: LocalRepository) {
    internal var urlNewFeed: String? = null
    private val mListVocabularyToTranslateRefresh = mutableListOf<VocabularySelected>()
    private val mListVocabularyAddFavorite = mutableListOf<VocabularySelected>()
    private var mNameListDialogShowing: String = ""
    private val mListVocabularyTranslateResponse = mutableListOf<Vocabulary>()
    private val mListVocabularyNotTranslateResponse = mutableListOf<Vocabulary>()
    internal val dataRecyclerView = mutableListOf<LineContentNewFeed>()
    private val listContentSource = hashMapOf<Int, String>()

    // TRường hợp với báo BBC text ok
    fun getDataFromHTMLAndOnNextDetectWord(): Observable<DataStringReponse> = Observable.create(ObservableOnSubscribe<LineContentNewFeed> { emitter ->
        with(Jsoup.connect(urlNewFeed).get()) {
            emitter.onNext(LineContentNewFeed(TypeContent.TITLE, this.title()))

            with(this.getElementsByTag("p")) contentElem@{
                this@contentElem.forEach { element ->
                    when {
                        element.hasClass(TypeContent.INTRODUCTION.valueType) -> emitter.onNext(LineContentNewFeed(TypeContent.INTRODUCTION, element.text()))
                        element.hasClass(TypeContent.HEADER.valueType) -> emitter.onNext(LineContentNewFeed(TypeContent.HEADER, element.text()))
                        element.hasClass(TypeContent.ITEM.valueType) -> emitter.onNext(LineContentNewFeed(TypeContent.ITEM, element.text()))
                        element.attributes().size() == 0 -> emitter.onNext(LineContentNewFeed(TypeContent.TEXT, element.text()))
                    }
                }
            }
        }
        emitter.onComplete()
    }).flatMapSingle { (typeContent, textContent) ->
        val positionContent = dataRecyclerView.size
        listContentSource[positionContent] = textContent
        mReadingRepository.translateNewFeed(urlNewFeed, positionContent, textContent)
                .doOnSuccess {
                    mListVocabularyTranslateResponse.addAll(it.listVocabulary)
                    mListVocabularyNotTranslateResponse.addAll(it.listVocabularyNotTranslate)
                    dataRecyclerView.add(LineContentNewFeed(typeContent,
                            it.stringData,
                            it.listVocabulary,
                            it.listVocabularyNotTranslate))
                }
    }

    fun getSizeListRefresh() = mListVocabularyToTranslateRefresh.size

    fun getSizeListAddFavorite() = mListVocabularyAddFavorite.size

    fun addOrRemoveVocabularyToListRefresh(vocabularyLocation: VocabularyLocation) {
        mListVocabularyNotTranslateResponse.firstOrNull {
            it.startIndex == vocabularyLocation.startIndex && it.endIndex == vocabularyLocation.endIndex
        }?.let {
            with(VocabularySelected(vocabulary = it, positionContent = vocabularyLocation.positionContent)) {
                mListVocabularyToTranslateRefresh.firstOrNull { vocabularySelected ->
                    vocabularySelected.vocabulary.word == this.vocabulary.word
                }.let { vocabularySelected ->
                    when {
                        vocabularySelected == null -> {
                            mListVocabularyToTranslateRefresh.add(this)
                        }
                        vocabularySelected.vocabulary.type != it.type -> {
                            mListVocabularyToTranslateRefresh.add(this)
                        }
                        vocabularySelected.positionContent != vocabularyLocation.positionContent ||
                                vocabularySelected.vocabulary.startIndex != vocabularyLocation.startIndex -> {
                            RxBusTransport.publish(Transport(TypeTransportBus.TOAST_WITH_MESSAGE_SELECT_WORD,
                                    message = vocabularySelected.vocabulary.word))
                        }
                        else -> {
                            mListVocabularyToTranslateRefresh.remove(this)
                        }
                    }
                }
            }
        }
    }

    fun addOrRemoveVocabularyToListAddFavoriteByLocationVocabulary(vocabularyLocation: VocabularyLocation) {
        mListVocabularyTranslateResponse.firstOrNull {
            it.startIndex == vocabularyLocation.startIndex && it.endIndex == vocabularyLocation.endIndex
        }?.let {
            with(VocabularySelected(vocabulary = it, positionContent = vocabularyLocation.positionContent)) {
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
                        vocabularySelected.positionContent != vocabularyLocation.positionContent ||
                                vocabularySelected.vocabulary.startIndex != vocabularyLocation.startIndex -> {
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

    fun getArrayWordRefresh() = mListVocabularyToTranslateRefresh.map {
        it.vocabulary.word
    }.toTypedArray()

    fun getArrayWordAddFavorite() = mListVocabularyAddFavorite.map {
        it.vocabulary.word
    }.toTypedArray()

    fun getArraySelectedRefresh() = mListVocabularyToTranslateRefresh.map {
        it.isChecked
    }.toBooleanArray()

    fun getArraySelectedAddFavorite() = mListVocabularyAddFavorite.map {
        it.isChecked
    }.toBooleanArray()

    fun resetListVocabularyRefresh() {
        mListVocabularyToTranslateRefresh.clear()
    }

    fun resetListVocabularyAddFavorite() {
        mListVocabularyAddFavorite.clear()
    }

    fun getNameListDialogShowing() = mNameListDialogShowing

    fun setNameListDialogShowing(name: String) {
        mNameListDialogShowing = name
    }

    fun getPositionItemInsertedOfRV() = dataRecyclerView.size

    fun addFavoriteToServer(): Single<DetailResponse> = mReadingRepository.setListVocabularyFavorite(mListVocabularyAddFavorite.filter {
        it.isChecked
    }.map {
        it.vocabulary
    })

    fun sendVocabularySelectedToServerToTranslateAgain() {
        mListVocabularyToTranslateRefresh.groupBy {
            it.positionContent
        }.forEach {
            //            Log.e("xxx",listContentSource[it.key])

        }
    }
}
