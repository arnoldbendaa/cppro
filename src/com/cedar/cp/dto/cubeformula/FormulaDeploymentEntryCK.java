/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormulaDeploymentEntryCK extends FormulaDeploymentLineCK
/*     */   implements Serializable
/*     */ {
/*     */   protected FormulaDeploymentEntryPK mFormulaDeploymentEntryPK;
/*     */ 
/*     */   public FormulaDeploymentEntryCK(ModelPK paramModelPK, FinanceCubePK paramFinanceCubePK, CubeFormulaPK paramCubeFormulaPK, FormulaDeploymentLinePK paramFormulaDeploymentLinePK, FormulaDeploymentEntryPK paramFormulaDeploymentEntryPK)
/*     */   {
/*  36 */     super(paramModelPK, paramFinanceCubePK, paramCubeFormulaPK, paramFormulaDeploymentLinePK);
/*     */ 
/*  42 */     this.mFormulaDeploymentEntryPK = paramFormulaDeploymentEntryPK;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentEntryPK getFormulaDeploymentEntryPK()
/*     */   {
/*  50 */     return this.mFormulaDeploymentEntryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  58 */     return this.mFormulaDeploymentEntryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  66 */     return this.mFormulaDeploymentEntryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  75 */     if ((obj instanceof FormulaDeploymentEntryPK)) {
/*  76 */       return obj.equals(this);
/*     */     }
/*  78 */     if (!(obj instanceof FormulaDeploymentEntryCK)) {
/*  79 */       return false;
/*     */     }
/*  81 */     FormulaDeploymentEntryCK other = (FormulaDeploymentEntryCK)obj;
/*  82 */     boolean eq = true;
/*     */ 
/*  84 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  85 */     eq = (eq) && (this.mFinanceCubePK.equals(other.mFinanceCubePK));
/*  86 */     eq = (eq) && (this.mCubeFormulaPK.equals(other.mCubeFormulaPK));
/*  87 */     eq = (eq) && (this.mFormulaDeploymentLinePK.equals(other.mFormulaDeploymentLinePK));
/*  88 */     eq = (eq) && (this.mFormulaDeploymentEntryPK.equals(other.mFormulaDeploymentEntryPK));
/*     */ 
/*  90 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append(super.toString());
/* 100 */     sb.append("[");
/* 101 */     sb.append(this.mFormulaDeploymentEntryPK);
/* 102 */     sb.append("]");
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 111 */     StringBuffer sb = new StringBuffer();
/* 112 */     sb.append("FormulaDeploymentEntryCK|");
/* 113 */     sb.append(super.getPK().toTokens());
/* 114 */     sb.append('|');
/* 115 */     sb.append(this.mFormulaDeploymentEntryPK.toTokens());
/* 116 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 121 */     String[] token = extKey.split("[|]");
/* 122 */     int i = 0;
/* 123 */     checkExpected("FormulaDeploymentEntryCK", token[(i++)]);
/* 124 */     checkExpected("ModelPK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("FinanceCubePK", token[(i++)]);
/* 127 */     i++;
/* 128 */     checkExpected("CubeFormulaPK", token[(i++)]);
/* 129 */     i++;
/* 130 */     checkExpected("FormulaDeploymentLinePK", token[(i++)]);
/* 131 */     i++;
/* 132 */     checkExpected("FormulaDeploymentEntryPK", token[(i++)]);
/* 133 */     i = 1;
/* 134 */     return new FormulaDeploymentEntryCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CubeFormulaPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FormulaDeploymentLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FormulaDeploymentEntryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 145 */     if (!expected.equals(found))
/* 146 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.FormulaDeploymentEntryCK
 * JD-Core Version:    0.6.0
 */