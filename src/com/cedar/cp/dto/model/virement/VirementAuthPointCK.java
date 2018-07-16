/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementAuthPointCK extends VirementRequestCK
/*     */   implements Serializable
/*     */ {
/*     */   protected VirementAuthPointPK mVirementAuthPointPK;
/*     */ 
/*     */   public VirementAuthPointCK(ModelPK paramModelPK, VirementRequestPK paramVirementRequestPK, VirementAuthPointPK paramVirementAuthPointPK)
/*     */   {
/*  32 */     super(paramModelPK, paramVirementRequestPK);
/*     */ 
/*  36 */     this.mVirementAuthPointPK = paramVirementAuthPointPK;
/*     */   }
/*     */ 
/*     */   public VirementAuthPointPK getVirementAuthPointPK()
/*     */   {
/*  44 */     return this.mVirementAuthPointPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mVirementAuthPointPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mVirementAuthPointPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof VirementAuthPointPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof VirementAuthPointCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     VirementAuthPointCK other = (VirementAuthPointCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mVirementRequestPK.equals(other.mVirementRequestPK));
/*  80 */     eq = (eq) && (this.mVirementAuthPointPK.equals(other.mVirementAuthPointPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mVirementAuthPointPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("VirementAuthPointCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mVirementAuthPointPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("VirementAuthPointCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("VirementRequestPK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("VirementAuthPointPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new VirementAuthPointCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementAuthPointPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementAuthPointCK
 * JD-Core Version:    0.6.0
 */