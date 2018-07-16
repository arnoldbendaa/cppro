/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormulaDeploymentLineCK extends CubeFormulaCK
/*     */   implements Serializable
/*     */ {
/*     */   protected FormulaDeploymentLinePK mFormulaDeploymentLinePK;
/*     */ 
/*     */   public FormulaDeploymentLineCK(ModelPK paramModelPK, FinanceCubePK paramFinanceCubePK, CubeFormulaPK paramCubeFormulaPK, FormulaDeploymentLinePK paramFormulaDeploymentLinePK)
/*     */   {
/*  34 */     super(paramModelPK, paramFinanceCubePK, paramCubeFormulaPK);
/*     */ 
/*  39 */     this.mFormulaDeploymentLinePK = paramFormulaDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public FormulaDeploymentLinePK getFormulaDeploymentLinePK()
/*     */   {
/*  47 */     return this.mFormulaDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  55 */     return this.mFormulaDeploymentLinePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  63 */     return this.mFormulaDeploymentLinePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  72 */     if ((obj instanceof FormulaDeploymentLinePK)) {
/*  73 */       return obj.equals(this);
/*     */     }
/*  75 */     if (!(obj instanceof FormulaDeploymentLineCK)) {
/*  76 */       return false;
/*     */     }
/*  78 */     FormulaDeploymentLineCK other = (FormulaDeploymentLineCK)obj;
/*  79 */     boolean eq = true;
/*     */ 
/*  81 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  82 */     eq = (eq) && (this.mFinanceCubePK.equals(other.mFinanceCubePK));
/*  83 */     eq = (eq) && (this.mCubeFormulaPK.equals(other.mCubeFormulaPK));
/*  84 */     eq = (eq) && (this.mFormulaDeploymentLinePK.equals(other.mFormulaDeploymentLinePK));
/*     */ 
/*  86 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  94 */     StringBuffer sb = new StringBuffer();
/*  95 */     sb.append(super.toString());
/*  96 */     sb.append("[");
/*  97 */     sb.append(this.mFormulaDeploymentLinePK);
/*  98 */     sb.append("]");
/*  99 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 107 */     StringBuffer sb = new StringBuffer();
/* 108 */     sb.append("FormulaDeploymentLineCK|");
/* 109 */     sb.append(super.getPK().toTokens());
/* 110 */     sb.append('|');
/* 111 */     sb.append(this.mFormulaDeploymentLinePK.toTokens());
/* 112 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 117 */     String[] token = extKey.split("[|]");
/* 118 */     int i = 0;
/* 119 */     checkExpected("FormulaDeploymentLineCK", token[(i++)]);
/* 120 */     checkExpected("ModelPK", token[(i++)]);
/* 121 */     i++;
/* 122 */     checkExpected("FinanceCubePK", token[(i++)]);
/* 123 */     i++;
/* 124 */     checkExpected("CubeFormulaPK", token[(i++)]);
/* 125 */     i++;
/* 126 */     checkExpected("FormulaDeploymentLinePK", token[(i++)]);
/* 127 */     i = 1;
/* 128 */     return new FormulaDeploymentLineCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CubeFormulaPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FormulaDeploymentLinePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 138 */     if (!expected.equals(found))
/* 139 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.FormulaDeploymentLineCK
 * JD-Core Version:    0.6.0
 */