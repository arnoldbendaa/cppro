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

public class AllFinanceXmlFormsForModelELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"XmlForm", "XmlFormUserLink", "Model", "FinanceCube", "DataEntryProfile"};
   private transient XmlFormRef mXmlFormEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient FinanceCubeRef mFinanceCubeEntityRef;
   private transient String mDescription;
   private transient String mDataType;
   private transient boolean mDefaultForm;

   public AllFinanceXmlFormsForModelELO() {
      super(new String[]{"XmlForm", "Model", "FinanceCube", "Description", "DataType", "DefaultForm"});
   }

   public void add(XmlFormRef eRefXmlForm, ModelRef eRefModel, FinanceCubeRef eRefFinanceCube, String description, String dataType) {
      ArrayList l = new ArrayList();
      l.add(eRefXmlForm);
      l.add(eRefModel);
      l.add(eRefFinanceCube);
      l.add(description);
      l.add(dataType);
      l.add(false);
      this.mCollection.add(l);
   }
   
   public void add(XmlFormRef eRefXmlForm, ModelRef eRefModel, FinanceCubeRef eRefFinanceCube, String description, String dataType, boolean defaultForm) {
       ArrayList l = new ArrayList();
       l.add(eRefXmlForm);
       l.add(eRefModel);
       l.add(eRefFinanceCube);
       l.add(description);
       l.add(dataType);
       l.add(defaultForm);
       this.mCollection.add(l);
    }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      int index = 0;
      this.mXmlFormEntityRef = (XmlFormRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(index++);
      this.mFinanceCubeEntityRef = (FinanceCubeRef)l.get(index++);
      this.mDescription = (String)l.get(index++);
      this.setDataType((String) l.get(index++));
      this.mDefaultForm = (Boolean) l.get(index++);
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

	public String getDataType() {
		return mDataType;
	}
	
	public void setDataType(String mDataType) {
		this.mDataType = mDataType;
	}

    public boolean isDefaultForm() {
        return mDefaultForm;
    }

    public void setDefaultForm(boolean mDefaultForm) {
        this.mDefaultForm = mDefaultForm;
    }

}
