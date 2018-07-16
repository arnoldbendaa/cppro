/*     */ package com.cedar.cp.dto.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WeightingProfilePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mProfileId;
/*     */ 
/*     */   public WeightingProfilePK(int newProfileId)
/*     */   {
/*  23 */     this.mProfileId = newProfileId;
/*     */   }
/*     */ 
/*     */   public int getProfileId()
/*     */   {
/*  32 */     return this.mProfileId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mProfileId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     WeightingProfilePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof WeightingProfileCK)) {
/*  56 */       other = ((WeightingProfileCK)obj).getWeightingProfilePK();
/*     */     }
/*  58 */     else if ((obj instanceof WeightingProfilePK))
/*  59 */       other = (WeightingProfilePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mProfileId == other.mProfileId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ProfileId=");
/*  77 */     sb.append(this.mProfileId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mProfileId);
/*  89 */     return "WeightingProfilePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static WeightingProfilePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("WeightingProfilePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'WeightingProfilePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pProfileId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new WeightingProfilePK(pProfileId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.udwp.WeightingProfilePK
 * JD-Core Version:    0.6.0
 */