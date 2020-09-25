package com.finite_la_kirilledia.messenger.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finite_la_kirilledia.messenger.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            login(email, password)
        }

        goToRegisterScreenButton.setOnClickListener { findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()) }
    }

    private fun login(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Timber.d("Successfully logged in with credentials: $email/$password")
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToChatListFragment())
            }
            .addOnFailureListener {
                Timber.d("Failed to login with credentials: $email/$password")
            }
    }
}