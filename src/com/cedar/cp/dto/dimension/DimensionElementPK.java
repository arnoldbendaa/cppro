/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DimensionElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mDimensionElementId;
/*     */ 
/*     */   public DimensionElementPK(int newDimensionElementId)
/*     */   {
/*  23 */     this.mDimensionElementId = newDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int getDimensionElementId()
/*     */   {
/*  32 */     return this.mDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mDimensionElementId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     DimensionElementPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof DimensionElementCK)) {
/*  56 */       other = ((DimensionElementCK)obj).getDimensionElementPK();
/*     */     }
/*  58 */     else if ((obj instanceof DimensionElementPK))
/*  59 */       other = (DimensionElementPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mDimensionElementId == other.mDimensionElementId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" DimensionElementId=");
/*  77 */     sb.append(this.mDimensionElementId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mDimensionElementId);
/*  89 */     return "DimensionElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DimensionElementPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("DimensionElementPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DimensionElementPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pDimensionElementId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new DimensionElementPK(pDimensionElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.DimensionElementPK
 * JD-Core Version:    0.6.0
 */