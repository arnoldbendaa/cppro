// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:23
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.common;

import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.virement.VirementRequestCK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.model.virement.VirementRequestRefImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils<E extends Object> {

   public static final int COL_TYPE_INT = 0;
   public static final int COL_TYPE_STRING = 1;
   public static final int COL_TYPE_DATE = 3;
   public static final int COL_TYPE_REF = 4;
   public static final int COL_TYPE_BOOLEAN = 5;
   public static final int COL_TYPE_TIMESTAMP = 6;
   public static final int COL_TYPE_BIGDECIMAL = 7;


   public static String[] queryNames(ColType[] colTypes) {
      String[] colNames = new String[colTypes.length];

      for(int i = 0; i < colTypes.length; ++i) {
         colNames[i] = colTypes[i].mName;
      }

      return colNames;
   }

   public static Object[] extract(ColType[] colTypes, ResultSet rs) throws SQLException {
      Object[] row = new Object[colTypes.length];

      for(int i = 0; i < colTypes.length; ++i) {
         try {
            row[i] = colTypes[i].extract(rs);
         } catch (SQLException var5) {
            throw new IllegalStateException("sql error obtaining column " + colTypes[i].mName, var5);
         }
      }

      return row;
   }

   public static EntityListImpl extractToEntityListImpl(ColType[] colTypes, ResultSet rs) throws SQLException {
      ArrayList rows = new ArrayList();

      while(rs.next()) {
         rows.add(extract(colTypes, rs));
      }

      return new EntityListImpl(queryNames(colTypes), (Object[][])((Object[][])rows.toArray(new Object[0][])));
   }

   public static <T extends Object> List<T> extractToList(ColType colType, ResultSet rs) throws SQLException {
      ArrayList result = new ArrayList();

      while(rs.next()) {
         result.add(colType.extract(rs));
      }

      return result;
   }
   
   
   public static class ColType {

	   protected String mName;
	   protected String mColName;
	   protected int mType;


	   public ColType(String name, String colName, int type) {
	      this.mName = name;
	      this.mColName = colName;
	      this.mType = type;
	   }

	   public ColType(String name, int type) {
	      this.mName = name;
	      this.mColName = name;
	      this.mType = type;
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      switch(this.mType) {
	      case 0:
	         return new Integer(rs.getInt(this.mColName));
	      case 1:
	         return rs.getString(this.mColName);
	      case 2:
	      case 4:
	      default:
	         throw new IllegalStateException("Unexpected ColType:" + this.mType);
	      case 3:
	         return rs.getDate(this.mColName);
	      case 5:
	         return Boolean.valueOf(rs.getString(this.mColName).equalsIgnoreCase("Y"));
	      case 6:
	         return rs.getTimestamp(this.mColName);
	      case 7:
	         return rs.getBigDecimal(this.mColName);
	      }
	   }
	}
   
   public static abstract class RefColType extends ColType {

	   protected String mVisIdColName;


	   protected RefColType(String name, String colName, String visIdColName) {
	      super(name, colName, 4);
	      this.mVisIdColName = visIdColName;
	   }
	}
   
   public static class CubeFormulaRefColType extends RefColType {
	   
	   public CubeFormulaRefColType(String name, String colName, String visIdColName) {
		   super(name, colName, visIdColName);
	   }
	   
	   public Object extract(ResultSet rs) throws SQLException {
		   CubeFormulaPK pk = new CubeFormulaPK(rs.getInt(this.mColName));
		   return new CubeFormulaRefImpl(pk, rs.getString(this.mVisIdColName));
	   }
   }

   public static class EntityRefColType extends RefColType {

	   public EntityRefColType(String name, String colName, String visIdColName) {
	      super(name, colName, visIdColName);
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      return new EntityRefImpl(rs.getString(this.mColName), rs.getString(this.mVisIdColName));
	   }
	}
   
   public static class ExternalEntityRefColType extends RefColType {

	   public ExternalEntityRefColType(String name, String colName, String visIdColName) {
	      super(name, colName, visIdColName);
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      return new EntityRefImpl(rs.getString(this.mColName), rs.getString(this.mColName) + " - " + rs.getString(this.mVisIdColName));
	   }
	}
   
   public static class FinanceCubeRefColType extends RefColType {

	   public FinanceCubeRefColType(String name, String colName, String visIdColName) {
	      super(name, colName, visIdColName);
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      FinanceCubePK fcPK = new FinanceCubePK(rs.getInt(this.mColName));
	      return new FinanceCubeRefImpl(fcPK, rs.getString(this.mVisIdColName));
	   }
	}
   
   public static class ModelRefColType extends RefColType {

	   public ModelRefColType(String name, String colName, String visIdColName) {
	      super(name, colName, visIdColName);
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      ModelPK modelPK = new ModelPK(rs.getInt(this.mColName));
	      return new ModelRefImpl(modelPK, rs.getString(this.mVisIdColName));
	   }
	}
   
   public static class StructureElementRefColType extends RefColType {

	   private String mStructureIdColName;


	   public StructureElementRefColType(String name, String structureColId, String structureElementColId, String visIdColName) {
	      super(name, structureElementColId, visIdColName);
	      this.mStructureIdColName = structureColId;
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      StructureElementPK sePK = new StructureElementPK(rs.getInt(this.mStructureIdColName), rs.getInt(this.mColName));
	      return new StructureElementRefImpl(sePK, rs.getString(this.mVisIdColName));
	   }
	}
   
   public static class UserRefColType extends RefColType {

	   public UserRefColType(String name, String colName, String visIdColName) {
	      super(name, colName, visIdColName);
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      UserPK userPK = new UserPK(rs.getInt(this.mColName));
	      return new UserRefImpl(userPK, rs.getString(this.mVisIdColName));
	   }
	}
   
   public static class VirementRequestRefColType extends RefColType {

	   private String mModelIdColName;


	   public VirementRequestRefColType(String name, String colName, String visIdColName, String modelIdColName) {
	      super(name, colName, visIdColName);
	      this.mModelIdColName = modelIdColName;
	   }

	   public Object extract(ResultSet rs) throws SQLException {
	      VirementRequestCK ck = new VirementRequestCK(new ModelPK(rs.getInt(this.mModelIdColName)), new VirementRequestPK(rs.getInt(this.mColName)));
	      String visId = this.mVisIdColName != null?rs.getString(this.mVisIdColName):null;
	      return new VirementRequestRefImpl(ck, visId);
	   }
	}
}
