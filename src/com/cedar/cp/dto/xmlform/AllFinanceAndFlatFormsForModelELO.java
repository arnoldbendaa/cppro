// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFinanceAndFlatFormsForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "Model", "FinanceCube", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient String mDescription;


   public AllFinanceAndFlatFormsForModelELO() {
      super(new String[]{"XmlForm", "Model", "FinanceCube", "Description"});
   }

   public void add(XmlFormRef eRefXmlForm, ModelRef eRefModel, FinanceCubeRef eRefFinanceCube, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(eRefModel);
      l.add(eRefFinanceCube);
      l.add(col1);
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
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public FinanceCubeRef getFinanceCubeEntityRef() {
      return this.mFinanceCubeEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
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
