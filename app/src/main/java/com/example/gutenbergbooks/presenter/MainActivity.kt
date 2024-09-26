package com.example.gutenbergbooks.presenter

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.gutenbergbooks.R
import com.example.gutenbergbooks.databinding.ActivityMainBinding
import com.example.gutenbergbooks.presenter.viewmodel.BookListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

    }

    private fun setupNavigation() {
        val navGraphId = R.navigation.main_nav_graph
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as? NavHostFragment
            ?: NavHostFragment.create(navGraphId)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()

        navController = navHostFragment.navController
    }

}