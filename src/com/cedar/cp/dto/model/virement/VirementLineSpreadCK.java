/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementLineSpreadCK extends VirementRequestLineCK
/*     */   implements Serializable
/*     */ {
/*     */   protected VirementLineSpreadPK mVirementLineSpreadPK;
/*     */ 
/*     */   public VirementLineSpreadCK(ModelPK paramModelPK, VirementRequestPK paramVirementRequestPK, VirementRequestGroupPK paramVirementRequestGroupPK, VirementRequestLinePK paramVirementRequestLinePK, VirementLineSpreadPK paramVirementLineSpreadPK)
/*     */   {
/*  36 */     super(paramModelPK, paramVirementRequestPK, paramVirementRequestGroupPK, paramVirementRequestLinePK);
/*     */ 
/*  42 */     this.mVirementLineSpreadPK = paramVirementLineSpreadPK;
/*     */   }
/*     */ 
/*     */   public VirementLineSpreadPK getVirementLineSpreadPK()
/*     */   {
/*  50 */     return this.mVirementLineSpreadPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  58 */     return this.mVirementLineSpreadPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  66 */     return this.mVirementLineSpreadPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  75 */     if ((obj instanceof VirementLineSpreadPK)) {
/*  76 */       return obj.equals(this);
/*     */     }
/*  78 */     if (!(obj instanceof VirementLineSpreadCK)) {
/*  79 */       return false;
/*     */     }
/*  81 */     VirementLineSpreadCK other = (VirementLineSpreadCK)obj;
/*  82 */     boolean eq = true;
/*     */ 
/*  84 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  85 */     eq = (eq) && (this.mVirementRequestPK.equals(other.mVirementRequestPK));
/*  86 */     eq = (eq) && (this.mVirementRequestGroupPK.equals(other.mVirementRequestGroupPK));
/*  87 */     eq = (eq) && (this.mVirementRequestLinePK.equals(other.mVirementRequestLinePK));
/*  88 */     eq = (eq) && (this.mVirementLineSpreadPK.equals(other.mVirementLineSpreadPK));
/*     */ 
/*  90 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append(super.toString());
/* 100 */     sb.append("[");
/* 101 */     sb.append(this.mVirementLineSpreadPK);
/* 102 */     sb.append("]");
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 111 */     StringBuffer sb = new StringBuffer();
/* 112 */     sb.append("VirementLineSpreadCK|");
/* 113 */     sb.append(super.getPK().toTokens());
/* 114 */     sb.append('|');
/* 115 */     sb.append(this.mVirementLineSpreadPK.toTokens());
/* 116 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 121 */     String[] token = extKey.split("[|]");
/* 122 */     int i = 0;
/* 123 */     checkExpected("VirementLineSpreadCK", token[(i++)]);
/* 124 */     checkExpected("ModelPK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("VirementRequestPK", token[(i++)]);
/* 127 */     i++;
/* 128 */     checkExpected("VirementRequestGroupPK", token[(i++)]);
/* 129 */     i++;
/* 130 */     checkExpected("VirementRequestLinePK", token[(i++)]);
/* 131 */     i++;
/* 132 */     checkExpected("VirementLineSpreadPK", token[(i++)]);
/* 133 */     i = 1;
/* 134 */     return new VirementLineSpreadCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementRequestLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), VirementLineSpreadPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 145 */     if (!expected.equals(found))
/* 146 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementLineSpreadCK
 * JD-Core Version:    0.6.0
 */