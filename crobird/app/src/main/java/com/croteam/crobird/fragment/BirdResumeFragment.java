package com.croteam.crobird.fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.croteam.crobird.CroDetailActivity;
import com.croteam.crobird.MainActivity;
import com.croteam.crobird.R;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BirdResumeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public BirdResumeFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_mail)
    TextView tvEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_dob)
    TextView tvDob;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.mapView)
    MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_bird_resume, container, false);
        ButterKnife.bind(this, root);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        initResume();
        return root;
    }

    private void initResume(){
        tvJob.setText(((CroDetailActivity)getActivity()).user.getJob());
        tvEmail.setText(((CroDetailActivity)getActivity()).user.getEmail());
        tvPhone.setText(((CroDetailActivity)getActivity()).user.getPhone());
        tvDob.setText(((CroDetailActivity)getActivity()).user.getDob());
        tvAddress.setText(((CroDetailActivity)getActivity()).user.getAddress());

        float distance = Utils.distanceBetween(MainActivity.user.getLat(), MainActivity.user.getLng(), ((CroDetailActivity)getActivity()).user.getLat(), ((CroDetailActivity)getActivity()).user.getLng());
        tvDistance.setText(String.valueOf(Math.ceil(distance/1000)) + " km");
    }

    private void  moveCameraTo(Location location){
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title("Vị trí cửa hàng");
        markerOptions.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        gmap.addMarker(markerOptions);
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        gmap.setOnMapClickListener(this);
        Location location = new Location(((CroDetailActivity)getActivity()).user.getName());
        location.setLatitude(((CroDetailActivity)getActivity()).user.getLat());
        location.setLongitude(((CroDetailActivity)getActivity()).user.getLng());
        moveCameraTo(location);
        Log.d(AppConstants.TAG, "Map ready roi nè");
    }
}
