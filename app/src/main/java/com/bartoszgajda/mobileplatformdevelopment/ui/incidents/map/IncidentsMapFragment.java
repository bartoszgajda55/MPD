package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.map.IconConverter;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class IncidentsMapFragment extends Fragment implements OnMapReadyCallback, OnMarkerClickListener {
  private HashMap<LatLng, Incident> markerIncidentHashMap = new HashMap<>();
  private IncidentsMapViewModel incidentsMapViewModel;
  private IconConverter iconConverter = IconConverter.getInstance();

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    incidentsMapViewModel = ViewModelProviders.of(this).get(IncidentsMapViewModel.class);
    View root = inflater.inflate(R.layout.fragment_incidents_map, container, false);

    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.incidents_map);
    mapFragment.getMapAsync(this);

    return root;
  }

  @Override
  public void onMapReady(final GoogleMap googleMap) {

    incidentsMapViewModel.getIncidents().observe(this, incidents -> {
      for (Incident incident: incidents) {
        LatLng marker = new LatLng(Double.parseDouble(incident.getCoordinates()[0]), Double.parseDouble(incident.getCoordinates()[1]));
        markerIncidentHashMap.put(marker, incident);
        Bitmap icon = iconConverter.getMarkerBitmapFromDrawable((getResources().getDrawable(R.drawable.announcement_24px_red)));
        Bitmap largerIcon = Bitmap.createScaledBitmap(icon, 120, 120, false);
        googleMap.addMarker(new MarkerOptions().position(marker).icon(BitmapDescriptorFactory.fromBitmap(largerIcon)));
      }
    });

    LatLng glasgow = new LatLng(55.86515, -4.25763);
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 6));
    googleMap.setOnMarkerClickListener(this);
    googleMap.getUiSettings().setZoomControlsEnabled(true);
  }

  @Override
  public boolean onMarkerClick(Marker marker) {
    IncidentsMapDialogFragment mapDialogFragment = new IncidentsMapDialogFragment(markerIncidentHashMap.get(marker.getPosition()));
    mapDialogFragment.show(getChildFragmentManager(), "dialog");
    return false;
  }
}