package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class IncidentsMapFragment extends Fragment implements OnMapReadyCallback {
  GoogleMap googleMap;

  private IncidentsMapViewModel incidentsMapViewModel;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    incidentsMapViewModel = ViewModelProviders.of(this).get(IncidentsMapViewModel.class);
    View root = inflater.inflate(R.layout.fragment_incidents_map, container, false);

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.incidents_map);
    mapFragment.getMapAsync(this);

    return root;
  }

  @Override
  public void onMapReady(final GoogleMap googleMap) {

    incidentsMapViewModel.getIncidents().observe(this, new Observer<List<Incident>>() {
      @Override
      public void onChanged(List<Incident> incidents) {
        for (Incident incident: incidents) {
          LatLng marker = new LatLng(Double.parseDouble(incident.getCoordinates()[0]), Double.parseDouble(incident.getCoordinates()[1]));
          googleMap.addMarker(new MarkerOptions().position(marker));
        }
      }
    });

    LatLng glasgow = new LatLng(55.86515, -4.25763);
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 6));
  }
}