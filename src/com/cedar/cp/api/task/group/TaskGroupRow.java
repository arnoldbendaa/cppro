// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.task.group;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.task.group.TaskGroupRowParam;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskGroupRow implements Serializable, Comparable {

   private Integer mType;
   private Integer mIndex;
   private List<TaskGroupRowParam> mParams;


   public Integer getType() {
      return this.mType;
   }

   public void setType(Integer type) {
      this.mType = type;
   }

   public List<TaskGroupRowParam> getParams() {
      if(this.mParams == null) {
         this.mParams = new ArrayList();
      }

      return this.mParams;
   }

   public void setParams(List<TaskGroupRowParam> params) {
      this.mParams = params;
   }

   public void addParamRow(String key, String param) {
      if(key != null && key.length() != 0 && param != null && param.length() != 0) {
         Iterator i$ = this.getParams().iterator();

         TaskGroupRowParam rowParam;
         do {
            if(!i$.hasNext()) {
               this.getParams().add(new TaskGroupRowParam(key, param));
               return;
            }

            rowParam = (TaskGroupRowParam)i$.next();
         } while(!key.equals(rowParam.getKey()));

         rowParam.setValue(param);
      }
   }

   public void addParamRow(EntityRef ref) {
      if(ref != null) {
         Iterator i$ = this.getParams().iterator();

         TaskGroupRowParam rowParam;
         do {
            if(!i$.hasNext()) {
               this.getParams().add(new TaskGroupRowParam(ref));
               return;
            }

            rowParam = (TaskGroupRowParam)i$.next();
         } while(!"ID".equals(rowParam.getKey()));

         rowParam.setRef(ref);
      }
   }

   public Integer getIndex() {
      return this.mIndex;
   }

   public void setIndex(Integer index) {
      this.mIndex = index;
   }

   public int compareTo(Object o) {
      TaskGroupRow that = (TaskGroupRow)o;
      return this.getIndex().compareTo(that.getIndex());
   }
}
