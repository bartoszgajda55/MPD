package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;

import java.util.List;

public class IncidentsListAdapter extends ArrayAdapter<Incident> {
  public IncidentsListAdapter(Context context, List<Incident> users) {
    super(context,0, users);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Incident incident = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.incidents_list_item, parent, false);
    }
    // Lookup view for data population
    TextView incidentName = (TextView) convertView.findViewById(R.id.incident_name);
    // Populate the data into the template view using the data object
    incidentName.setText(incident.getTitle()[0]);
    // Return the completed view to render on screen
    return convertView;
  }
}
