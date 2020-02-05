package com.bartoszgajda.mobileplatformdevelopment.util.http;

import android.os.AsyncTask;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.XmlParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SendHttpRequestTask extends AsyncTask<String, Void, ArrayList<?>> {
  private String url;
  private XmlParser parser;
  private AsyncResponse response;

  public SendHttpRequestTask(String url, XmlParser parser, AsyncResponse asyncResponse) {
    this.url = url;
    this.parser = parser;
    this.response = asyncResponse;
  }

  @Override
  protected ArrayList<?> doInBackground(String... urls) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(this.url);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      InputStream inputStream = connection.getInputStream();

      if (inputStream == null)
        return null;

      return this.parser.parseXmlToIncidentsArrayList(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return null;
  }

  @Override
  protected void onPostExecute(ArrayList<?> list) {
    super.onPostExecute(list);
    response.processFinish(list);
  }
}
