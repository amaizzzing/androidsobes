package com.example.sobes.data.services.googlemapsservice

import android.annotation.SuppressLint
import com.example.sobes.data.entities.NotesEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapsService: IMapsService {
    private var googleMap: GoogleMap? = null

    private val markerHashMap = hashMapOf<String, Marker>()

    override fun setMap(gMap: GoogleMap) {
        googleMap = gMap
    }

    override fun getMap(): GoogleMap? = googleMap

    override fun saveNewMarker(name: String, lat: Double, lon: Double) {
        googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(lat, lon))
                .title(name)
                .icon(BitmapDescriptorFactory.defaultMarker(DEFAULT_MARKER))
        )?.let {
            getMarker("$lat $lon")?.remove()
            setMarkerToMap("$lat $lon", it)
        }
    }

    override fun addMarkersToMap(notesList: List<NotesEntity>) {
        notesList
            .map {
                MarkerOptions().apply {
                    position(LatLng(it.lat, it.lon))
                    title(it.name)
                    icon(BitmapDescriptorFactory.defaultMarker(DEFAULT_MARKER))
                }
            }
            .forEach { marker ->
                googleMap
                    ?.addMarker(marker)
                    ?.let {
                        setMarkerToMap("${marker.position.latitude} ${marker.position.longitude}", it)
                    }
        }
    }

    override fun moveCamera(lat: Double, lon: Double, zoom: Float) {
        googleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(lat, lon), zoom
            )
        )
    }

    private fun setMarkerToMap(key: String, marker: Marker) {
        markerHashMap[key] = marker
    }

    private fun getMarker(key: String): Marker? = markerHashMap[key]


    override fun configMap(
        onMapClick: (latLon: LatLng) -> Unit,
        onInfoWindowClick: (marker: Marker) -> Unit
    ) {
        googleMap?.let { gMap ->
            gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            with(gMap.uiSettings) {
                isZoomControlsEnabled = true
                isMyLocationButtonEnabled = true
                isCompassEnabled = true
            }
            gMap.setOnMapClickListener { latLon ->
                onMapClick.invoke(latLon)
            }
            gMap.setOnInfoWindowClickListener {
                onInfoWindowClick.invoke(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun setMyLocationEnabled() {
        googleMap?.isMyLocationEnabled = true
    }

    override fun clearMap() {
        googleMap = null
    }

    companion object {
        const val DEFAULT_ZOOM = 8.0f
        const val DEFAULT_LAT = 40.26335164
        const val DEFAULT_LON = 56.26392814
        const val DEFAULT_MARKER = BitmapDescriptorFactory.HUE_VIOLET
    }
}