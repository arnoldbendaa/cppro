// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base.sax;

import com.cedar.cp.ejb.impl.base.sax.ElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class AbstractElementProcessor implements ElementProcessor {

   protected String getAttributeStringValue(Attributes attributes, String fieldName) throws SAXException {
      String value = attributes.getValue(fieldName);
      if(value != null && value.trim().length() != 0) {
         return value;
      } else {
         throw new SAXException("No value or all whitespace value supplied for attribute:" + fieldName);
      }
   }

   protected String getOptionalAttributeStringValue(Attributes attributes, String fieldName) {
      return attributes.getValue(fieldName);
   }

   protected int getAttributeIntValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getAttributeStringValue(attributes, fieldName);

      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var5) {
         throw new SAXException("Unable to decode int value for attribute:" + fieldName);
      }
   }

   protected Integer getOptionalAttributeIntValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getOptionalAttributeStringValue(attributes, fieldName);
      if(value == null) {
         return null;
      } else {
         try {
            return Integer.valueOf(Integer.parseInt(value));
         } catch (NumberFormatException var5) {
            throw new SAXException("Unable to decode int value for attribute:" + fieldName);
         }
      }
   }

   protected long getAttributeLongValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getAttributeStringValue(attributes, fieldName);

      try {
         return Long.parseLong(value);
      } catch (NumberFormatException var5) {
         throw new SAXException("Unable to decode long value for attribute:" + fieldName);
      }
   }

   protected boolean getAttributeBooleanValue(Attributes attributes, String fieldName) throws SAXException {
      String value = this.getAttributeStringValue(attributes, fieldName);
      return Boolean.parseBoolean(value);
   }

   public void characters(char[] ch, int start, int length) throws SAXException {}
}
