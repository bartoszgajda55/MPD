package com.bartoszgajda.mobileplatformdevelopment.util.parser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public abstract class XmlParser {
  public abstract ArrayList<?> parseXmlToJavaObject(InputStream inputStream);
}
