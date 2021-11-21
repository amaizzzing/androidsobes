package com.example.sobes.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sobes.SobesApp
import com.example.sobes.data.entities.NotesEntity
import com.example.sobes.data.services.googlemapsservice.IMapsService
import com.example.sobes.databinding.FragmentMapsBinding
import com.example.sobes.di.ViewModelFactory
import com.example.sobes.ui.dialogs.AddMarkerDialog
import com.example.sobes.ui.states.BaseState
import com.example.sobes.ui.viewmodels.MapFragmentViewModel
import com.example.sobes.utils.setGone
import com.example.sobes.utils.setVisible
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val ADD_MARKER_DIALOG_TAG = "AddMarkerDialog"

class MapsFragment:
    BaseFragment<FragmentMapsBinding, MapFragmentViewModel>(),
    AddMarkerDialog.OnSaveClick
{
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var googleMapsService: IMapsService

    override fun getViewModelClass() = MapFragmentViewModel::class.java
    override fun getViewBinding() = FragmentMapsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SobesApp.appComponent.inject(this)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            binding?.let { fragmentMapBinding ->
                initMap(fragmentMapBinding.map, savedInstanceState)
            }
            initLocationProviderClient()
            viewModel.getNotesData()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "ERROR!", Toast.LENGTH_LONG).show()
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    override fun setupViews() {
        AddMarkerDialog.onSaveImpl = this
        isProgressVisible(true)
    }

    override fun observeData() {
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }
    }

    override fun renderData(data: BaseState) {
        when(data) {
            is BaseState.Success<*> -> {
                isProgressVisible(false)
                (data.resultData as List<NotesEntity>).also {
                    addMarkersToMap(it)
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
                it.map.setGone()
                it.pbFragmentMaps.setVisible()
            } else {
                it.map.setVisible()
                it.pbFragmentMaps.setGone()
            }
        }
    }

    private fun initMap(mapView: MapView, savedInstanceState: Bundle?) {
        MapsInitializer.initialize(requireContext())
        with(mapView) {
            onCreate(savedInstanceState)
            onResume()
            getMapAsync { gMap ->
                with(googleMapsService) {
                    setMap(gMap)
                    setMyLocationEnabled()
                    configMap(
                        {
                            AddMarkerDialog.newInstance(it.latitude, it.longitude)
                                .show(childFragmentManager, ADD_MARKER_DIALOG_TAG)
                        },
                        {
                            AddMarkerDialog.newInstance(it.position.latitude, it.position.longitude)
                                .show(childFragmentManager, ADD_MARKER_DIALOG_TAG)
                        }
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationProviderClient() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                task.result?.let {
                    googleMapsService.moveCamera(it.latitude, it.longitude)
                }
            } else {
                googleMapsService.moveCamera()
            }
        }
    }

    private fun addMarkersToMap(notesList: List<NotesEntity>) = launch {
        withContext(Dispatchers.Main) {
            googleMapsService.addMarkersToMap(notesList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        googleMapsService.clearMap()
    }

    override fun onSaveNewMarker(name: String, description: String, lat: Double, lon: Double) {
        googleMapsService.saveNewMarker(name, lat, lon)
    }
}