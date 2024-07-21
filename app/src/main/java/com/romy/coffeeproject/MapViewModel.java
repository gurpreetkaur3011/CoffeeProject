package com.romy.coffeeproject;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewModel extends ViewModel {

    public void getMap(SupportMapFragment mapFragment) {

        MarkerOptions markerOptions = new MarkerOptions();
        MarkerOptions markerOptions1 = new MarkerOptions();
        LatLng CoffeeOne = new LatLng(55.74100568878398, 37.57804483107259);
        LatLng CoffeeTwo = new LatLng(55.77046797310417, 37.62782662634571);
        LatLng Toronto = new LatLng(43.65107, -79.347015); // Toronto coordinates
        markerOptions.position(CoffeeOne).title("Our point").snippet("Opening hours: 6:00 - 22:00");
        markerOptions1.position(CoffeeTwo).title("Our point").snippet("Opening hours: 7:30 - 23:30");

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(markerOptions);
                googleMap.addMarker(markerOptions1);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Toronto,12));
            }
        });
    }
}
