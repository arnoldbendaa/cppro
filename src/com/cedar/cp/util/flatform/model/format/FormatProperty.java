// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.FormatPropertyNames;
import com.cedar.cp.util.xmlform.XMLWritable;

public interface FormatProperty extends FormatPropertyNames, XMLWritable {

   void updateFormat(CellFormat var1);

   String getName();

   boolean isXmlAttribute();
}
