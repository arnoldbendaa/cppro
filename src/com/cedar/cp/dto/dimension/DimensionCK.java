/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DimensionCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected DimensionPK mDimensionPK;
/*     */ 
/*     */   public DimensionCK(DimensionPK paramDimensionPK)
/*     */   {
/*  26 */     this.mDimensionPK = paramDimensionPK;
/*     */   }
/*     */ 
/*     */   public DimensionPK getDimensionPK()
/*     */   {
/*  34 */     return this.mDimensionPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mDimensionPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mDimensionPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof DimensionPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof DimensionCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     DimensionCK other = (DimensionCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mDimensionPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("DimensionCK|");
/*  92 */     sb.append(this.mDimensionPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("DimensionCK", token[(i++)]);
/* 101 */     checkExpected("DimensionPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new DimensionCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.DimensionCK
 * JD-Core Version:    0.6.0
 */