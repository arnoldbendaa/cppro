// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.datatype.DataTypeRef;
import java.io.Serializable;

public class DataTypeDTO implements Serializable {

   private String mKey;
   private String mNarrative;
   private String mVisId;


   public DataTypeDTO() {}

   public DataTypeDTO(DataTypeRef dtRef) {
      this.mKey = dtRef.getTokenizedKey();
      this.mNarrative = dtRef.getNarrative() + " " + dtRef.getDescription();
      this.mVisId = dtRef.getNarrative();
   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public String getNarrative() {
      return this.mNarrative;
   }

   public void setNarrative(String narrative) {
      this.mNarrative = narrative;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }
}
