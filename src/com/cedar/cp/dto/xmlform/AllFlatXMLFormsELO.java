// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllFlatXMLFormsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient int mFinanceCubeId;
   private transient int mXmlFormId;
   private transient Timestamp mUpdatedTime;


   public AllFlatXMLFormsELO() {
      super(new String[]{"XmlForm", "FinanceCubeId", "XmlFormId", "UpdatedTime"});
   }

   public void add(XmlFormRef eRefXmlForm, int col1, int col2, Timestamp col3) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mXmlFormEntityRef = (XmlFormRef)l.get(index);
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mXmlFormId = ((Integer)l.get(var4++)).intValue();
      this.mUpdatedTime = (Timestamp)l.get(var4++);
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getXmlFormId() {
      return this.mXmlFormId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
