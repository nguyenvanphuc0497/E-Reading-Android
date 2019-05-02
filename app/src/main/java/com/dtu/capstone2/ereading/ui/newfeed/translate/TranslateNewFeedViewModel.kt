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
    private val mListVocabularyToAddFavorite = mutableListOf<VocabularySelected>()
    private var mNameListDialogShowing: String = ""
    private val mListVocabularyTranslateResponse = hashMapOf<Int, List<Vocabulary>>()
    private val mListVocabularyNotTranslateResponse = hashMapOf<Int, List<Vocabulary>>()
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
                    setListVocabularyFromSeverByPosition(positionContent, it.listVocabulary, it.listVocabularyNotTranslate)
                    dataRecyclerView.add(LineContentNewFeed(typeContent,
                            it.stringData,
                            it.listVocabulary,
                            it.listVocabularyNotTranslate))
                }
    }

    fun getSizeListRefresh() = mListVocabularyToTranslateRefresh.size

    fun getSizeListAddFavorite() = mListVocabularyToAddFavorite.size

    fun addOrRemoveVocabularyToListRefresh(vocabularyLocation: VocabularyLocation) {
        mListVocabularyNotTranslateResponse[vocabularyLocation.positionContent]?.firstOrNull {
            it.startIndex == vocabularyLocation.startIndex && it.endIndex == vocabularyLocation.endIndex
        }?.let {
            mListVocabularyToTranslateRefresh.firstOrNull { checking ->
                checking.positionContent != vocabularyLocation.positionContent && checking.vocabulary.word == it.word
            }?.let { checking ->
                RxBusTransport.publish(Transport(TypeTransportBus.TOAST_WITH_MESSAGE_SELECT_WORD,
                        message = checking.vocabulary.word))
                return
            }
            with(VocabularySelected(vocabulary = it, positionContent = vocabularyLocation.positionContent)) {
                if (mListVocabularyToTranslateRefresh.contains(this))
                    mListVocabularyToTranslateRefresh.remove(this)
                else {
                    mListVocabularyToTranslateRefresh.add(this)
                }
            }
        }
    }

    fun addOrRemoveVocabularyToListAddFavoriteByLocationVocabulary(vocabularyLocation: VocabularyLocation) {
        mListVocabularyTranslateResponse[vocabularyLocation.positionContent]?.firstOrNull {
            it.startIndex == vocabularyLocation.startIndex && it.endIndex == vocabularyLocation.endIndex
        }?.let {
            mListVocabularyToAddFavorite.firstOrNull { checking ->
                checking.positionContent != vocabularyLocation.positionContent && checking.vocabulary.word == it.word
            }?.let { checking ->
                RxBusTransport.publish(Transport(TypeTransportBus.TOAST_WITH_MESSAGE_SELECT_WORD,
                        message = checking.vocabulary.word))
                return
            }
            with(VocabularySelected(vocabulary = it, positionContent = vocabularyLocation.positionContent)) {
                if (mListVocabularyToAddFavorite.contains(this))
                    mListVocabularyToAddFavorite.remove(this)
                else {
                    mListVocabularyToAddFavorite.add(this)
                }
            }
        }
    }

    fun getArrayWordRefresh() = mListVocabularyToTranslateRefresh.map {
        it.vocabulary.word
    }.toTypedArray()

    fun getArrayWordAddFavorite() = mListVocabularyToAddFavorite.map {
        it.vocabulary.word
    }.toTypedArray()

    fun getArraySelectedRefresh() = mListVocabularyToTranslateRefresh.map {
        it.isChecked
    }.toBooleanArray()

    fun getArraySelectedAddFavorite() = mListVocabularyToAddFavorite.map {
        it.isChecked
    }.toBooleanArray()

    fun resetListVocabularyRefresh() {
        mListVocabularyToTranslateRefresh.clear()
    }

    fun resetListVocabularyAddFavorite() {
        mListVocabularyToAddFavorite.clear()
    }

    fun getNameListDialogShowing() = mNameListDialogShowing

    fun setNameListDialogShowing(name: String) {
        mNameListDialogShowing = name
    }

    fun getPositionItemInsertedOfRV() = dataRecyclerView.size

    fun addFavoriteToServer(): Single<DetailResponse> = mReadingRepository.setListVocabularyFavorite(mListVocabularyToAddFavorite.filter {
        it.isChecked
    }.map {
        it.vocabulary
    })

    fun sendVocabularySelectedToServerToTranslateAgain(): Observable<Int> = Observable.create(ObservableOnSubscribe<Map.Entry<Int, List<VocabularySelected>>> {
        mListVocabularyToTranslateRefresh.groupBy { selected ->
            selected.positionContent
        }.forEach { map ->
            it.onNext(map)
        }
    }).flatMapSingle { map ->
        mReadingRepository.translateNewFeedAgain(urlNewFeed,
                map.key,
                listContentSource[map.key]
                , mListVocabularyToTranslateRefresh.map { vocabularySelected ->
            vocabularySelected.vocabulary
        }).doOnSuccess {
            val oldTypeContent = dataRecyclerView[map.key].typeContent
            dataRecyclerView[map.key] = LineContentNewFeed(oldTypeContent,
                    it.stringData,
                    it.listVocabulary,
                    it.listVocabularyNotTranslate)
            // Xoá các từ được dịch thành công khỏi danh sách
            mListVocabularyToTranslateRefresh.removeAll(map.value)
        }.map {
            map.key
        }
    }

    private fun setListVocabularyFromSeverByPosition(positionContent: Int, vocabulariesTranslated: List<Vocabulary>, vocabulariesNotTranslate: List<Vocabulary>) {
        mListVocabularyTranslateResponse[positionContent] = vocabulariesTranslated
        mListVocabularyNotTranslateResponse[positionContent] = vocabulariesNotTranslate
    }
}
