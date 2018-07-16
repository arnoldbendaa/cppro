/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmDimensionElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAmmDimensionElementId;
/*     */ 
/*     */   public AmmDimensionElementPK(int newAmmDimensionElementId)
/*     */   {
/*  23 */     this.mAmmDimensionElementId = newAmmDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int getAmmDimensionElementId()
/*     */   {
/*  32 */     return this.mAmmDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAmmDimensionElementId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AmmDimensionElementPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AmmDimensionElementCK)) {
/*  56 */       other = ((AmmDimensionElementCK)obj).getAmmDimensionElementPK();
/*     */     }
/*  58 */     else if ((obj instanceof AmmDimensionElementPK))
/*  59 */       other = (AmmDimensionElementPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAmmDimensionElementId == other.mAmmDimensionElementId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AmmDimensionElementId=");
/*  77 */     sb.append(this.mAmmDimensionElementId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAmmDimensionElementId);
/*  89 */     return "AmmDimensionElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AmmDimensionElementPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AmmDimensionElementPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AmmDimensionElementPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAmmDimensionElementId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AmmDimensionElementPK(pAmmDimensionElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmDimensionElementPK
 * JD-Core Version:    0.6.0
 */