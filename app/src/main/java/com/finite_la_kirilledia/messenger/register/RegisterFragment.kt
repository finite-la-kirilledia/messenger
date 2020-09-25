package com.finite_la_kirilledia.messenger.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finite_la_kirilledia.messenger.R
import com.finite_la_kirilledia.messenger.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import timber.log.Timber

class RegisterFragment : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {
            val username = username.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()

            register(username, email, password)
        }

        goToLoginScreenButton.setOnClickListener { findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()) }
    }

    private fun register(username: String, email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Timber.d("Successfully created user with id: ${it.user?.uid}")

                val uid = Firebase.auth.uid.toString()
                val user = User(uid = uid, username = username)
                saveUserToFirebaseDatabase(user)
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToChatListFragment())
            }
            .addOnFailureListener {
                Timber.d("Failed to create user: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(user: User) {
        val databaseReference = Firebase.database.getReference("/users/${user.uid}")
        databaseReference.setValue(user)
            .addOnSuccessListener {
                Timber.d("Successfully saved user to Firebase Database")
            }
            .addOnFailureListener {
                Timber.d("Failed to save user to Firebase Database: ${it.message}")
            }
    }
}