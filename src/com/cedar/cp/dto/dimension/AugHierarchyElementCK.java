/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AugHierarchyElementCK extends HierarchyCK
/*     */   implements Serializable
/*     */ {
/*     */   protected AugHierarchyElementPK mAugHierarchyElementPK;
/*     */ 
/*     */   public AugHierarchyElementCK(DimensionPK paramDimensionPK, HierarchyPK paramHierarchyPK, AugHierarchyElementPK paramAugHierarchyElementPK)
/*     */   {
/*  31 */     super(paramDimensionPK, paramHierarchyPK);
/*     */ 
/*  35 */     this.mAugHierarchyElementPK = paramAugHierarchyElementPK;
/*     */   }
/*     */ 
/*     */   public AugHierarchyElementPK getAugHierarchyElementPK()
/*     */   {
/*  43 */     return this.mAugHierarchyElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mAugHierarchyElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mAugHierarchyElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof AugHierarchyElementPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof AugHierarchyElementCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     AugHierarchyElementCK other = (AugHierarchyElementCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*  78 */     eq = (eq) && (this.mHierarchyPK.equals(other.mHierarchyPK));
/*  79 */     eq = (eq) && (this.mAugHierarchyElementPK.equals(other.mAugHierarchyElementPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mAugHierarchyElementPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("AugHierarchyElementCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mAugHierarchyElementPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("AugHierarchyElementCK", token[(i++)]);
/* 115 */     checkExpected("DimensionPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("HierarchyPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("AugHierarchyElementPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new AugHierarchyElementCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), HierarchyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AugHierarchyElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.AugHierarchyElementCK
 * JD-Core Version:    0.6.0
 */