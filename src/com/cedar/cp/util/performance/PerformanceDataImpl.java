// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.performance;

import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PerformanceDataImpl implements PerformanceData {

   private static PerformanceDataImpl sImpl = new PerformanceDataImpl();
   private Map mPerformanceTypes = new HashMap();


   public static PerformanceDataImpl getInstance() {
      return sImpl;
   }

   public Collection getPerformanceTypes() {
      return Collections.unmodifiableCollection(this.mPerformanceTypes.values());
   }

   public PerformanceType getPerformanceType(String type) {
      return (PerformanceType)this.mPerformanceTypes.get(type);
   }

   public PerformanceDatum getPerformanceDatum(String id) {
      Iterator iter = this.mPerformanceTypes.values().iterator();

      while(iter.hasNext()) {
         PerformanceType perfType = (PerformanceType)iter.next();
         if(perfType != null) {
            PerformanceDatum datum = perfType.getPerformanceDatum(id);
            if(datum != null) {
               return datum;
            }
         }
      }

      return null;
   }

   public void registerPerformanceType(String type, PerformanceType p) {
      this.mPerformanceTypes.put(type, p);
   }

}
