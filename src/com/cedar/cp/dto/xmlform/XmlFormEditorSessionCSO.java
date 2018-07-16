// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.dto.xmlform.XmlFormImpl;
import java.io.Serializable;

public class XmlFormEditorSessionCSO implements Serializable {

   private int mUserId;
   private XmlFormImpl mEditorData;


   public XmlFormEditorSessionCSO(int userId, XmlFormImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public XmlFormImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
