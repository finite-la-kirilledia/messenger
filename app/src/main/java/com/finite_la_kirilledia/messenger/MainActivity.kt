package com.finite_la_kirilledia.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.finite_la_kirilledia.messenger.login.LoginFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration
            .Builder(R.id.loginFragment, R.id.registerFragment, R.id.chatListFragment)
            .build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        if (Firebase.auth.uid != null) {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToChatListFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}