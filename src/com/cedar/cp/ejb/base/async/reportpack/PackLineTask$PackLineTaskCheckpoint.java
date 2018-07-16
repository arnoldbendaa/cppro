// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.reportpack;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.report.distribution.DistributionDetail;
import com.cedar.cp.api.report.distribution.DistributionDetailUser;
import com.cedar.cp.api.report.distribution.DistributionDetails;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PackLineTask$PackLineTaskCheckpoint extends AbstractTaskCheckpoint {

   private transient int mMaxBatchSize = 1;
   private int mListIndex = -1;
   private transient int mBatchCount = 0;
   private Map<DistributionDetailUser, List<CPFileWrapper>> mGroup;


   public int getListIndex() {
      return this.mListIndex;
   }

   public int getNextIndex() {
      ++this.mBatchCount;
      return ++this.mListIndex;
   }

   public boolean shouldCheckpoint() {
      return this.mBatchCount == this.getMaxBatchSize();
   }

   public int getMaxBatchSize() {
      return this.mMaxBatchSize;
   }

   public void setMaxBatchSize(int maxBatchSize) {
      this.mMaxBatchSize = maxBatchSize;
   }

   public Map<DistributionDetailUser, List<CPFileWrapper>> getGroup() {
      if(this.mGroup == null) {
         this.mGroup = new HashMap();
      }

      return this.mGroup;
   }

   public void setGroup(Map<DistributionDetailUser, List<CPFileWrapper>> group) {
      this.mGroup = group;
   }

   public void addToGroup(DistributionDetails dd, CPFileWrapper rf) {
      Iterator i$ = dd.getDistributionList().iterator();

      while(i$.hasNext()) {
         Object o = i$.next();
         if(o instanceof DistributionDetail) {
            DistributionDetail detail = (DistributionDetail)o;

            for(int i = 0; i < detail.getUsers().size(); ++i) {
               DistributionDetailUser key = detail.getDistributionDetailUser(i);
               if(!this.getGroup().containsKey(key)) {
                  this.getGroup().put(key, new ArrayList());
               }

               List l = (List)this.getGroup().get(key);
               if(!l.contains(rf)) {
                  l.add(rf);
               }
            }
         }
      }

   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add(Integer.valueOf(this.mListIndex).toString());
      return l;
   }
}
