package com.example.projectandersen.presentation.episodes_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectandersen.R
import moxy.MvpAppCompatFragment

class EpisodesFilterFragment : MvpAppCompatFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.episodes_filter, container, false)
        return view
    }

    companion object {
        fun newInstance() = EpisodesFilterFragment()
    }
}