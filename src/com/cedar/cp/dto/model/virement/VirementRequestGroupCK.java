/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementRequestGroupCK extends VirementRequestCK
/*     */   implements Serializable
/*     */ {
/*     */   protected VirementRequestGroupPK mVirementRequestGroupPK;
/*     */ 
/*     */   public VirementRequestGroupCK(ModelPK paramModelPK, VirementRequestPK paramVirementRequestPK, VirementRequestGroupPK paramVirementRequestGroupPK)
/*     */   {
/*  32 */     super(paramModelPK, paramVirementRequestPK);
/*     */ 
/*  36 */     this.mVirementRequestGroupPK = paramVirementRequestGroupPK;
/*     */   }
/*     */ 
/*     */   public VirementRequestGroupPK getVirementRequestGroupPK()
/*     */   {
/*  44 */     return this.mVirementRequestGroupPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mVirementRequestGroupPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mVirementRequestGroupPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof VirementRequestGroupPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof VirementRequestGroupCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     VirementRequestGroupCK other = (VirementRequestGroupCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mVirementRequestPK.equals(other.mVirementRequestPK));
/*  80 */     eq = (eq) && (this.mVirementRequestGroupPK.equals(other.mVirementRequestGroupPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mVirementRequestGroupPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("VirementRequestGroupCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mVirementRequestGroupPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("VirementRequestGroupCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("VirementRequestPK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("VirementRequestGroupPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new VirementRequestGroupCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementRequestGroupCK
 * JD-Core Version:    0.6.0
 */