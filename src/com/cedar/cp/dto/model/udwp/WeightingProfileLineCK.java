/*     */ package com.cedar.cp.dto.model.udwp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class WeightingProfileLineCK extends WeightingProfileCK
/*     */   implements Serializable
/*     */ {
/*     */   protected WeightingProfileLinePK mWeightingProfileLinePK;
/*     */ 
/*     */   public WeightingProfileLineCK(ModelPK paramModelPK, WeightingProfilePK paramWeightingProfilePK, WeightingProfileLinePK paramWeightingProfileLinePK)
/*     */   {
/*  32 */     super(paramModelPK, paramWeightingProfilePK);
/*     */ 
/*  36 */     this.mWeightingProfileLinePK = paramWeightingProfileLinePK;
/*     */   }
/*     */ 
/*     */   public WeightingProfileLinePK getWeightingProfileLinePK()
/*     */   {
/*  44 */     return this.mWeightingProfileLinePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mWeightingProfileLinePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mWeightingProfileLinePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof WeightingProfileLinePK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof WeightingProfileLineCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     WeightingProfileLineCK other = (WeightingProfileLineCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mWeightingProfilePK.equals(other.mWeightingProfilePK));
/*  80 */     eq = (eq) && (this.mWeightingProfileLinePK.equals(other.mWeightingProfileLinePK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mWeightingProfileLinePK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("WeightingProfileLineCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mWeightingProfileLinePK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("WeightingProfileLineCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("WeightingProfilePK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("WeightingProfileLinePK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new WeightingProfileLineCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), WeightingProfilePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), WeightingProfileLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.udwp.WeightingProfileLineCK
 * JD-Core Version:    0.6.0
 */