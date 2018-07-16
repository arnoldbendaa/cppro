// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.DimensionImportFormat;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.impl.dimension.DimensionImportErrorImpl;
import com.cedar.cp.impl.dimension.DimensionsProcessImpl;
import com.cedar.cp.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DimensionImportFormatImpl implements DimensionImportFormat {

   private List mErrors = new ArrayList();
   private DimensionsProcessImpl mProcess;
   public DimensionImpl mDimension;
   private transient Log mLog = new Log(this.getClass());


   public DimensionImportFormatImpl(DimensionsProcessImpl process) {
      this.mProcess = process;
      this.mDimension = new DimensionImpl((Object)null);
   }

   private String getFileType(List record) {
      String fileType = (String)record.get(0);
      if(fileType != null && (fileType.equals("L") || fileType.equals("D"))) {
         return fileType;
      } else {
         this.mErrors.add(new DimensionImportErrorImpl(0, 0, 1, -1, fileType));
         return null;
      }
   }

   private void getName(List record) {
      String name = (String)record.get(1);
      if(name != null && name.length() != 0) {
         this.mDimension.setVisId(name);
         this.mLog.debug("Mdimension name = <" + this.mDimension.getVisId() + ">");
      } else {
         this.mErrors.add(new DimensionImportErrorImpl(0, 1, 6, -1, name));
      }
   }

   private void getDimensionType(List record) {
      String dimType = (String)record.get(2);
      if(dimType != null && dimType.length() != 0) {
         this.mDimension.setType(this.getDimensionTypeNum(dimType));
      } else {
         this.mErrors.add(new DimensionImportErrorImpl(0, 2, 7, -1, dimType));
      }
   }

   private boolean doesDimensionExist() throws CPException {
      EntityList coll = this.mProcess.getAllDimensions();
      DimensionRefImpl[] refs = (DimensionRefImpl[])((DimensionRefImpl[])coll.getValues("Dimension"));
      Integer[] types = (Integer[])((Integer[])coll.getValues("Type"));
      this.mLog.debug("My Name = <" + this.mDimension.getVisId() + "> Type = <" + this.mDimension.getType() + ">");
      boolean found = false;

      for(int i = 0; i < refs.length; ++i) {
         String name = refs[i].getNarrative();
         this.mLog.debug("List Name = <" + name + "> Type = <" + refs[i].getType() + ">");
         if(name.equals(this.mDimension.getVisId()) && refs[i].getType() == this.mDimension.getType()) {
            this.mDimension.setPrimaryKey(refs[i].getDimensionPK());
            found = true;
            break;
         }
      }

      this.mLog.debug("DimensionInportImpl:doesDimensionExist:" + found);
      return found;
   }

   private int getDimensionTypeNum(String dimType) {
      this.mLog.debug("Getting dimension type num:" + dimType);
      return "A".equals(dimType)?1:("B".equals(dimType)?2:("C".equals(dimType)?3:-1));
   }
}
