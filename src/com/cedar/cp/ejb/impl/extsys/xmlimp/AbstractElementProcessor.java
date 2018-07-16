// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.base.sax.ElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class AbstractElementProcessor extends AbstractDAO implements ElementProcessor {

   private List<String> mKeys = new ArrayList();
   private ExternalSystemXMLDefLoader mExternalSystemXMLDefLoader;


   protected AbstractElementProcessor(ExternalSystemXMLDefLoader loader) {
      this.mExternalSystemXMLDefLoader = loader;
   }

   public String getEntityName() {
      return this.getClass().getName();
   }

   public ExternalSystemXMLDefLoader getExternalSystemDefLoader() {
      return this.mExternalSystemXMLDefLoader;
   }

   String getAttributeStringValue(Attributes attributes, String fieldName) throws SAXException {
      String value = attributes.getValue(fieldName);
      if(value != null && value.trim().length() != 0) {
         return value;
      } else {
         throw new SAXException("No value or all whitespace value supplied for attribute:" + fieldName);
      }
   }

   int getAttributeIntValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getAttributeStringValue(attributes, fieldName);

      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var5) {
         throw new SAXException("Unable to decode int value for attribute:" + fieldName);
      }
   }

   long getAttributeLongValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getAttributeStringValue(attributes, fieldName);

      try {
         return Long.parseLong(value);
      } catch (NumberFormatException var5) {
         throw new SAXException("Unable to decode long value for attribute:" + fieldName);
      }
   }

   boolean getAttributeBooleanValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getAttributeStringValue(attributes, fieldName);
      return Boolean.parseBoolean(value);
   }

   protected void log(String msg) {
      this.mExternalSystemXMLDefLoader.log(msg);
   }

   public void registerKey(Object ... keys) {
      StringBuffer sb = new StringBuffer();
      Object[] arr$ = keys;
      int len$ = keys.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Object obj = arr$[i$];
         sb.append('[');
         sb.append(obj);
         sb.append(']');
      }

      this.mKeys.add(sb.toString());
   }

   public void clearKeys() {
      this.mKeys.clear();
   }

   public String getKey(int i) {
      return (String)this.mKeys.get(i);
   }

   public void characters(char[] ch, int start, int length) throws SAXException {}

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return 0;
   }
}
