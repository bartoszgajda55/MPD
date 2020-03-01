package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bartoszgajda.mobileplatformdevelopment.R;
import com.bartoszgajda.mobileplatformdevelopment.util.model.RoadworkModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
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
    TextView roadworkTitle = (TextView) convertView.findViewById(R.id.roadwork_title);
    TextView roadworkType = (TextView) convertView.findViewById(R.id.roadwork_type);
    TextView roadworkLength = (TextView) convertView.findViewById(R.id.roadwork_length);
    TextView roadworkDescription = (TextView) convertView.findViewById(R.id.roadwork_description);
    TextView roadworkLink = (TextView) convertView.findViewById(R.id.roadwork_link);
    TextView roadworkPubdate = (TextView) convertView.findViewById(R.id.roadwork_pubdate);

    roadworkTitle.setText(roadwork.getTitle());
    roadworkDescription.setText(Html.fromHtml(roadwork.getDescription()));
    roadworkLink.setText("More: " + roadwork.getLink());
    roadworkPubdate.setText("Published: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(roadwork.getPublicationDate()));

    if (roadwork.getType().equals("current")) {
      roadworkType.setText(R.string.roadworks_type_current);
      roadworkType.setTextColor(parent.getResources().getColor(R.color.brandGreen));
    } else {
      roadworkType.setText(R.string.roadworks_type_planned);
      roadworkType.setTextColor(parent.getResources().getColor(R.color.brandYellow));
    }

    String[] splitDescription = roadwork.getDescription().split("\\s+");
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    try {
      Date startDate = new SimpleDateFormat("dd/MMMM/yyyy").parse(splitDescription[3] + "/" + splitDescription[4] + "/" + splitDescription[5]);
      startCalendar.setTime(startDate);
      Date endDate = new SimpleDateFormat("dd/MMMM/yyyy").parse(splitDescription[11] + "/" + splitDescription[12] + "/" + splitDescription[13]);
      endCalendar.setTime(endDate);

      Long lengthInDays = TimeUnit.MILLISECONDS.toDays(Math.abs(startCalendar.getTimeInMillis() - endCalendar.getTimeInMillis()));
      roadworkLength.setText("Roadwork Length in Days: " + lengthInDays);
    } catch (ParseException e) {
      Log.e("roadwork", e.getMessage());
    }

    return convertView;
  }
}
