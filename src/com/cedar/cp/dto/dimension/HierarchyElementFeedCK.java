/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class HierarchyElementFeedCK extends HierarchyElementCK
/*     */   implements Serializable
/*     */ {
/*     */   protected HierarchyElementFeedPK mHierarchyElementFeedPK;
/*     */ 
/*     */   public HierarchyElementFeedCK(DimensionPK paramDimensionPK, HierarchyPK paramHierarchyPK, HierarchyElementPK paramHierarchyElementPK, HierarchyElementFeedPK paramHierarchyElementFeedPK)
/*     */   {
/*  33 */     super(paramDimensionPK, paramHierarchyPK, paramHierarchyElementPK);
/*     */ 
/*  38 */     this.mHierarchyElementFeedPK = paramHierarchyElementFeedPK;
/*     */   }
/*     */ 
/*     */   public HierarchyElementFeedPK getHierarchyElementFeedPK()
/*     */   {
/*  46 */     return this.mHierarchyElementFeedPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  54 */     return this.mHierarchyElementFeedPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  62 */     return this.mHierarchyElementFeedPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  71 */     if ((obj instanceof HierarchyElementFeedPK)) {
/*  72 */       return obj.equals(this);
/*     */     }
/*  74 */     if (!(obj instanceof HierarchyElementFeedCK)) {
/*  75 */       return false;
/*     */     }
/*  77 */     HierarchyElementFeedCK other = (HierarchyElementFeedCK)obj;
/*  78 */     boolean eq = true;
/*     */ 
/*  80 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*  81 */     eq = (eq) && (this.mHierarchyPK.equals(other.mHierarchyPK));
/*  82 */     eq = (eq) && (this.mHierarchyElementPK.equals(other.mHierarchyElementPK));
/*  83 */     eq = (eq) && (this.mHierarchyElementFeedPK.equals(other.mHierarchyElementFeedPK));
/*     */ 
/*  85 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     sb.append(super.toString());
/*  95 */     sb.append("[");
/*  96 */     sb.append(this.mHierarchyElementFeedPK);
/*  97 */     sb.append("]");
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 106 */     StringBuffer sb = new StringBuffer();
/* 107 */     sb.append("HierarchyElementFeedCK|");
/* 108 */     sb.append(super.getPK().toTokens());
/* 109 */     sb.append('|');
/* 110 */     sb.append(this.mHierarchyElementFeedPK.toTokens());
/* 111 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/* 116 */     String[] token = extKey.split("[|]");
/* 117 */     int i = 0;
/* 118 */     checkExpected("HierarchyElementFeedCK", token[(i++)]);
/* 119 */     checkExpected("DimensionPK", token[(i++)]);
/* 120 */     i++;
/* 121 */     checkExpected("HierarchyPK", token[(i++)]);
/* 122 */     i++;
/* 123 */     checkExpected("HierarchyElementPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("HierarchyElementFeedPK", token[(i++)]);
/* 126 */     i = 1;
/* 127 */     return new HierarchyElementFeedCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), HierarchyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), HierarchyElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), HierarchyElementFeedPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 137 */     if (!expected.equals(found))
/* 138 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.HierarchyElementFeedCK
 * JD-Core Version:    0.6.0
 */