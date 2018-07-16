// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CcDeploymentsForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"CcDeployment", "Model", "CcDeploymentLine", "CcDeploymentEntry", "CcDeploymentDataType", "CcMappingLine", "CcMappingEntry", "XmlForm"};
   private transient CcDeploymentRef mCcDeploymentEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient XmlFormRef mXmlFormEntityRef;
   private transient String mDescription;
   private transient int mXmlFormId;


   public CcDeploymentsForModelELO() {
      super(new String[]{"CcDeployment", "Model", "XmlForm", "Description", "XmlFormId"});
   }

   public void add(CcDeploymentRef eRefCcDeployment, ModelRef eRefModel, XmlFormRef eRefXmlForm, String col1, int col2) {
      ArrayList l = new ArrayList();
      l.add(eRefCcDeployment);
      l.add(eRefModel);
      l.add(eRefXmlForm);
      l.add(col1);
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
      this.mCcDeploymentEntityRef = (CcDeploymentRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mXmlFormEntityRef = (XmlFormRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mXmlFormId = ((Integer)l.get(var4++)).intValue();
   }

   public CcDeploymentRef getCcDeploymentEntityRef() {
      return this.mCcDeploymentEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public XmlFormRef getXmlFormEntityRef() {
      return this.mXmlFormEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlFormId() {
      return this.mXmlFormId;
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
