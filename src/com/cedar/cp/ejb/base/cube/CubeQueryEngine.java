// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.ejb.base.cube.CubeQueryEngine$1;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CubeQueryEngine extends AbstractDAO {

   private static String IS_CARRYING_BALANCE_SQL = "select count(*) from {dft-table-name} where rownum < 2 and {column} = ?";
   private int[] mDimensionIds;
   private int mNumDimensions;
   private int mFinanceCubeId;


   public CubeQueryEngine() {}

   public CubeQueryEngine(Connection connection) {
      super(connection);
   }

   public CubeQueryEngine(DataSource ds) {
      super(ds);
   }

   public String getEntityName() {
      return "CubeQueryEngine";
   }

   private String getIsCarryingBalanceSQL(String columnName) {
      CubeQueryEngine$1 tp = new CubeQueryEngine$1(this, IS_CARRYING_BALANCE_SQL, columnName);
      return tp.parse();
   }

   public boolean isCarryingBalancesForElement(int dimensionId, int dimensionElementId) {
      PreparedStatement ps = null;
      ResultSet rs = null;

      boolean e;
      try {
         ps = this.getConnection().prepareStatement(this.getIsCarryingBalanceSQL(this.getColumnName(dimensionId)));
         rs = null;
         ps.setInt(1, dimensionElementId);
         rs = ps.executeQuery();
         if(rs.next()) {
            e = rs.getInt(1) > 0;
            return e;
         }

         e = false;
      } catch (SQLException var9) {
         throw new RuntimeException("Failed to query balances for cube:" + var9.getMessage(), var9);
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }

      return e;
   }

   private String getColumnName(int dimensionId) {
      int[] dims = this.getDimensionIds();

      for(int i = 0; i < dims.length; ++i) {
         if(dims[i] == dimensionId) {
            return "dim" + i;
         }
      }

      throw new IllegalStateException("Unable to locate dimensionId in dimension list");
   }

   public int getNumDimensions() {
      return this.mNumDimensions;
   }

   public void setNumDimensions(int numDimensions) {
      this.mNumDimensions = numDimensions;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   private int[] getDimensionIds() {
      if(this.mDimensionIds == null) {
         ModelDAO modelDAO = new ModelDAO();
         FinanceCubePK fcPK = new FinanceCubePK(this.mFinanceCubeId);
         FinanceCubeCK fcCK = modelDAO.getFinanceCubeCK(fcPK);
         ModelDimensionsELO dims = modelDAO.getModelDimensions(fcCK.getModelPK().getModelId());
         this.mDimensionIds = new int[dims.getNumRows()];
         dims.reset();

         for(int row = 0; dims.hasNext(); this.mDimensionIds[row++] = ((DimensionRefImpl)dims.getDimensionEntityRef()).getDimensionPK().getDimensionId()) {
            dims.next();
         }
      }

      return this.mDimensionIds;
   }

   // $FF: synthetic method
   static int accessMethod000(CubeQueryEngine x0) {
      return x0.mFinanceCubeId;
   }

}
