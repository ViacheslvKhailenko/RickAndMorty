package com.example.projectandersen.presentation.characters_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.projectandersen.R
import com.example.projectandersen.presentation.characters.CharactersFragment.Companion.REQUEST_FILTER_CODE
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class CharactersFilterFragment : MvpAppCompatFragment(), ICharactersFilterView {

    private val presenter by moxyPresenter { CharactersFilterPresenter() }

    lateinit var backToList: Button
    lateinit var applyFilter: Button
    lateinit var clearFilters: Button
    lateinit var filterEnterCharacterName: EditText
    lateinit var filterEnterCharacterSpecies: EditText
    lateinit var filterEnterCharacterType: EditText
    lateinit var radioGroupStatus: RadioGroup
    lateinit var radioGroupGender: RadioGroup
    lateinit var filterName: String
    lateinit var filterStatus: String
    lateinit var filterSpecies: String
    lateinit var filterType: String
    lateinit var filterGender: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.characters_filter, container, false)
        backToList = view.findViewById(R.id.backToList)
        applyFilter = view.findViewById(R.id.applyFilter)
        clearFilters = view.findViewById(R.id.clearFilters)
        filterEnterCharacterName = view.findViewById(R.id.filterEnterCharacterName)
        filterEnterCharacterSpecies = view.findViewById(R.id.filterEnterCharacterSpecies)
        filterEnterCharacterType = view.findViewById(R.id.filterEnterCharacterType)
        radioGroupStatus = view.findViewById(R.id.radioGroupStatus)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)

        backToList.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        applyFilter.setOnClickListener {
            presenter.applyFilters(
                filterGenderId = radioGroupGender.checkedRadioButtonId,
                filterStatusId = radioGroupStatus.checkedRadioButtonId,
                filterName = filterEnterCharacterName.text.toString(),
                filterSpecies = filterEnterCharacterSpecies.text.toString(),
                filterType = filterEnterCharacterType.text.toString(),
            )
        }
        clearFilters.setOnClickListener { clearFilters() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val charactersFilterData =
            requireArguments().getParcelable<CharactersFilterData>(KEY_FILTER_DATA) as CharactersFilterData
        filterName = charactersFilterData.filterName.orEmpty()
        filterStatus = charactersFilterData.filterStatus.orEmpty()
        filterSpecies = charactersFilterData.filterSpecies.orEmpty()
        filterType = charactersFilterData.filterType.orEmpty()
        filterGender = charactersFilterData.filterGender.orEmpty()
        setFilters()
    }

    private fun clearFilters() {
        filterEnterCharacterName.setText("")
        filterEnterCharacterName.clearFocus()
        filterEnterCharacterSpecies.setText("")
        filterEnterCharacterSpecies.clearFocus()
        filterEnterCharacterType.setText("")
        filterEnterCharacterType.clearFocus()
        radioGroupStatus.clearCheck()
        radioGroupGender.clearCheck()
    }

    private fun setFilters() {
        presenter.setStatusSelected(filterStatus)
        presenter.setGenderSelected(filterGender)
        filterEnterCharacterName.setText(filterName)
        filterEnterCharacterSpecies.setText(filterSpecies)
        filterEnterCharacterType.setText(filterType)
    }

    override fun onSelectStatusFilter(id: Int?) {
        id?.let { radioGroupStatus.check(it) }
    }

    override fun onSelectGenderFilter(id: Int?) {
        id?.let { radioGroupGender.check(it) }
    }

    override fun onFilterApplied(charactersFilterData: CharactersFilterData) {
        setFragmentResult(REQUEST_FILTER_CODE, bundleOf(KEY_FILTER_DATA to charactersFilterData))
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        const val KEY_FILTER_DATA = "key_filter_data"
        fun newInstance(charactersFilterData: CharactersFilterData) =
            CharactersFilterFragment().apply {
                arguments = bundleOf(KEY_FILTER_DATA to charactersFilterData)
            }
    }
}