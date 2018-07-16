/*     */ package com.cedar.cp.dto.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WeightingProfileLinePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mProfileId;
/*     */   int mLineIdx;
/*     */ 
/*     */   public WeightingProfileLinePK(int newProfileId, int newLineIdx)
/*     */   {
/*  24 */     this.mProfileId = newProfileId;
/*  25 */     this.mLineIdx = newLineIdx;
/*     */   }
/*     */ 
/*     */   public int getProfileId()
/*     */   {
/*  34 */     return this.mProfileId;
/*     */   }
/*     */ 
/*     */   public int getLineIdx()
/*     */   {
/*  41 */     return this.mLineIdx;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mProfileId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mLineIdx).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     WeightingProfileLinePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof WeightingProfileLineCK)) {
/*  66 */       other = ((WeightingProfileLineCK)obj).getWeightingProfileLinePK();
/*     */     }
/*  68 */     else if ((obj instanceof WeightingProfileLinePK))
/*  69 */       other = (WeightingProfileLinePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mProfileId == other.mProfileId);
/*  76 */     eq = (eq) && (this.mLineIdx == other.mLineIdx);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ProfileId=");
/*  88 */     sb.append(this.mProfileId);
/*  89 */     sb.append(",LineIdx=");
/*  90 */     sb.append(this.mLineIdx);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mProfileId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mLineIdx);
/* 104 */     return "WeightingProfileLinePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static WeightingProfileLinePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("WeightingProfileLinePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'WeightingProfileLinePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pProfileId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pLineIdx = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new WeightingProfileLinePK(pProfileId, pLineIdx);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.udwp.WeightingProfileLinePK
 * JD-Core Version:    0.6.0
 */