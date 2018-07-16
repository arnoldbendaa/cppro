// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AvailableStructureElementELO extends AbstractELO implements Serializable {

   private transient Integer mLevel;
   private transient Object mKey;
   private transient Integer mId;
   private transient String mVisId;
   private transient String mLabel;
   private transient String mCredit;
   private transient String mDisabled;
   private transient String mNotPlannable;
   private transient String mLeaf;
   private transient Integer mPosition;


   public AvailableStructureElementELO() {
      super(new String[]{"Level", "Key", "Id", "VisId", "Label", "Credit", "Disabled", "NotPlannable", "Leaf", "Position"});
   }

   public void add(Integer level, Object key, Integer id, String visId, String label, String credit, String disabled, String notPlannable, String leaf, Integer position) {
      ArrayList l = new ArrayList();
      l.add(level);
      l.add(key);
      l.add(id);
      l.add(visId);
      l.add(label);
      l.add(credit);
      l.add(disabled);
      l.add(notPlannable);
      l.add(leaf);
      l.add(position);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mLevel = (Integer)l.get(index);
      this.mKey = l.get(var4++);
      this.mId = (Integer)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mLabel = (String)l.get(var4++);
      this.mCredit = (String)l.get(var4++);
      this.mDisabled = (String)l.get(var4++);
      this.mNotPlannable = (String)l.get(var4++);
      this.mLeaf = (String)l.get(var4++);
      this.mPosition = (Integer)l.get(var4++);
   }

   public Integer getLevel() {
      return this.mLevel;
   }

   public Object getKey() {
      return this.mKey;
   }

   public Integer getId() {
      return this.mId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getModelId() {
      return this.mLabel;
   }

   public String getCredit() {
      return this.mCredit;
   }

   public String getDisabled() {
      return this.mDisabled;
   }

   public String getNotPlannable() {
      return this.mNotPlannable;
   }

   public String getLeaf() {
      return this.mLeaf;
   }

   public Integer getPosition() {
      return this.mPosition;
   }
}
