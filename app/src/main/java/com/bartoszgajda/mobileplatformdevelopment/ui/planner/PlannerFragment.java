package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

public class PlannerFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

  private PlannerViewModel plannerViewModel;
  private EditText origin;
  private EditText destination;
  private Button planButton;
  private MapView mapView;
  private GoogleMap googleMap;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    plannerViewModel = ViewModelProviders.of(this).get(PlannerViewModel.class);
    View root = inflater.inflate(R.layout.fragment_planner, container, false);

    this.origin = (EditText) root.findViewById(R.id.planner_origin);
    this.destination = (EditText) root.findViewById(R.id.planner_destination);
    this.planButton = (Button) root.findViewById(R.id.planner_plan);
    this.mapView = (MapView) root.findViewById(R.id.planner_map);

    this.mapView.onCreate(savedInstanceState);
    this.mapView.getMapAsync(this);

    this.planButton.setOnClickListener(this);

    return root;
  }

  @Override
  public void onClick(View view) {
    Log.d("planner", "Origin: " + this.origin.getText());
    Log.d("planner", "Destination: " + this.destination.getText());
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;

    LatLng glasgow = new LatLng(55.86515, -4.25763);
    this.googleMap.addMarker(new MarkerOptions().position(glasgow));
    LatLng edinburgh = new LatLng(55.953251, -3.188267);
    this.googleMap.addMarker(new MarkerOptions().position(edinburgh));

    List<LatLng> polyline = this.getPolyLineBetweenPlaces(glasgow, edinburgh);
    PolylineOptions polylineOptions = new PolylineOptions().addAll(polyline).color(Color.BLACK).width(15);
    this.googleMap.addPolyline(polylineOptions);

    boolean isOnPath = PolyUtil.isLocationOnPath(glasgow, polyline, false, 100.0);
    Log.d("isOnPath", Boolean.toString(isOnPath));

    this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(glasgow, 6));
    this.googleMap.getUiSettings().setZoomControlsEnabled(true);
  }

  private List<LatLng> getPolyLineBetweenPlaces(LatLng origin, LatLng destination) {
    String originString = origin.latitude + "," + origin.longitude;
    String destinationString = destination.latitude + "," + destination.longitude;

    List<LatLng> path = new ArrayList<>();
    GeoApiContext context = new GeoApiContext.Builder().apiKey(getResources().getString(R.string.google_maps_key)).build();
    DirectionsApiRequest req = DirectionsApi.getDirections(context, originString, destinationString);
    try {
      DirectionsResult res = req.await();
      //Loop through legs and steps to get encoded polylines of each step
      for (DirectionsRoute route : res.routes) {
        for (DirectionsLeg leg : route.legs) {
          for (DirectionsStep step : leg.steps) {
            if (step.steps != null && step.steps.length > 0) {
              for (DirectionsStep step1 : step.steps) {
                EncodedPolyline polyline = step1.polyline;
                if (polyline == null) {
                  continue;
                }
                List<com.google.maps.model.LatLng> coordinates = polyline.decodePath();
                for (com.google.maps.model.LatLng coordinate : coordinates) {
                  path.add(new LatLng(coordinate.lat, coordinate.lng));
                }
              }
            } else {
              EncodedPolyline points = step.polyline;
              if (points == null) {
                continue;
              }
              List<com.google.maps.model.LatLng> coords = points.decodePath();
              for (com.google.maps.model.LatLng coord : coords) {
                path.add(new LatLng(coord.lat, coord.lng));
              }
            }
          }
        }
      }
    } catch (Exception e) {
      Log.e("planner", e.getMessage());
    }
    return path;
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