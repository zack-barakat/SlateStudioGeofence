package com.android.slatestudio.test.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.android.slatestudio.test.R
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.ui.base.BaseMvpActivity
import com.android.slatestudio.test.ui.base.BasePresenter
import com.android.slatestudio.test.ui.creategeofence.CreateGeofenceActivity
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class MainActivity : BaseMvpActivity(), MainContracts.View, OnMapReadyCallback {

    @Inject
    lateinit var mPresenter: MainContracts.Presenter<MainContracts.View>
    private lateinit var locationManager: LocationManager

    private var mMap: GoogleMap? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLayout()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mPresenter.onAttachView(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun sendExtrasToPresenter(extras: Bundle) {

    }

    protected fun setupLayout() {
        btnAddGeofence.setOnClickListener { mPresenter.onAddGeofenceClick() }
        setupMap()
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    public override fun getPresenter(): BasePresenter<*> {
        return mPresenter
    }

    override fun showUpdatedStatus(newStatusText: String) {
        tvStatus.text = newStatusText
    }

    override fun openCreateGeofenceScreen() {
        mMap?.let { map ->
            CreateGeofenceActivity.startActivity(this, map.cameraPosition.target, map.cameraPosition.zoom)
        }
    }

    override fun showUserCurrentLocation() {
        centerMapToCurrentLocationWithPermissionCheck()
    }

    override fun showGeofence(geofenceModel: GeofenceModel) {
        val latLng = LatLng(geofenceModel.lat, geofenceModel.lng)
        val marker = mMap?.addMarker(MarkerOptions().position(latLng))
        marker?.tag = geofenceModel.id

        mMap?.addCircle(
            CircleOptions()
                .center(LatLng(geofenceModel.lat, geofenceModel.lng))
                .radius(geofenceModel.radius)
                .strokeColor(ContextCompat.getColor(this, R.color.colorAccent))
                .fillColor(ContextCompat.getColor(this, R.color.colorAccentTransparent))
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.run {
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.isMapToolbarEnabled = true
        }

        mPresenter.onMapReady()
        centerMapToCurrentLocationWithPermissionCheck()
    }


    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun centerMapToCurrentLocation() {
        mMap?.uiSettings?.isMyLocationButtonEnabled = true
        mMap?.isMyLocationEnabled = true
        val bestProvider = locationManager.getBestProvider(Criteria(), false)
        val location = locationManager.getLastKnownLocation(bestProvider)
        if (location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }
}
