package com.android.slatestudio.test.ui.creategeofence

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.android.slatestudio.test.R
import com.android.slatestudio.test.data.model.GeofenceModel
import com.android.slatestudio.test.ui.base.BaseMvpActivity
import com.android.slatestudio.test.ui.base.BasePresenter
import kotlinx.android.synthetic.main.activity_create_geofence.*
import javax.inject.Inject

class CreateGeofenceActivity : BaseMvpActivity(), CreateGeofenceContracts.View, OnMapReadyCallback {


    companion object {
        private const val EXTRA_LAT_LNG = "EXTRA_LAT_LNG"
        private const val EXTRA_ZOOM = "EXTRA_ZOOM"

        fun startActivity(context: Context, latLng: LatLng, zoom: Float) {
            val intent = Intent(context, CreateGeofenceActivity::class.java)
            intent.putExtra(EXTRA_LAT_LNG, latLng)
            intent.putExtra(EXTRA_ZOOM, zoom)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var mPresenter: CreateGeofenceContracts.Presenter<CreateGeofenceContracts.View>

    private lateinit var locationManager: LocationManager

    private var mMap: GoogleMap? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_geofence)
        setupLayout()
        mPresenter.onAttachView(this)
    }

    override fun sendExtrasToPresenter(extras: Bundle) {
        val latLng = extras[EXTRA_LAT_LNG] as LatLng
        val zoom = extras[EXTRA_ZOOM] as Float
        mPresenter.initPresenter(latLng, zoom)
    }

    private fun setupLayout() {
        setupMap()
        btnSelectLocation.setOnClickListener {
            mMap?.run {
                mPresenter.onSelectLocation(cameraPosition.target)
            }
        }
        btnCreateGeofence.setOnClickListener { mPresenter.onCreateGeofenceClick() }
        sbRadiusBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mPresenter.onUpdateRadius(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    public override fun getPresenter(): BasePresenter<*> {
        return mPresenter
    }

    override fun showSelectLocationView() {
        sbRadiusBar.visibility = View.GONE
        btnCreateGeofence.visibility = View.GONE
        tvRadiusDescription.visibility = View.GONE
        ivMarker.visibility = View.VISIBLE
        tvSelectLocationLabel.visibility = View.VISIBLE
        btnSelectLocation.visibility = View.VISIBLE
    }

    override fun showSelectRadiusView() {
        sbRadiusBar.visibility = View.VISIBLE
        btnCreateGeofence.visibility = View.VISIBLE
        tvRadiusDescription.visibility = View.VISIBLE
        ivMarker.visibility = View.GONE
        tvSelectLocationLabel.visibility = View.GONE
        btnSelectLocation.visibility = View.GONE
    }

    override fun showGeofenceAddedSuccessfully() {
        finish()
    }

    override fun centerMap(extraLatLng: LatLng, extraZoom: Float) {
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(extraLatLng, extraZoom))
    }

    override fun showUpdatedGeofence(geofenceModel: GeofenceModel, radiusLabel: String) {
        mMap?.clear()
        mMap?.addCircle(
            CircleOptions()
                .center(LatLng(geofenceModel.lat, geofenceModel.lng))
                .radius(geofenceModel.radius)
                .strokeColor(ContextCompat.getColor(this, R.color.colorAccent))
                .fillColor(ContextCompat.getColor(this, R.color.colorAccentTransparent))
        )
        tvRadiusDescription.text = radiusLabel
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.run {
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.isMapToolbarEnabled = true
        }

        mPresenter.onMapReady()
    }
}
