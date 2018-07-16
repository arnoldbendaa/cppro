/*     */ package com.cedar.cp.dto.datatype;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataTypeRelCK extends DataTypeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected DataTypeRelPK mDataTypeRelPK;
/*     */ 
/*     */   public DataTypeRelCK(DataTypePK paramDataTypePK, DataTypeRelPK paramDataTypeRelPK)
/*     */   {
/*  29 */     super(paramDataTypePK);
/*     */ 
/*  32 */     this.mDataTypeRelPK = paramDataTypeRelPK;
/*     */   }
/*     */ 
/*     */   public DataTypeRelPK getDataTypeRelPK()
/*     */   {
/*  40 */     return this.mDataTypeRelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mDataTypeRelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mDataTypeRelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof DataTypeRelPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof DataTypeRelCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     DataTypeRelCK other = (DataTypeRelCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mDataTypePK.equals(other.mDataTypePK));
/*  75 */     eq = (eq) && (this.mDataTypeRelPK.equals(other.mDataTypeRelPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mDataTypeRelPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("DataTypeRelCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mDataTypeRelPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DataTypeCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("DataTypeRelCK", token[(i++)]);
/* 111 */     checkExpected("DataTypePK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("DataTypeRelPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new DataTypeRelCK(DataTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), DataTypeRelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.datatype.DataTypeRelCK
 * JD-Core Version:    0.6.0
 */