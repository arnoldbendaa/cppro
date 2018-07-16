/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DimensionPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mDimensionId;
/*     */ 
/*     */   public DimensionPK(int newDimensionId)
/*     */   {
/*  23 */     this.mDimensionId = newDimensionId;
/*     */   }
/*     */ 
/*     */   public int getDimensionId()
/*     */   {
/*  32 */     return this.mDimensionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mDimensionId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     DimensionPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof DimensionCK)) {
/*  56 */       other = ((DimensionCK)obj).getDimensionPK();
/*     */     }
/*  58 */     else if ((obj instanceof DimensionPK))
/*  59 */       other = (DimensionPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mDimensionId == other.mDimensionId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" DimensionId=");
/*  77 */     sb.append(this.mDimensionId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mDimensionId);
/*  89 */     return "DimensionPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DimensionPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("DimensionPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DimensionPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pDimensionId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new DimensionPK(pDimensionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.DimensionPK
 * JD-Core Version:    0.6.0
 */