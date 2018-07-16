/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CubeFormulaPackageCK extends FinanceCubeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CubeFormulaPackagePK mCubeFormulaPackagePK;
/*     */ 
/*     */   public CubeFormulaPackageCK(ModelPK paramModelPK, FinanceCubePK paramFinanceCubePK, CubeFormulaPackagePK paramCubeFormulaPackagePK)
/*     */   {
/*  32 */     super(paramModelPK, paramFinanceCubePK);
/*     */ 
/*  36 */     this.mCubeFormulaPackagePK = paramCubeFormulaPackagePK;
/*     */   }
/*     */ 
/*     */   public CubeFormulaPackagePK getCubeFormulaPackagePK()
/*     */   {
/*  44 */     return this.mCubeFormulaPackagePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mCubeFormulaPackagePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mCubeFormulaPackagePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof CubeFormulaPackagePK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof CubeFormulaPackageCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     CubeFormulaPackageCK other = (CubeFormulaPackageCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mFinanceCubePK.equals(other.mFinanceCubePK));
/*  80 */     eq = (eq) && (this.mCubeFormulaPackagePK.equals(other.mCubeFormulaPackagePK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mCubeFormulaPackagePK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("CubeFormulaPackageCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mCubeFormulaPackagePK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("CubeFormulaPackageCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("FinanceCubePK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("CubeFormulaPackagePK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new CubeFormulaPackageCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CubeFormulaPackagePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.CubeFormulaPackageCK
 * JD-Core Version:    0.6.0
 */