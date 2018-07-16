// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.amm;

import com.cedar.cp.api.model.amm.AmmCubeMap;
import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.io.Serializable;
import java.sql.Timestamp;

public class AmmDataTypeMapImpl implements AmmDataTypeMap, Serializable {

   protected transient Log mLog = new Log(this.getClass());
   private Integer mTrgId;
   private String mTrgVisId;
   private String mTrgDescr;
   private Timestamp mTrgLastUpdate;
   private Integer mSrcId;
   private String mSrcVisId;
   private String mSrcDescr;
   private Timestamp mSrcLastUpdate;
   private int mAmmDataTypeId;
   private Timestamp mMappingLastUpdate;
   private Timestamp mFmsMappingLastUpdate;
   private AmmCubeMap mParentCube;


   public AmmDataTypeMapImpl(int trgId, String trgVisId, String trgDescr, Timestamp trgLastUpdate, int srcId, String srcVisId, String srcDescr, Timestamp srcLastUpdate, int ammDataTypeId, Timestamp mappingLastUpdate, Timestamp fmsMappingLastUpdate, AmmCubeMap parentCube) {
      this.mTrgId = Integer.valueOf(trgId);
      this.mTrgVisId = trgVisId;
      this.mTrgDescr = trgDescr;
      this.mTrgLastUpdate = trgLastUpdate;
      this.mSrcId = Integer.valueOf(srcId);
      this.mSrcVisId = srcVisId;
      this.mSrcDescr = srcDescr;
      this.mSrcLastUpdate = srcLastUpdate;
      this.mAmmDataTypeId = ammDataTypeId;
      this.mMappingLastUpdate = mappingLastUpdate;
      this.mFmsMappingLastUpdate = fmsMappingLastUpdate;
      this.mParentCube = parentCube;
   }

   public Integer getAmmDataTypeId() {
      return Integer.valueOf(this.mAmmDataTypeId);
   }

   public Integer getTargetId() {
      return this.mTrgId;
   }

   public String getTargetVisId() {
      return this.mTrgVisId;
   }

   public String getTargetDescr() {
      return this.mTrgDescr;
   }

   public Timestamp getTargetLastUpdate() {
      return this.mTrgLastUpdate;
   }

   public Integer getSourceId() {
      return this.mSrcId;
   }

   public String getSourceVisId() {
      return this.mSrcVisId;
   }

   public String getSourceDescr() {
      return this.mSrcDescr;
   }

   public Timestamp getSourceLastUpdate() {
      return this.mSrcLastUpdate;
   }

   public Timestamp getMappingLastUpdate() {
      return this.mMappingLastUpdate;
   }

   public boolean isTargetRefreshNeeded() {
      return this.getTargetLastUpdate() == null && this.getSourceLastUpdate() == null?false:(this.getTargetLastUpdate() != null && this.getMappingLastUpdate().after(this.getTargetLastUpdate()) && this.getSourceLastUpdate() != null?true:(this.getTargetLastUpdate() != null && this.getSourceLastUpdate() != null?this.getSourceLastUpdate().after(this.getTargetLastUpdate()):this.getSourceLastUpdate() != null));
   }

   public String getTimeDifferenceText() {
      return this.getTargetLastUpdate() == null && this.getSourceLastUpdate() == null?"neither data type has been updated":(this.getTargetLastUpdate() != null && this.getMappingLastUpdate().after(this.getTargetLastUpdate()) && this.getSourceLastUpdate() != null?"mapping changed " + this.getTimeDifferenceText(this.getMappingLastUpdate(), this.getTargetLastUpdate()) + " after target updated":(this.getTargetLastUpdate() != null && this.getSourceLastUpdate() != null?(this.getSourceLastUpdate().after(this.getTargetLastUpdate())?"source changed " + this.getTimeDifferenceText(this.getSourceLastUpdate(), this.getTargetLastUpdate()) + " after target":"target is up to date"):(this.getSourceLastUpdate() != null?"first refresh needed (or mapping produced no transactions)":"source has never been updated")));
   }

   public AmmCubeMap getParentCubeMap() {
      return this.mParentCube;
   }

   private String getTimeDifferenceText(Timestamp date1, Timestamp date2) {
      long laterTime;
      long earlierTime;
      if(date1.after(date2)) {
         laterTime = date1.getTime();
         earlierTime = date2.getTime();
      } else {
         laterTime = date2.getTime();
         earlierTime = date1.getTime();
      }

      String diffStr = Timer.getFormatted(laterTime - earlierTime, 3);
      String[] diffs = diffStr.split(":");
      int hoursDiff = (new Integer(diffs[0])).intValue();
      int minsDiff = (new Integer(diffs[1])).intValue();
      int daysDiff = hoursDiff / 24;
      hoursDiff -= daysDiff * 24;
      String retStr;
      if(daysDiff > 0) {
         retStr = daysDiff + " days, " + hoursDiff + " hours";
      } else if(hoursDiff > 0) {
         retStr = hoursDiff + " hours, " + minsDiff + " mins";
      } else if(minsDiff > 0) {
         retStr = minsDiff + " mins";
      } else {
         retStr = "less than a minute";
      }

      return retStr;
   }

   public String toString() {
      return this.getTargetVisId() + " [" + this.getTargetDescr() + "] <-- " + this.getSourceVisId() + " [" + this.getSourceDescr() + "]";
   }

   public void print() {
      this.mLog.debug("print()", "trgDtId=" + this.getTargetId() + " trgDtVisId=" + this.getTargetVisId() + " upd=" + this.getTargetLastUpdate() + " srcDtId=" + this.getSourceId() + " srcDtVisId=" + this.getSourceVisId() + " upd=" + this.getSourceLastUpdate() + " refreshNeeded=" + this.isTargetRefreshNeeded());
   }
}
