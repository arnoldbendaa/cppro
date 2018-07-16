package com.cedar.cp.util.xmlreport;

public enum Purpose
{
  XML_FORM, 
  ANALYSER;

  public String toString() {
    switch (ordinal()) {
    case 1:
      return "dataentry";
    case 2:
      return "analyser"; }
    return "";
  }

  public static Purpose parseString(String purpose)
  {
    if (purpose.equals("dataentry")) {
      return XML_FORM;
    }
    return ANALYSER;
  }
}