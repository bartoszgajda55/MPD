package com.bartoszgajda.mobileplatformdevelopment.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendHttpRequestTask extends AsyncTask<String, Void, Object> {
  public AsyncResponse response = null;
  private String url;

  public SendHttpRequestTask(String url, AsyncResponse asyncResponse) {
    this.url = url;
    this.response = asyncResponse;
  }

  @Override
  protected Object doInBackground(String... urls) {
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    String xmlString = null;
    try {
      URL url = new URL(this.url);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      InputStream inputStream = connection.getInputStream();
      StringBuffer buffer = new StringBuffer();

      if (inputStream == null) {
        return null;
      }
      reader = new BufferedReader(new InputStreamReader(inputStream));

      String line;
      while((line = reader.readLine()) != null) {
        buffer.append(line + "\n");
      }

      if(buffer.length() == 0) {
        return null;
      }

      xmlString = buffer.toString();
      return xmlString;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          Log.e("api", "Error closing stream", e);
        }
      }
    }
    return null;
  }

  @Override
  protected void onPostExecute(Object object) {
    super.onPostExecute(object);
    response.processFinish(object);
  }
}
