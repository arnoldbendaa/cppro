/*     */ package com.cedar.cp.dto.datatype;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataTypeCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected DataTypePK mDataTypePK;
/*     */ 
/*     */   public DataTypeCK(DataTypePK paramDataTypePK)
/*     */   {
/*  26 */     this.mDataTypePK = paramDataTypePK;
/*     */   }
/*     */ 
/*     */   public DataTypePK getDataTypePK()
/*     */   {
/*  34 */     return this.mDataTypePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mDataTypePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mDataTypePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof DataTypePK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof DataTypeCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     DataTypeCK other = (DataTypeCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mDataTypePK.equals(other.mDataTypePK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mDataTypePK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("DataTypeCK|");
/*  92 */     sb.append(this.mDataTypePK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DataTypeCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("DataTypeCK", token[(i++)]);
/* 101 */     checkExpected("DataTypePK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new DataTypeCK(DataTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.datatype.DataTypeCK
 * JD-Core Version:    0.6.0
 */