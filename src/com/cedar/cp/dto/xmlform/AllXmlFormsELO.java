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

public class AllXmlFormsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient int mType;
   private transient String mDescription;
   private transient int mXmlFormId;
   private transient Timestamp mUpdatedTime;
   private transient FinanceCubeRef financeCube;


   public AllXmlFormsELO() {
      super(new String[]{"XmlForm", "FinanceCube", "Type", "Description","XmlFormId", "UpdatedTime"});
   }

   public void add(XmlFormRef eRefXmlForm, FinanceCubeRef financeCube, int col1, String col2, int col3, Timestamp col4) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(financeCube);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
      l.add(col4);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      this.mXmlFormEntityRef = (XmlFormRef)l.get(index++);
      this.financeCube = (FinanceCubeRef)l.get(index++);
      this.mType = ((Integer)l.get(index++)).intValue();
      this.mDescription = (String)l.get(index++);
      this.mXmlFormId = ((Integer)l.get(index++)).intValue();
      this.mUpdatedTime = (Timestamp)l.get(index++);
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }
   
   public FinanceCubeRef getFinanceCube() {
	      return this.financeCube;
	   }

   public int getType() {
      return this.mType;
   }

   public String getDescription() {
      return this.mDescription;
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
