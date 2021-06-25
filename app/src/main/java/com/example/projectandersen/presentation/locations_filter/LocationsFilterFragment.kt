package com.example.projectandersen.presentation.locations_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectandersen.R
import moxy.MvpAppCompatFragment

class LocationsFilterFragment : MvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.locations_filter, container, false)
        return view
    }

    companion object {
        fun newInstance() = LocationsFilterFragment()
    }
}