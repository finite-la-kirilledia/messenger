package com.finite_la_kirilledia.messenger.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.finite_la_kirilledia.messenger.MainActivity
import com.finite_la_kirilledia.messenger.R
import com.finite_la_kirilledia.messenger.User
import com.finite_la_kirilledia.messenger.chat_list.ChatListFragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_chat.*
import timber.log.Timber

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var currentUser: User
    private lateinit var selectedUser: User

    private lateinit var adapter: GroupAdapter<ViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUser = ChatListFragment.currentUser!!
        selectedUser = ChatFragmentArgs.fromBundle(requireArguments()).selectedUser
        (activity as MainActivity).supportActionBar?.title = selectedUser.username

        adapter = GroupAdapter()
        recyclerViewMessages.adapter = adapter
        loadMessages()

        sendButton.setOnClickListener { sendMessage() }
    }

    private fun loadMessages() {
        val currentUserId = currentUser.uid
        val selectedUserId = selectedUser.uid
        val databaseReference = Firebase.database.getReference("/messages/$currentUserId/$selectedUserId")
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message?.currentUserId == currentUserId) {
                    adapter.add(ChatItemCurrentUser(message))
                } else {
                    adapter.add(ChatItemSelectedUser(message!!))
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendMessage() {
        val currentUserId = currentUser.uid
        val selectedUserId = selectedUser.uid
        val currentUserDatabaseReference = Firebase.database.getReference("/messages/$currentUserId/$selectedUserId").push()
        val selectedUserDatabaseReference = Firebase.database.getReference("/messages/$selectedUserId/$currentUserId").push()

        val messageId = currentUserDatabaseReference.key.toString()
        val messageText = typeMessage.text.toString()
        val sendTimeMillis = System.currentTimeMillis()

        val message = Message(
            id = messageId,
            currentUserId = currentUserId,
            selectedUserId = selectedUserId,
            text = messageText,
            sendTimeMillis = sendTimeMillis
        )

        currentUserDatabaseReference.setValue(message)
            .addOnSuccessListener {
                Timber.d("Successfully sent message: $it")
                typeMessage.text.clear()
            }
            .addOnFailureListener {
                Timber.d("Failed to send message: ${it.message}")
            }

        selectedUserDatabaseReference.setValue(message)
            .addOnSuccessListener {
                Timber.d("Successfully sent message: $it")
            }
            .addOnFailureListener {
                Timber.d("Failed to send message: ${it.message}")
            }
    }
}