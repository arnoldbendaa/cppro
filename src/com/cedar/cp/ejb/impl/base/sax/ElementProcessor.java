// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface ElementProcessor {

   void processStartElement(String var1, String var2, String var3, Attributes var4) throws SAXException;

   void characters(char[] var1, int var2, int var3) throws SAXException;

   void processEndElement(String var1, String var2, String var3) throws SAXException;
}
