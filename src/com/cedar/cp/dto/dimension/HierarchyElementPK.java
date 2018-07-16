/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class HierarchyElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mHierarchyElementId;
/*     */ 
/*     */   public HierarchyElementPK(int newHierarchyElementId)
/*     */   {
/*  23 */     this.mHierarchyElementId = newHierarchyElementId;
/*     */   }
/*     */ 
/*     */   public int getHierarchyElementId()
/*     */   {
/*  32 */     return this.mHierarchyElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mHierarchyElementId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     HierarchyElementPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof HierarchyElementCK)) {
/*  56 */       other = ((HierarchyElementCK)obj).getHierarchyElementPK();
/*     */     }
/*  58 */     else if ((obj instanceof HierarchyElementPK))
/*  59 */       other = (HierarchyElementPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mHierarchyElementId == other.mHierarchyElementId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" HierarchyElementId=");
/*  77 */     sb.append(this.mHierarchyElementId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mHierarchyElementId);
/*  89 */     return "HierarchyElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static HierarchyElementPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("HierarchyElementPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'HierarchyElementPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pHierarchyElementId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new HierarchyElementPK(pHierarchyElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.HierarchyElementPK
 * JD-Core Version:    0.6.0
 */