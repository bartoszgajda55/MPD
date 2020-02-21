package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.RoadworkModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class RoadworksListAdapter extends ArrayAdapter<RoadworkModel> {
  public RoadworksListAdapter(Context context, List<RoadworkModel> roadworks) {
    super(context,0, roadworks);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    RoadworkModel roadwork = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.roadworks_list_item, parent, false);
    }
    TextView incidentTitle = (TextView) convertView.findViewById(R.id.roadwork_title);
    TextView incidentDescription = (TextView) convertView.findViewById(R.id.roadwork_description);
    TextView incidentLink = (TextView) convertView.findViewById(R.id.roadwork_link);
    TextView incidentPubdate = (TextView) convertView.findViewById(R.id.roadwork_pubdate);

    incidentTitle.setText(roadwork.getTitle());
    incidentDescription.setText(Html.fromHtml(roadwork.getDescription()));
    incidentLink.setText("More: " + roadwork.getLink());
    incidentPubdate.setText("Published: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(roadwork.getPublicationDate()));

    return convertView;
  }
}
