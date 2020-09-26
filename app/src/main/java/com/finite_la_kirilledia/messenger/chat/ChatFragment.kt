package com.finite_la_kirilledia.messenger.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.finite_la_kirilledia.messenger.MainActivity
import com.finite_la_kirilledia.messenger.R
import com.finite_la_kirilledia.messenger.User

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var selectedUser: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedUser = ChatFragmentArgs.fromBundle(requireArguments()).selectedUser
        (activity as MainActivity).supportActionBar?.title = selectedUser.username
    }
}