package com.finite_la_kirilledia.messenger.chat_list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finite_la_kirilledia.messenger.R
import com.finite_la_kirilledia.messenger.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat_list.*
import timber.log.Timber

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    private lateinit var adapter: GroupAdapter<ViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GroupAdapter()
        adapter.setOnItemClickListener { item, view -> findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToChatFragment((item as ChatListItem).user)) }
        recyclerViewChatList.adapter = adapter
        loadUsers()

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

    private fun loadUsers() {
        val databaseReference = Firebase.database.getReference("/users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val user = it.getValue(User::class.java)
                        adapter.add(ChatListItem(user!!))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Timber.d("Failed to listen Firebase Database: ${error.message}")
                }
            })
    }
}