package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class IncidentsListAdapter extends ArrayAdapter<Incident> {
  public IncidentsListAdapter(Context context, List<Incident> incidents) {
    super(context,0, incidents);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Incident incident = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.incidents_list_item, parent, false);
    }
    TextView incidentTitle = (TextView) convertView.findViewById(R.id.incident_title);
    TextView incidentDescription = (TextView) convertView.findViewById(R.id.incident_description);
    TextView incidentLink = (TextView) convertView.findViewById(R.id.incident_link);
    TextView incidentPubdate = (TextView) convertView.findViewById(R.id.incident_pubdate);

    incidentTitle.setText(incident.getTitle());
    incidentDescription.setText(incident.getDescription());
    incidentLink.setText("More: " + incident.getLink());
    incidentPubdate.setText("Published: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(incident.getPublicationDate()));

    return convertView;
  }
}
