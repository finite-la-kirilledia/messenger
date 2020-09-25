package com.finite_la_kirilledia.messenger.chat_list

import com.finite_la_kirilledia.messenger.R
import com.finite_la_kirilledia.messenger.User
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_list_item.view.*

class ChatListItem(private val user: User) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.username.text = user.username
    }

    override fun getLayout(): Int = R.layout.chat_list_item
}