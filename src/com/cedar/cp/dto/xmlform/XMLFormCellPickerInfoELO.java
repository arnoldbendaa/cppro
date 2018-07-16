// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XMLFormCellPickerInfoELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "FinanceCube", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient int mModelId;
   private transient int mFinanceCubeId;


   public XMLFormCellPickerInfoELO() {
      super(new String[]{"XmlForm", "FinanceCube", "ModelId", "FinanceCubeId"});
   }

   public void add(XmlFormRef eRefXmlForm, FinanceCubeRef eRefFinanceCube, int col1, int col2) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(eRefFinanceCube);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
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
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
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
