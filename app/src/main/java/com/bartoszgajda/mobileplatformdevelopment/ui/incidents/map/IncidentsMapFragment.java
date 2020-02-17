package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class IncidentsMapFragment extends Fragment implements OnMapReadyCallback {
  GoogleMap googleMap;

  private IncidentsMapViewModel incidentsMapViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    incidentsMapViewModel =
        ViewModelProviders.of(this).get(IncidentsMapViewModel.class);
    View root = inflater.inflate(R.layout.fragment_incidents_map, container, false);

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.incidents_map);
    mapFragment.getMapAsync(this);

    return root;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    googleMap = googleMap;

    LatLng sydney = new LatLng(-34, 151);
    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
  }
}