package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.format.ReportCellFormat;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class ReportFormat
  implements XMLWritable, Serializable
{
  private String mCriteria;
  private Purpose mPurpose;
  private ReportCellFormat mCellFormat;

  public ReportFormat()
  {
  }

  public ReportFormat(Purpose purpose, String criteria, ReportCellFormat format)
  {
    mCriteria = (criteria != null ? criteria : null);
    mPurpose = purpose;
    mCellFormat = format;
  }

  public ReportFormat(ReportFormat format)
  {
    mCriteria = format.getCriteria();
    mCellFormat = new ReportCellFormat(format.getCellFormat());
    mPurpose = format.getPurpose();
  }

  public String getCriteria() {
    return mCriteria;
  }

  public void setCriteria(String Criteria) {
    mCriteria = Criteria;
  }

  public Purpose getPurpose() {
    return mPurpose;
  }

  public void setReportPurpose(String purpose) {
    mPurpose = Purpose.parseString(purpose);
  }

  public ReportCellFormat getCellFormat() {
    return mCellFormat;
  }

  public void setCellFormat(ReportCellFormat cellFormat)
  {
    mCellFormat = cellFormat;
  }

  public void writeXml(Writer out) throws IOException
  {
    out.write("<reportFormat ");
    XmlUtils.outputAttribute(out, "reportPurpose", mPurpose.toString());
    if (mCriteria != null) {
      XmlUtils.outputAttribute(out, "criteria", mCriteria);
    }
    out.write(">");
    mCellFormat.writeXml(out);
		out.write("</reportFormat>");
	}
}