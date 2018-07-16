/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class HierarchyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mHierarchyId;
/*     */ 
/*     */   public HierarchyPK(int newHierarchyId)
/*     */   {
/*  23 */     this.mHierarchyId = newHierarchyId;
/*     */   }
/*     */ 
/*     */   public int getHierarchyId()
/*     */   {
/*  32 */     return this.mHierarchyId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mHierarchyId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     HierarchyPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof HierarchyCK)) {
/*  56 */       other = ((HierarchyCK)obj).getHierarchyPK();
/*     */     }
/*  58 */     else if ((obj instanceof HierarchyPK))
/*  59 */       other = (HierarchyPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mHierarchyId == other.mHierarchyId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" HierarchyId=");
/*  77 */     sb.append(this.mHierarchyId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mHierarchyId);
/*  89 */     return "HierarchyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static HierarchyPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("HierarchyPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'HierarchyPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pHierarchyId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new HierarchyPK(pHierarchyId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.HierarchyPK
 * JD-Core Version:    0.6.0
 */