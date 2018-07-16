/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmModelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAmmModelId;
/*     */ 
/*     */   public AmmModelPK(int newAmmModelId)
/*     */   {
/*  23 */     this.mAmmModelId = newAmmModelId;
/*     */   }
/*     */ 
/*     */   public int getAmmModelId()
/*     */   {
/*  32 */     return this.mAmmModelId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAmmModelId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AmmModelPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AmmModelCK)) {
/*  56 */       other = ((AmmModelCK)obj).getAmmModelPK();
/*     */     }
/*  58 */     else if ((obj instanceof AmmModelPK))
/*  59 */       other = (AmmModelPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAmmModelId == other.mAmmModelId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AmmModelId=");
/*  77 */     sb.append(this.mAmmModelId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAmmModelId);
/*  89 */     return "AmmModelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AmmModelPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AmmModelPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AmmModelPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAmmModelId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AmmModelPK(pAmmModelId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmModelPK
 * JD-Core Version:    0.6.0
 */