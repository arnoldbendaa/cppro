// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllFinXmlFormsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "FinanceCube", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient int mType;
   private transient String mDescription;
   private transient int mFinanceCubeId;
   private transient String mVisId;
   private transient Timestamp mUpdatedTime;


   public AllFinXmlFormsELO() {
      super(new String[]{"XmlForm", "FinanceCube", "Type", "Description", "FinanceCubeId", "VisId", "UpdatedTime"});
   }

   public void add(XmlFormRef eRefXmlForm, FinanceCubeRef eRefFinanceCube, int col1, String col2, int col3, String col4, Timestamp col5) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(eRefFinanceCube);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
      l.add(col4);
      l.add(col5);
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
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mType = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
      this.mUpdatedTime = (Timestamp)l.get(var4++);
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public int getType() {
      return this.mType;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public String getVisId() {
      return this.mVisId;
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
