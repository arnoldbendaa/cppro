/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmDimensionPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAmmDimensionId;
/*     */ 
/*     */   public AmmDimensionPK(int newAmmDimensionId)
/*     */   {
/*  23 */     this.mAmmDimensionId = newAmmDimensionId;
/*     */   }
/*     */ 
/*     */   public int getAmmDimensionId()
/*     */   {
/*  32 */     return this.mAmmDimensionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAmmDimensionId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AmmDimensionPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AmmDimensionCK)) {
/*  56 */       other = ((AmmDimensionCK)obj).getAmmDimensionPK();
/*     */     }
/*  58 */     else if ((obj instanceof AmmDimensionPK))
/*  59 */       other = (AmmDimensionPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAmmDimensionId == other.mAmmDimensionId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AmmDimensionId=");
/*  77 */     sb.append(this.mAmmDimensionId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAmmDimensionId);
/*  89 */     return "AmmDimensionPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AmmDimensionPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AmmDimensionPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AmmDimensionPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAmmDimensionId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AmmDimensionPK(pAmmDimensionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmDimensionPK
 * JD-Core Version:    0.6.0
 */