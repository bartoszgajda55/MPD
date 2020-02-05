package com.bartoszgajda.mobileplatformdevelopment.util.http;

import java.util.ArrayList;

public interface AsyncResponse {
  void processFinish(ArrayList<? extends Object> output);
}
