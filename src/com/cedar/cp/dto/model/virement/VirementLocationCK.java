/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementLocationCK extends VirementCategoryCK
/*     */   implements Serializable
/*     */ {
/*     */   protected VirementLocationPK mVirementLocationPK;
/*     */ 
/*     */   public VirementLocationCK(ModelPK paramModelPK, VirementCategoryPK paramVirementCategoryPK, VirementLocationPK paramVirementLocationPK)
/*     */   {
/*  32 */     super(paramModelPK, paramVirementCategoryPK);
/*     */ 
/*  36 */     this.mVirementLocationPK = paramVirementLocationPK;
/*     */   }
/*     */ 
/*     */   public VirementLocationPK getVirementLocationPK()
/*     */   {
/*  44 */     return this.mVirementLocationPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mVirementLocationPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mVirementLocationPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof VirementLocationPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof VirementLocationCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     VirementLocationCK other = (VirementLocationCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mVirementCategoryPK.equals(other.mVirementCategoryPK));
/*  80 */     eq = (eq) && (this.mVirementLocationPK.equals(other.mVirementLocationPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mVirementLocationPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("VirementLocationCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mVirementLocationPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("VirementLocationCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("VirementCategoryPK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("VirementLocationPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new VirementLocationCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementCategoryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementLocationPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementLocationCK
 * JD-Core Version:    0.6.0
 */