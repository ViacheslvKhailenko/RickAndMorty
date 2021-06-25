package com.example.projectandersen.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectandersen.R
import com.example.projectandersen.presentation.characters.CharactersFragment
import com.example.projectandersen.presentation.locations.LocationsFragment
import com.example.projectandersen.presentation.main.MainPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(R.layout.activity_main), MainView {

    private val presenter by moxyPresenter { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ProjectAndersen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        val fragmentCharacters = CharactersFragment.newInstance()
        val fragmentSeason = EpisodesFragment.newInstance()
        val fragmentLocation = LocationsFragment.newInstance()

        chooseCurrentFragmentFromBottomNavigation(fragmentCharacters)

        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.seasonMenu -> chooseCurrentFragmentFromBottomNavigation(fragmentSeason)
                R.id.locationsMenu -> chooseCurrentFragmentFromBottomNavigation(fragmentLocation)
                R.id.сharactersMenu -> chooseCurrentFragmentFromBottomNavigation(fragmentCharacters)
            }
            true
        }
    }

    private fun chooseCurrentFragmentFromBottomNavigation(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment, fragment)
            commit()
        }
}