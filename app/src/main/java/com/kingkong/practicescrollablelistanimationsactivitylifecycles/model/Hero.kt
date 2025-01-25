package com.kingkong.practicescrollablelistanimationsactivitylifecycles.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R

data class Hero(
    val id: Int,
    @StringRes val nameRes: Int ,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int,
    val additionalImages: List<Int>,
    var isFav: Boolean
)



