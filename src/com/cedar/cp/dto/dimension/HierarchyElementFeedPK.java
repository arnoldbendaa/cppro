/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class HierarchyElementFeedPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mHierarchyElementId;
/*     */   int mDimensionElementId;
/*     */ 
/*     */   public HierarchyElementFeedPK(int newHierarchyElementId, int newDimensionElementId)
/*     */   {
/*  24 */     this.mHierarchyElementId = newHierarchyElementId;
/*  25 */     this.mDimensionElementId = newDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int getHierarchyElementId()
/*     */   {
/*  34 */     return this.mHierarchyElementId;
/*     */   }
/*     */ 
/*     */   public int getDimensionElementId()
/*     */   {
/*  41 */     return this.mDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mHierarchyElementId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mDimensionElementId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     HierarchyElementFeedPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof HierarchyElementFeedCK)) {
/*  66 */       other = ((HierarchyElementFeedCK)obj).getHierarchyElementFeedPK();
/*     */     }
/*  68 */     else if ((obj instanceof HierarchyElementFeedPK))
/*  69 */       other = (HierarchyElementFeedPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mHierarchyElementId == other.mHierarchyElementId);
/*  76 */     eq = (eq) && (this.mDimensionElementId == other.mDimensionElementId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" HierarchyElementId=");
/*  88 */     sb.append(this.mHierarchyElementId);
/*  89 */     sb.append(",DimensionElementId=");
/*  90 */     sb.append(this.mDimensionElementId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mHierarchyElementId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mDimensionElementId);
/* 104 */     return "HierarchyElementFeedPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static HierarchyElementFeedPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("HierarchyElementFeedPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'HierarchyElementFeedPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pHierarchyElementId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pDimensionElementId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new HierarchyElementFeedPK(pHierarchyElementId, pDimensionElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.HierarchyElementFeedPK
 * JD-Core Version:    0.6.0
 */