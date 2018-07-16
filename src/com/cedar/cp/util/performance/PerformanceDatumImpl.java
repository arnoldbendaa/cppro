// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.performance;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PerformanceDatumImpl implements PerformanceDatum {

   private transient Log mLog = new Log(this.getClass());
   private long mStartedMillis = System.currentTimeMillis();
   private PerformanceType mType;
   private String mId;
   private Date mWhenInitiated;
   private String mWhoInitiated;
   private List mDatumPoints;
   private double mElapsedTime = -1.0D;
   private Map mAdditionalInformation = new TreeMap();


   public PerformanceDatumImpl(PerformanceType type, String id, Date when, String who) {
      this.mType = type;
      this.mId = id;
      this.mWhenInitiated = when;
      this.mWhoInitiated = who;
      this.mDatumPoints = new ArrayList();
      int count = this.mType.getPerformanceDatumPointNames().size();

      while(count-- > 0) {
         this.mDatumPoints.add(new Integer(-1));
      }

   }

   public PerformanceType getType() {
      return this.mType;
   }

   public String getId() {
      return this.mId;
   }

   public Date getWhenInitiated() {
      return this.mWhenInitiated;
   }

   public String getWhoInitiated() {
      return this.mWhoInitiated;
   }

   public List getAllDatumPoints() {
      return this.mDatumPoints;
   }

   public Map getAdditionalProperties() {
      return this.mAdditionalInformation;
   }

   public void completed() {
      this.mElapsedTime = (double)(System.currentTimeMillis() - this.mWhenInitiated.getTime()) / 1000.0D;
      if(this.mLog.isInfoEnabled()) {
         StringBuffer msg = new StringBuffer("Performance for " + this.mType.getType() + "[");
         msg.append("ElapsedSeconds=" + this.mElapsedTime);
         msg.append(",WhenInitiated=" + this.mWhenInitiated);
         msg.append(",WhoInitiated=" + this.mWhoInitiated);
         List names = this.mType.getPerformanceDatumPointNames();

         for(int i = 0; i < names.size(); ++i) {
            msg.append("," + names.get(i));
            msg.append("=" + this.mDatumPoints.get(i));
         }

         msg.append("]");
         this.mLog.info("completed", msg.toString());
      }

   }

   public double getElapsedSeconds() {
      return this.mElapsedTime;
   }

   public void setDatumPoint(String id) {
      long elapsedTime = System.currentTimeMillis() - this.mStartedMillis;
      List names = this.mType.getPerformanceDatumPointNames();
      int index = names.indexOf(id);
      if(index < 0) {
         throw new IllegalArgumentException("Unknown datum point " + id);
      } else {
         this.mDatumPoints.set(index, new Double((double)elapsedTime / 1000.0D));
         this.mStartedMillis = System.currentTimeMillis();
      }
   }

   public void setAdditonalInformation(Object key, Object value) {
      this.mAdditionalInformation.put(key, value);
   }
}
