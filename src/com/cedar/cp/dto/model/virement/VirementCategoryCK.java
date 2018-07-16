/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementCategoryCK extends ModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected VirementCategoryPK mVirementCategoryPK;
/*     */ 
/*     */   public VirementCategoryCK(ModelPK paramModelPK, VirementCategoryPK paramVirementCategoryPK)
/*     */   {
/*  30 */     super(paramModelPK);
/*     */ 
/*  33 */     this.mVirementCategoryPK = paramVirementCategoryPK;
/*     */   }
/*     */ 
/*     */   public VirementCategoryPK getVirementCategoryPK()
/*     */   {
/*  41 */     return this.mVirementCategoryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  49 */     return this.mVirementCategoryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  57 */     return this.mVirementCategoryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  66 */     if ((obj instanceof VirementCategoryPK)) {
/*  67 */       return obj.equals(this);
/*     */     }
/*  69 */     if (!(obj instanceof VirementCategoryCK)) {
/*  70 */       return false;
/*     */     }
/*  72 */     VirementCategoryCK other = (VirementCategoryCK)obj;
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  76 */     eq = (eq) && (this.mVirementCategoryPK.equals(other.mVirementCategoryPK));
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(super.toString());
/*  88 */     sb.append("[");
/*  89 */     sb.append(this.mVirementCategoryPK);
/*  90 */     sb.append("]");
/*  91 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append("VirementCategoryCK|");
/* 101 */     sb.append(super.getPK().toTokens());
/* 102 */     sb.append('|');
/* 103 */     sb.append(this.mVirementCategoryPK.toTokens());
/* 104 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] token = extKey.split("[|]");
/* 110 */     int i = 0;
/* 111 */     checkExpected("VirementCategoryCK", token[(i++)]);
/* 112 */     checkExpected("ModelPK", token[(i++)]);
/* 113 */     i++;
/* 114 */     checkExpected("VirementCategoryPK", token[(i++)]);
/* 115 */     i = 1;
/* 116 */     return new VirementCategoryCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementCategoryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 124 */     if (!expected.equals(found))
/* 125 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementCategoryCK
 * JD-Core Version:    0.6.0
 */