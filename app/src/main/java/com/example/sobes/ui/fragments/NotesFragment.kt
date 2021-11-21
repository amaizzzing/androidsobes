package com.example.sobes.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobes.SobesApp
import com.example.sobes.data.entities.NotesEntity
import com.example.sobes.databinding.FragmentNotesBinding
import com.example.sobes.di.ViewModelFactory
import com.example.sobes.ui.adapters.NotesAdapter
import com.example.sobes.ui.adapters.presenters.INotesAdapterPresenter
import com.example.sobes.ui.dialogs.AddMarkerDialog
import com.example.sobes.ui.states.BaseState
import com.example.sobes.ui.viewmodels.NotesViewModel
import com.example.sobes.utils.setGone
import com.example.sobes.utils.setVisible
import javax.inject.Inject

const val NOTES_ADD_MARKER_DIALOG_TAG = "NotesAddMarkerDialog"

class NotesFragment:
    BaseFragment<FragmentNotesBinding, NotesViewModel>(),
    AddMarkerDialog.OnSaveClick
{
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var notesAdapterPresenter: INotesAdapterPresenter

    private var adapter: NotesAdapter? = null

    override fun getViewModelClass() = NotesViewModel::class.java
    override fun getViewBinding() = FragmentNotesBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SobesApp.appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    override fun setupViews() {
        AddMarkerDialog.onSaveImpl = this
        initRecyclerView()
        initListeners()
        isProgressVisible(true)
    }

    private fun initListeners() {
        notesAdapterPresenter.itemClickListener = { notesView ->
            val note = notesAdapterPresenter.getList()[notesView.pos]
            AddMarkerDialog
                .newInstance(note.lat, note.lon)
                .show(childFragmentManager, NOTES_ADD_MARKER_DIALOG_TAG)
        }
    }

    override fun observeData() {
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        viewModel.getAllNotes()
    }

    private fun initRecyclerView() {
        binding?.let {
            it.rvNotes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = NotesAdapter(notesAdapterPresenter)
            it.rvNotes.adapter = adapter
        }
    }

    override fun renderData(data: BaseState) {
        when(data) {
            is BaseState.Success<*> -> {
                isProgressVisible(false)
                (data.resultData as List<NotesEntity>).also {
                    notesAdapterPresenter.setList(it)
                    adapter?.notifyDataSetChanged()
                }
            }
            is BaseState.Error -> {
                isProgressVisible(false)
                Toast.makeText(requireContext(), "ERROR!", Toast.LENGTH_LONG).show()
            }
            BaseState.Loading -> {
                isProgressVisible(true)
            }
        }
    }

    override fun isProgressVisible(isVisible: Boolean) {
        binding?.let {
            if (isVisible) {
                it.rvNotes.setGone()
                it.pbFragmentNotes.setVisible()
            } else {
                it.rvNotes.setVisible()
                it.pbFragmentNotes.setGone()
            }
        }
    }

    override fun onSaveNewMarker(name: String, description: String, lat: Double, lon: Double) {
        val listNotes = notesAdapterPresenter.getList()

        val index = listNotes.indexOfFirst { it.lat == lat && it.lon == lon }

        listNotes[index].apply {
            this.name = name
            this.text = description
            this.lat = lat
            this.lon = lon
        }

        if (index != -1) {
            adapter?.notifyItemChanged(index)
        }
    }
}