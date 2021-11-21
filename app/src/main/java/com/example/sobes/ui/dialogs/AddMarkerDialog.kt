package com.example.sobes.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.sobes.R
import com.example.sobes.SobesApp
import com.example.sobes.data.entities.NotesEntity
import com.example.sobes.di.ViewModelFactory
import com.example.sobes.ui.states.BaseState
import com.example.sobes.ui.viewmodels.AddMarkerDialogViewModel
import com.example.sobes.utils.setGone
import com.example.sobes.utils.setVisible
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddMarkerDialog: DialogFragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext =
        Dispatchers.IO + SupervisorJob()

    interface OnSaveClick {
        fun onSaveNewMarker(name: String, description: String, lat: Double, lon: Double)
    }

    private var viewModel: AddMarkerDialogViewModel? = null

    private var lat: Double? = null
    private var lon: Double? = null

    private lateinit var dialogLat: TextView
    private lateinit var dialogLon: TextView
    private lateinit var noteDialogName: EditText
    private lateinit var noteDialogDescription: EditText
    private lateinit var dialogCancelButton: Button
    private lateinit var dialogOkButton: Button
    private lateinit var pbMarkerDialog: ProgressBar

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SobesApp.appComponent.inject(this)

        lat = arguments?.getDouble(LAT)
        lon = arguments?.getDouble(LON)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_marker, container, false)

        initViews(view)
        initViewModel()
        initListeners()
        getInitialData()

        return view
    }

    private fun getInitialData() {
        lat?.let { lat ->
            lon?.let { lon ->
                viewModel?.getDialogData(lat, lon)
            }
        }
    }

    private fun initViews(view: View) {
        with(view) {
            dialogLat = findViewById(R.id.dialog_lat)
            dialogLon = findViewById(R.id.dialog_lon)
            noteDialogName = findViewById(R.id.note_name_dialog)
            noteDialogDescription = findViewById(R.id.note_dialog)
            dialogCancelButton = findViewById(R.id.dialog_cancel_button)
            dialogOkButton = findViewById(R.id.dialog_ok_button)
            pbMarkerDialog = findViewById(R.id.pb_add_marker_dialog)
        }

        dialogLat.text = getString(R.string.lat_dialog_text, lat)
        dialogLon.text = getString(R.string.lon_dialog_text, lon)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[AddMarkerDialogViewModel::class.java]

        viewModel?.data?.observe(viewLifecycleOwner) {
            it?.let { resultData ->
                renderData(resultData)
            }  ?: renderData(BaseState.Error(Error()))
        }
    }

    private fun initListeners() {
        dialogCancelButton.setOnClickListener {
            closeDialog(this)
        }

        dialogOkButton.setOnClickListener {
            if (checkFields()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.error_dialog_name),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            launch(coroutineContext) {
                if (lat != null && lon != null) {
                    saveOrUpdateNote(
                        name = noteDialogName.text.toString(),
                        description = noteDialogDescription.text.toString(),
                        lat = lat!!,
                        lon = lon!!
                    )
                    saveNewMarker(
                        name = noteDialogName.text.toString(),
                        description = noteDialogDescription.text.toString(),
                        lat = lat!!,
                        lon = lon!!
                    )
                } else {
                    Toast.makeText(requireContext(), "ERROR!", Toast.LENGTH_LONG).show()
                }
                withContext(Dispatchers.Main) {
                    closeDialog(this@AddMarkerDialog)
                }
            }
        }
    }

    private fun checkFields(): Boolean =
        noteDialogName.text.toString().isBlank()

    private suspend fun saveNewMarker(
        name: String,
        description: String,
        lat: Double,
        lon: Double
    ) = withContext(Dispatchers.Main) {
        onSaveImpl?.onSaveNewMarker(
            name,
            description,
            lat,
            lon
        )
    }

    private fun closeDialog(fragment: AddMarkerDialog) {
        parentFragmentManager
            .beginTransaction()
            .remove(fragment)
            .commit()
    }

    private suspend fun saveOrUpdateNote(
        name: String,
        description: String,
        lat: Double,
        lon: Double
    ) {
        viewModel?.getNoteByLatLon(lat, lon)?.let {
            viewModel?.updateNote(
                name = name,
                text = description,
                lat = lat,
                lon = lon
            )
        } ?: run {
            viewModel?.insertNote(
                lat = lat,
                lon = lon,
                name = name,
                text = description,
            )
        }
    }

    private fun renderData(data: BaseState) {
        when(data) {
            is BaseState.Success<*> -> {
                pbMarkerDialog.setGone()
                data.resultData?.let { resultData ->
                    (resultData as NotesEntity).also {
                        noteDialogName.setText(it.name)
                        noteDialogDescription.setText(it.text)
                    }
                }
            }
            is BaseState.Error -> {
                pbMarkerDialog.setGone()
                Toast.makeText(requireContext(), "ERROR!", Toast.LENGTH_LONG).show()
            }
            BaseState.Loading -> {
                pbMarkerDialog.setVisible()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.8).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.6).toInt()
        dialog?.window?.apply {
            setLayout(width, height)
            setBackgroundDrawableResource(R.drawable.round_corners)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        coroutineContext.cancel()
    }

    companion object {
        fun newInstance(
            lat: Double,
            lon: Double
        ): AddMarkerDialog =
            AddMarkerDialog().apply {
                this.arguments = bundleOf(
                    LAT to lat,
                    LON to lon
                )
            }

        var onSaveImpl: OnSaveClick? = null

        const val LAT = "lat"
        const val LON = "lon"
    }
}