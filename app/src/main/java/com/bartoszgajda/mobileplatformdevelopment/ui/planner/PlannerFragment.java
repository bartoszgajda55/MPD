package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import android.graphics.Bitmap;
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
import com.bartoszgajda.mobileplatformdevelopment.util.map.IconConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.GeocodingResult;

import java.util.ArrayList;
import java.util.List;

public class PlannerFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

  private PlannerViewModel plannerViewModel;
  private EditText origin;
  private EditText destination;
  private Button planButton;
  private MapView mapView;
  private GoogleMap googleMap;
  private IconConverter iconConverter = IconConverter.getInstance();
  private GeoApiContext geoApiContext;
  private Polyline currentPolyline;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    this.geoApiContext = new GeoApiContext.Builder().apiKey(getResources().getString(R.string.google_maps_key)).build();
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
    String originName = "Glasgow, UK";
    String destinationName = "Edinburgh, UK";
    drawRouteBetweenTwoLocationAddresses(originName, destinationName);
  }

  private void drawRouteBetweenTwoLocationAddresses(String origin, String destination) {
    LatLng[] route = getLatLngFromOriginAndDestinationLocationAddresses(origin, destination);
    drawLocationAddressMarkers(route[0], route[1]);

    List<LatLng> polyline = this.getPolyLineBetweenLocationAddresses(route[0], route[1]);
    this.currentPolyline = drawPolylineOnGoogleMap(polyline, this.googleMap);

    setMapCameraOnLatLng(this.googleMap, route[0], 6);

//    boolean isOnPath = PolyUtil.isLocationOnPath(glasgow, polyline, false, 100.0);
//    Log.d("isOnPath", Boolean.toString(isOnPath));
  }

  private void setMapCameraOnLatLng(GoogleMap googleMap, LatLng position, int zoom) {
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
    googleMap.getUiSettings().setZoomControlsEnabled(true);
  }

  private Polyline drawPolylineOnGoogleMap(List<LatLng> polyline, GoogleMap googleMap) {
    PolylineOptions polylineOptions = new PolylineOptions().addAll(polyline).color(Color.BLACK).width(15);
    return googleMap.addPolyline(polylineOptions);
  }

  private void drawLocationAddressMarkers(LatLng origin, LatLng destination) {
    Bitmap icon = iconConverter.getMarkerBitmapFromDrawable((getResources().getDrawable(R.drawable.place_24px)));
    Bitmap largerIcon = Bitmap.createScaledBitmap(icon, 120, 120, false);

    this.googleMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.fromBitmap(largerIcon)));
    this.googleMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory.fromBitmap(largerIcon)));
  }

  private LatLng[] getLatLngFromOriginAndDestinationLocationAddresses(String origin, String destination) {
    GeocodingApiRequest originReq = GeocodingApi.geocode(this.geoApiContext, origin);
    GeocodingApiRequest destinationReq = GeocodingApi.geocode(this.geoApiContext, destination);
    try {
      GeocodingResult[] originRes = originReq.await();
      GeocodingResult[] destinationRes = destinationReq.await();

      LatLng originLatLng = new LatLng(originRes[0].geometry.location.lat, originRes[0].geometry.location.lng);
      LatLng destinationLatLng = new LatLng(destinationRes[0].geometry.location.lat, destinationRes[0].geometry.location.lng);

      return new LatLng[]{originLatLng, destinationLatLng};
    } catch (Exception e) {
      Log.e("planner", e.getMessage());
    }
    return null;
  }

  private List<LatLng> getPolyLineBetweenLocationAddresses(LatLng origin, LatLng destination) {
    String originString = origin.latitude + "," + origin.longitude;
    String destinationString = destination.latitude + "," + destination.longitude;

    List<LatLng> path = new ArrayList<>();
    DirectionsApiRequest req = DirectionsApi.getDirections(this.geoApiContext, originString, destinationString);
    try {
      DirectionsResult res = req.await();
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