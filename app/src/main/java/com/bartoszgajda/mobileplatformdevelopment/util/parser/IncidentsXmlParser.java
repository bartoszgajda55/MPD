package com.bartoszgajda.mobileplatformdevelopment.util.parser;

import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class IncidentsXmlParser extends XmlParser {
  @Override
  public ArrayList<Incident> parseXmlToJavaObject(InputStream inputStream) {
    try {
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
                incident.setCoordinates(text.split("\\s"));
                break;
              case "pubDate":
                incident.setPublicationDate(new Date(text));
                break;
              case "item":
                incidents.add(incident);
                break;
            }
            break;
        }
        event = parser.next();
      }
      if (inputStream != null) {
        inputStream.close();
      }

      return incidents;
    } catch (XmlPullParserException | IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
