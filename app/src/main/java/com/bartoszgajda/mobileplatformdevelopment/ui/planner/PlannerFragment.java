package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class PlannerFragment extends Fragment implements OnMapReadyCallback {

  private PlannerViewModel plannerViewModel;
  private MapView mapView;
  private GoogleMap googleMap;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    plannerViewModel = ViewModelProviders.of(this).get(PlannerViewModel.class);
    View root = inflater.inflate(R.layout.fragment_planner, container, false);

    this.mapView = (MapView) root.findViewById(R.id.planner_map);
    this.mapView.onCreate(savedInstanceState);
    this.mapView.getMapAsync(this);

    return root;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
    LatLng glasgow = new LatLng(55.86515, -4.25763);
    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 6));
    this.googleMap.getUiSettings().setZoomControlsEnabled(true);
  }

  @Override
  public void onResume() {
    super.onResume();
    this.mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    this.mapView.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    this.mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    this.mapView.onLowMemory();
  }
}