package com.finite_la_kirilledia.messenger.chat

data class Message(
    val id: String = "",
    val selectedUserId: String = "",
    val currentUserId: String = "",
    val text: String = "",
    val sendTimeMillis: Long = -1
)