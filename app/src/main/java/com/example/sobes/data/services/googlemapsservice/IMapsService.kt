package com.example.sobes.data.services.googlemapsservice

import com.example.sobes.data.entities.NotesEntity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

interface IMapsService {
    fun setMap(gMap: GoogleMap)

    fun getMap(): GoogleMap?

    fun saveNewMarker(name: String, lat: Double, lon: Double)

    fun addMarkersToMap(notesList: List<NotesEntity>)

    fun moveCamera(
        lat: Double = GoogleMapsService.DEFAULT_LAT,
        lon: Double = GoogleMapsService.DEFAULT_LON,
        zoom: Float = GoogleMapsService.DEFAULT_ZOOM
    )

    fun configMap(
        onMapClick: (latLon: LatLng) -> Unit,
        onInfoWindowClick: (marker: Marker) -> Unit
    )

    fun setMyLocationEnabled()

    fun clearMap()
}