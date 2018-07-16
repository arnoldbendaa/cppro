// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AggregatedModelTaskRequest extends AbstractTaskRequest {

   private List<AmmDataTypeMap> mRefreshList;


   public AggregatedModelTaskRequest(List<AmmDataTypeMap> refreshList) {
      this.mRefreshList = refreshList;
   }

   public List<AmmDataTypeMap> getRequestList() {
      return this.mRefreshList;
   }

   public boolean isRefreshRequested(AmmDataTypeMap dtMap) {
      if(this.mRefreshList == null) {
         return false;
      } else {
         Iterator i$ = this.mRefreshList.iterator();

         AmmDataTypeMap d;
         do {
            if(!i$.hasNext()) {
               return false;
            }

            d = (AmmDataTypeMap)i$.next();
         } while(!d.getAmmDataTypeId().equals(dtMap.getAmmDataTypeId()));

         return true;
      }
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.AggregatedModelTask";
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("create work list");
      return l;
   }
}
