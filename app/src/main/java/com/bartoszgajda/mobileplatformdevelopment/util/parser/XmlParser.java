package com.bartoszgajda.mobileplatformdevelopment.util.parser;

import java.io.InputStream;
import java.util.ArrayList;

public abstract class XmlParser {
  public abstract ArrayList<?> parseXmlToIncidentsArrayList(InputStream inputStream);
}
