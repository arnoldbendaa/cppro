// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.model.FinanceCubePK;
import java.util.ArrayList;
import java.util.List;

public class GlobalCubeAdminTaskRequest extends AbstractTaskRequest {

   public static final int CREATE_FINANCE_CUBE_VIEWS_AND_TABLES = 0;
   public static final int DELETE_FINANCE_CUBE_VIEWS_AND_TABLES = 1;
   public static final int RECREATE_FINANCE_CUBE_VIEWS = 2;
   public static final int CREATE_CUBE_FORMULA_RUNTIME_TABLES_AND_PACKAGES = 3;
   public static final int DELETE_CUBE_FORMULA_RUNTIME_TABLES_AND_PACKAGES = 4;
   private static String[] sActionText = new String[]{"Create finance cube tables and views", "Delete finance cube tables and views", "Update finance cube views", "Create cube formula runtime packages and tables", "Delete cube formula runtime packages and tables"};
   private int mFinanceCubeId;
   private int mNumDims;
   private int mAction;
   private String mVisId;
   private String mDescription;


   public GlobalCubeAdminTaskRequest(int financeCubeId, String visId, String description, int numDims, int action) {
      this.mFinanceCubeId = financeCubeId;
      this.mNumDims = numDims;
      this.mAction = action;
      this.mVisId = visId;
      this.mDescription = description;
      this.addExclusiveAccess((new FinanceCubePK(financeCubeId)).toString());
   }

   public int getAction() {
      return this.mAction;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getNumDims() {
      return this.mNumDims;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.cubeadmin.GlobalCubeAdminTask";
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Cube Admin - " + sActionText[this.mAction]);
      return l;
   }

}
