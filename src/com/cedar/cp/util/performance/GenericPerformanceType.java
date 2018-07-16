// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.performance;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.performance.PerformanceDataImpl;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.performance.PerformanceType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GenericPerformanceType implements PerformanceType {

   private static Log sLog = new Log(GenericPerformanceType.class);
   protected String mType;
   protected List mIdNames;
   protected List mDatums;
   protected String mDescription;


   protected GenericPerformanceType(String type, List idNames, String description) {
      this.mType = type;
      this.mIdNames = idNames;
      this.mDescription = description;
      this.mDatums = new LinkedList();
   }

   public static GenericPerformanceType getInstance(String type, List ids, String description) {
      Object p = PerformanceDataImpl.getInstance().getPerformanceType(type);
      if(p == null) {
         p = new GenericPerformanceType(type, ids, description);
         PerformanceDataImpl.getInstance().registerPerformanceType(type, (PerformanceType)p);
      }

      return (GenericPerformanceType)p;
   }

   public String getType() {
      return this.mType;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public List getPerformanceDatumPointNames() {
      return this.mIdNames;
   }

   public List getPerformanceDatums() {
      return this.mDatums;
   }

   public PerformanceDatum getPerformanceDatum(String id) {
      for(int i = 0; i < this.mDatums.size(); ++i) {
         PerformanceDatum datum = (PerformanceDatum)this.mDatums.get(i);
         if(datum.getId().equals(id)) {
            return datum;
         }
      }

      return null;
   }

   public PerformanceDatumImpl createPerformanceDatum(String who) {
      PerformanceDatumImpl datum = new PerformanceDatumImpl(this, String.valueOf(System.currentTimeMillis()), new Date(), who);
      if(sLog.isDebugEnabled()) {
         this.mDatums.add(datum);
         if(this.mDatums.size() > 10) {
            this.mDatums.remove(0);
         }
      }

      return datum;
   }

}
