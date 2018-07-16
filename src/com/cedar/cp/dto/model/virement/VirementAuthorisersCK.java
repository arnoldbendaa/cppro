/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementAuthorisersCK extends VirementAuthPointCK
/*     */   implements Serializable
/*     */ {
/*     */   protected VirementAuthorisersPK mVirementAuthorisersPK;
/*     */ 
/*     */   public VirementAuthorisersCK(ModelPK paramModelPK, VirementRequestPK paramVirementRequestPK, VirementAuthPointPK paramVirementAuthPointPK, VirementAuthorisersPK paramVirementAuthorisersPK)
/*     */   {
/*  34 */     super(paramModelPK, paramVirementRequestPK, paramVirementAuthPointPK);
/*     */ 
/*  39 */     this.mVirementAuthorisersPK = paramVirementAuthorisersPK;
/*     */   }
/*     */ 
/*     */   public VirementAuthorisersPK getVirementAuthorisersPK()
/*     */   {
/*  47 */     return this.mVirementAuthorisersPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  55 */     return this.mVirementAuthorisersPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  63 */     return this.mVirementAuthorisersPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  72 */     if ((obj instanceof VirementAuthorisersPK)) {
/*  73 */       return obj.equals(this);
/*     */     }
/*  75 */     if (!(obj instanceof VirementAuthorisersCK)) {
/*  76 */       return false;
/*     */     }
/*  78 */     VirementAuthorisersCK other = (VirementAuthorisersCK)obj;
/*  79 */     boolean eq = true;
/*     */ 
/*  81 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  82 */     eq = (eq) && (this.mVirementRequestPK.equals(other.mVirementRequestPK));
/*  83 */     eq = (eq) && (this.mVirementAuthPointPK.equals(other.mVirementAuthPointPK));
/*  84 */     eq = (eq) && (this.mVirementAuthorisersPK.equals(other.mVirementAuthorisersPK));
/*     */ 
/*  86 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  94 */     StringBuffer sb = new StringBuffer();
/*  95 */     sb.append(super.toString());
/*  96 */     sb.append("[");
/*  97 */     sb.append(this.mVirementAuthorisersPK);
/*  98 */     sb.append("]");
/*  99 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 107 */     StringBuffer sb = new StringBuffer();
/* 108 */     sb.append("VirementAuthorisersCK|");
/* 109 */     sb.append(super.getPK().toTokens());
/* 110 */     sb.append('|');
/* 111 */     sb.append(this.mVirementAuthorisersPK.toTokens());
/* 112 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 117 */     String[] token = extKey.split("[|]");
/* 118 */     int i = 0;
/* 119 */     checkExpected("VirementAuthorisersCK", token[(i++)]);
/* 120 */     checkExpected("ModelPK", token[(i++)]);
/* 121 */     i++;
/* 122 */     checkExpected("VirementRequestPK", token[(i++)]);
/* 123 */     i++;
/* 124 */     checkExpected("VirementAuthPointPK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("VirementAuthorisersPK", token[(i++)]);
/* 127 */     i = 1;
/* 128 */     return new VirementAuthorisersCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementAuthPointPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementAuthorisersPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 138 */     if (!expected.equals(found))
/* 139 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementAuthorisersCK
 * JD-Core Version:    0.6.0
 */