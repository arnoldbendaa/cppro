package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.flatform.model.format.ReportCellFormat;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Format
  implements XMLWritable, Serializable
{
  private List<ReportFormat> mFormats = null;

  public void addFormatStructure(ReportFormat reportFormat)
  {
    if (mFormats == null) {
      mFormats = new ArrayList();
    }
    mFormats.add(reportFormat);
  }

  public List<ReportFormat> getFormats()
  {
    return mFormats;
  }

  public void updateCellFormatForItem(ReportCellFormat cf, ReportFormat rf, boolean isWorkSheet)
  {
    for (int i = 0; i < mFormats.size(); i++) {
      ReportFormat lrf = (ReportFormat)mFormats.get(i);
      if ((!isWorkSheet) && (lrf.getCriteria() != null) && (lrf.getCriteria().equals(rf.getCriteria())) && (lrf.getPurpose().equals(rf.getPurpose())))
      {
        lrf.setCellFormat(cf);
      }
      else if ((isWorkSheet) && (rf.getCriteria() == null) && (lrf.getCriteria() == null) && (lrf.getPurpose().equals(rf.getPurpose())))
        lrf.setCellFormat(cf);
    }
  }

  public void updateCellFormatFromScale(int dps)
  {
    for (int i = 0; i < mFormats.size(); i++) {
      ReportFormat lrf = (ReportFormat)mFormats.get(i);
      if (lrf.getCriteria() == null) {
        lrf.getCellFormat().setDecimalPlaces(dps);
        break;
      }
    }
  }

  public ReportFormat getDisplayStructure(int index)
  {
    return (ReportFormat)mFormats.get(index);
  }

  public void writeXml(Writer out) throws IOException
  {
    out.write("<format>");
    Iterator iter = mFormats.iterator();
    while (iter.hasNext())
      ((XMLWritable)iter.next()).writeXml(out);
    out.write("</format>");
  }
}