package com.finite_la_kirilledia.messenger.chat

import com.finite_la_kirilledia.messenger.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_item_selected_user.view.*

class ChatItemSelectedUser(val message: Message) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.message.text = message.text
    }

    override fun getLayout(): Int = R.layout.chat_item_selected_user
}