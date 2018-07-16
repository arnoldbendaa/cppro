// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmMap;
import com.cedar.cp.api.model.amm.AmmModelMap;
import com.cedar.cp.dto.model.amm.AmmModelMapImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmmMapImpl implements AmmMap, Serializable {

   private List<AmmModelMapImpl> mChildren = new ArrayList();


   public AmmModelMapImpl findModel(Integer id) {
      AmmModelMapImpl ret = null;
      Iterator i$ = this.mChildren.iterator();

      do {
         if(!i$.hasNext()) {
            return null;
         }

         AmmModelMapImpl x = (AmmModelMapImpl)i$.next();
         ret = this.findModel(id, x);
      } while(ret == null);

      return ret;
   }

   private AmmModelMapImpl findModel(Integer id, AmmModelMapImpl mm) {
      if(mm.getId().equals(id)) {
         return mm;
      } else {
         AmmModelMapImpl ret = null;
         Iterator i$ = mm.getModelMaps().iterator();

         do {
            if(!i$.hasNext()) {
               return null;
            }

            AmmModelMap x = (AmmModelMap)i$.next();
            ret = this.findModel(id, (AmmModelMapImpl)x);
         } while(ret == null);

         return ret;
      }
   }

   public void addModelMap(AmmModelMapImpl mm) {
      this.mChildren.add(mm);
   }

   public void addModelMap(AmmModelMapImpl mm, Integer parentId) {
      AmmModelMapImpl pm = this.findModel(parentId);
      if(pm != null) {
         pm.addModelMap(mm);
      }

   }

   public AmmModelMapImpl findRootModel(Integer modelId) {
      Iterator i$ = this.mChildren.iterator();

      AmmModelMapImpl x;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         x = (AmmModelMapImpl)i$.next();
      } while(!x.getId().equals(modelId) || x.getParentId() != null);

      return x;
   }

   public void removeRootModel(Integer modelId) {
      for(int i = 0; i < this.mChildren.size(); ++i) {
         AmmModelMapImpl x = (AmmModelMapImpl)this.mChildren.get(i);
         if(x.getId().equals(modelId) && x.getParentId() == null) {
            this.mChildren.remove(i);
            break;
         }
      }

   }

   public void print() {
      Iterator i$ = this.mChildren.iterator();

      while(i$.hasNext()) {
         AmmModelMapImpl x = (AmmModelMapImpl)i$.next();
         x.print();
      }

   }

   public List<AmmModelMap> getModelMaps() {
      ArrayList ret = new ArrayList();
      Iterator i$ = this.mChildren.iterator();

      while(i$.hasNext()) {
         AmmModelMapImpl x = (AmmModelMapImpl)i$.next();
         ret.add(x);
      }

      return ret;
   }
}
