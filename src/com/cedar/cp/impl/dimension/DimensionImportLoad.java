// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.DimensionEditorSession;
import com.cedar.cp.api.dimension.DimensionImportFormat;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.impl.dimension.DimensionEditorImpl;
import com.cedar.cp.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DimensionImportLoad implements DimensionImportFormat {

   private List mErrors = new ArrayList();
   private int mRecordNum = -1;
   private String mFileTypeText;
   private DimensionEditorSession mSession;
   private DimensionEditorImpl mEditor;
   private DimensionImpl mDimension;
   private String mDimensionName;
   private String mDimensionType;
   private transient Log mLog = new Log(this.getClass());


   public DimensionImportLoad(DimensionEditorSession session) throws Exception {
      this.mSession = session;
      this.mEditor = (DimensionEditorImpl)session.getDimensionEditor();
   }

   private void doValidate() throws Exception {
      this.mLog.debug("Doing detail validation");
   }
}
