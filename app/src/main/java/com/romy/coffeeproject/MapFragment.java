package com.romy.coffeeproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private static final String TAG = "MapFragment";
    private static final LatLng TORONTO = new LatLng(43.7, -79.42); // Toronto coordinates

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Inflating the layout");
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Log.d(TAG, "onCreateView: Getting the map fragment");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            Log.d(TAG, "onCreateView: Map fragment found, setting up async map");
            mapFragment.getMapAsync(this);
        } else {
            Log.e(TAG, "onCreateView: Map fragment is null");
        }

        Log.d(TAG, "onCreateView: Getting the FusedLocationProviderClient");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Initialize LocationRequest and LocationCallback
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000) // 10 seconds
                .setFastestInterval(5000); // 5 seconds

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.e(TAG, "onLocationResult: Location result is null");
                    return;
                }
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.d(TAG, "onLocationResult: Location found, updating map");
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("You are here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

                    // Add markers for nearby stores
                    addNearbyStores(userLocation);
                } else {
                    Log.e(TAG, "onLocationResult: Location is null");
                    Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Button findNearestButton = view.findViewById(R.id.btn_find_nearest);
        findNearestButton.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Find Nearest button clicked");
            findNearestStores();
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;

        // Set the default location to Toronto, Canada
        Log.d(TAG, "onMapReady: Moving camera to Toronto");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TORONTO, 12));
        mMap.addMarker(new MarkerOptions().position(TORONTO).title("Toronto"));

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onMapReady: Location permission granted, enabling MyLocation layer");
            mMap.setMyLocationEnabled(true);
        } else {
            Log.d(TAG, "onMapReady: Requesting location permission");
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void findNearestStores() {
        Log.d(TAG, "findNearestStores: Finding nearest stores");
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "findNearestStores: Location permission granted, requesting location updates");
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            Log.d(TAG, "findNearestStores: Requesting location permission");
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void addNearbyStores(LatLng userLocation) {
        Log.d(TAG, "addNearbyStores: Adding nearby stores at location: " + userLocation.toString());
        // Example store locations, replace with actual store data
        LatLng[] storeLocations = {
                new LatLng(userLocation.latitude + 0.01, userLocation.longitude),
                new LatLng(userLocation.latitude - 0.01, userLocation.longitude),
                new LatLng(userLocation.latitude, userLocation.longitude + 0.01)
        };

        for (LatLng storeLocation : storeLocations) {
            Log.d(TAG, "addNearbyStores: Adding marker at " + storeLocation.toString());
            mMap.addMarker(new MarkerOptions().position(storeLocation).title("Coffee Shop"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: Permissions result received for requestCode: " + requestCode);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Location permission granted, enabling MyLocation layer");
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Location permission denied, showing toast");
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location updates
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
