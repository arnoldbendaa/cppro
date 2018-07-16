// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllWeightingProfilesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"WeightingProfile", "Model", "WeightingProfileLine", "WeightingDeployment", "WeightingDeploymentLine"};
   private transient WeightingProfileRef mWeightingProfileEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;
   private transient int mProfileType;
   private transient int mStartLevel;
   private transient int mLeafLevel;
   private transient String mVisId;


   public AllWeightingProfilesELO() {
      super(new String[]{"WeightingProfile", "Model", "Description", "ProfileType", "StartLevel", "LeafLevel", "VisId"});
   }

   public void add(WeightingProfileRef eRefWeightingProfile, ModelRef eRefModel, String col1, int col2, int col3, int col4, String col5) {
      ArrayList l = new ArrayList();
      l.add(eRefWeightingProfile);
      l.add(eRefModel);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(new Integer(col4));
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
      this.mWeightingProfileEntityRef = (WeightingProfileRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mProfileType = ((Integer)l.get(var4++)).intValue();
      this.mStartLevel = ((Integer)l.get(var4++)).intValue();
      this.mLeafLevel = ((Integer)l.get(var4++)).intValue();
      this.mVisId = (String)l.get(var4++);
   }

   public WeightingProfileRef getWeightingProfileEntityRef() {
      return this.mWeightingProfileEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getProfileType() {
      return this.mProfileType;
   }

   public int getStartLevel() {
      return this.mStartLevel;
   }

   public int getLeafLevel() {
      return this.mLeafLevel;
   }

   public String getVisId() {
      return this.mVisId;
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
