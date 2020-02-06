package com.bartoszgajda.mobileplatformdevelopment.util.parser;

import com.bartoszgajda.mobileplatformdevelopment.util.model.PlannedRoadwork;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class PlannedRoadworksXmlParser extends XmlParser {
  @Override
  public ArrayList<PlannedRoadwork> parseXmlToIncidentsArrayList(InputStream inputStream) {
    try {
      XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
      XmlPullParser parser = parserFactory.newPullParser();
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
      parser.setInput(inputStream, null);

      String tag, text = "";
      ArrayList<PlannedRoadwork> roadworks = new ArrayList<>();
      PlannedRoadwork roadwork = new PlannedRoadwork();

      int event = parser.getEventType();
      while (event != XmlPullParser.END_DOCUMENT) {
        tag = parser.getName();
        switch (event) {
          case XmlPullParser.START_TAG:
            if (tag.equals("item"))
              roadwork = new PlannedRoadwork();
            break;
          case XmlPullParser.TEXT:
            text = parser.getText();
            break;
          case XmlPullParser.END_TAG:
            switch (tag) {
              case "title":
                roadwork.setTitle(text);
                break;
              case "description":
                roadwork.setDescription(text);
                break;
              case "link":
                roadwork.setLink(text);
                break;
              case "georss:point":
                roadwork.setCoordinates(text.split("\\s"));
                break;
              case "pubDate":
                roadwork.setPublicationDate(new Date(text));
                break;
              case "item":
                roadworks.add(roadwork);
                break;
            }
            break;
        }
        event = parser.next();
      }
      if (inputStream != null) {
        inputStream.close();
      }

      return roadworks;
    } catch (XmlPullParserException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
