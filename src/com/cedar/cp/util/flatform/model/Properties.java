// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Properties extends HashMap<String, String> implements Serializable, XMLWritable {

   public void writeXml(Writer out) throws IOException {
      out.write("<properties>");
      Iterator i$ = this.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         out.write("<entry ");
         XmlUtils.outputAttribute(out, "name", entry.getKey());
         XmlUtils.outputAttribute(out, "value", entry.getValue());
         out.write("/>");
      }

      out.write("</properties>");
   }

   public void addPropEntry(PropEntry property) {
      this.put(property.getName(), property.getValue());
   }
   
   public static class PropEntry {

	   String mName;
	   String mValue;


	   public String getName() {
	      return this.mName;
	   }

	   public void setName(String name) {
	      this.mName = name;
	   }

	   public String getValue() {
	      return this.mValue;
	   }

	   public void setValue(String value) {
	      this.mValue = value;
	   }
	}
}
