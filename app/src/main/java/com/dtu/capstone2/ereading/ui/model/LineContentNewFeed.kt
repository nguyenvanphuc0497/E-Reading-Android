package com.dtu.capstone2.ereading.ui.model

import com.dtu.capstone2.ereading.network.request.Vocabulary

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
data class LineContentNewFeed(val typeContent: TypeContent,
                              val textContent: String,
                              val listVocabularies: List<Vocabulary>? = null,
                              val listVocabulariesNotTranslate: List<Vocabulary>? = null)

enum class TypeContent(val valueType: String) {
    TITLE("title"),
    ITEM("story-body__list-item"),
    INTRODUCTION("story-body__introduction"),
    TEXT("text"),
    HEADER("story-body__crosshead"),
}
