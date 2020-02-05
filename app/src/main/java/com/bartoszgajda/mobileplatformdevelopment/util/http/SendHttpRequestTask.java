package com.bartoszgajda.mobileplatformdevelopment.util.http;

import android.os.AsyncTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SendHttpRequestTask extends AsyncTask<String, Void, ArrayList<? extends Object>> {
  private AsyncResponse response;
  private String url;

  public SendHttpRequestTask(String url, AsyncResponse asyncResponse) {
    this.url = url;
    this.response = asyncResponse;
  }

  @Override
  protected ArrayList<? extends Object> doInBackground(String... urls) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(this.url);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      InputStream inputStream = connection.getInputStream();

      if (inputStream == null) {
        return null;
      }

      XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
      XmlPullParser parser = parserFactory.newPullParser();
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
      parser.setInput(inputStream, null);

      String tag, text = "";
      ArrayList<Incident> incidents = new ArrayList<>();
      Incident incident = new Incident();

      int event = parser.getEventType();
      while (event != XmlPullParser.END_DOCUMENT) {
        tag = parser.getName();
        switch (event) {
          case XmlPullParser.START_TAG:
            if (tag.equals("item"))
              incident = new Incident();
            break;
          case XmlPullParser.TEXT:
            text = parser.getText();
            break;
          case XmlPullParser.END_TAG:
            switch (tag) {
              case "title":
                incident.setTitle(text);
                break;
              case "description":
                incident.setDescription(text);
                break;
              case "link":
                incident.setLink(text);
                break;
              case "georss:point":
                incident.setCoordinates(text);
                break;
              case "pubDate":
                incident.setPublicationDate(text);
                break;
              case "item":
                incidents.add(incident);
                break;
            }
            break;
        }
        event = parser.next();
      }

      return incidents;
    } catch (IOException | XmlPullParserException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return null;
  }

  @Override
  protected void onPostExecute(ArrayList<? extends Object> list) {
    super.onPostExecute(list);
    response.processFinish(list);
  }
}
