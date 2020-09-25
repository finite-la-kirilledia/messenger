package com.finite_la_kirilledia.messenger.chat_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finite_la_kirilledia.messenger.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                Firebase.auth.signOut()
                findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}