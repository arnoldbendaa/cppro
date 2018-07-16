/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmFinanceCubePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAmmFinanceCubeId;
/*     */ 
/*     */   public AmmFinanceCubePK(int newAmmFinanceCubeId)
/*     */   {
/*  23 */     this.mAmmFinanceCubeId = newAmmFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int getAmmFinanceCubeId()
/*     */   {
/*  32 */     return this.mAmmFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAmmFinanceCubeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AmmFinanceCubePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AmmFinanceCubeCK)) {
/*  56 */       other = ((AmmFinanceCubeCK)obj).getAmmFinanceCubePK();
/*     */     }
/*  58 */     else if ((obj instanceof AmmFinanceCubePK))
/*  59 */       other = (AmmFinanceCubePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAmmFinanceCubeId == other.mAmmFinanceCubeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AmmFinanceCubeId=");
/*  77 */     sb.append(this.mAmmFinanceCubeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAmmFinanceCubeId);
/*  89 */     return "AmmFinanceCubePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AmmFinanceCubePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AmmFinanceCubePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AmmFinanceCubePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAmmFinanceCubeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AmmFinanceCubePK(pAmmFinanceCubeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmFinanceCubePK
 * JD-Core Version:    0.6.0
 */