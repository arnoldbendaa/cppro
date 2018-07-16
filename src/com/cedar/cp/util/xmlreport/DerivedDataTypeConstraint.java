package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Writer;

public class DerivedDataTypeConstraint extends BaseDataTypeConstraint
{
  private String mFormula;
  private String mParsedFormula;

  public String getFormula()
  {
    return mFormula;
  }

  public void setFormula(String formula) {
    mFormula = formula;
  }

  public String getParsedFormula() {
    return mParsedFormula;
  }

  public void setParsedFormula(String parsedFormula) {
    mParsedFormula = parsedFormula;
  }

  public void writeXml(Writer out) throws IOException
  {
    out.write("<derivedDataType");
    out.write(getXML());
    if (mFormula != null) {
      out.write(" formula=\"" + XmlUtils.escapeStringForXML(mFormula) + "\" ");
    }
    if (mParsedFormula != null)
      out.write(" parsedFormula=\"" + XmlUtils.escapeStringForXML(mParsedFormula) + "\" ");
    out.write(" />");
  }

  public String getSqlPredicate(String col)
  {
    return "";
  }

  public String[] getBindVariables()
  {
    return new String[0];
  }
}