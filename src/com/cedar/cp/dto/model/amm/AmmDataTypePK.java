/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmDataTypePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAmmDataTypeId;
/*     */ 
/*     */   public AmmDataTypePK(int newAmmDataTypeId)
/*     */   {
/*  23 */     this.mAmmDataTypeId = newAmmDataTypeId;
/*     */   }
/*     */ 
/*     */   public int getAmmDataTypeId()
/*     */   {
/*  32 */     return this.mAmmDataTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAmmDataTypeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AmmDataTypePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AmmDataTypeCK)) {
/*  56 */       other = ((AmmDataTypeCK)obj).getAmmDataTypePK();
/*     */     }
/*  58 */     else if ((obj instanceof AmmDataTypePK))
/*  59 */       other = (AmmDataTypePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAmmDataTypeId == other.mAmmDataTypeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AmmDataTypeId=");
/*  77 */     sb.append(this.mAmmDataTypeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAmmDataTypeId);
/*  89 */     return "AmmDataTypePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AmmDataTypePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AmmDataTypePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AmmDataTypePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAmmDataTypeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AmmDataTypePK(pAmmDataTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmDataTypePK
 * JD-Core Version:    0.6.0
 */