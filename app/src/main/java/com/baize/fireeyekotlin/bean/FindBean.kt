package com.baize.fireeyekotlin.bean

/**
 * Created by 彦泽 on 2018/9/25.
 */

data class FindBean(
    val id: Int,
    val name: String,
    val alias: Any,
    val description: String,
    val bgPicture: String,
    val bgColor: String,
    val headerImage: String,
    val defaultAuthorId: Int
)