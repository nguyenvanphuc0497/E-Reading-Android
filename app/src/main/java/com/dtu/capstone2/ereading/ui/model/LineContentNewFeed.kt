package com.dtu.capstone2.ereading.ui.model

/**
 * Create by Nguyen Van Phuc on 4/9/19
 */
data class LineContentNewFeed(val typeContent: TypeContent? = null,
                              val textContent: String = "",
                              val vocabulariesTranslated: List<WordSpannableHighLight>? = null,
                              val vocabulariesUntranslated: List<WordSpannableHighLight>? = null)

enum class TypeContent(val valueType: String) {
    TITLE("title"),
    ITEM("story-body__list-item"),
    INTRODUCTION("story-body__introduction"),
    TEXT("text"),
    HEADER("story-body__crosshead"),
}
