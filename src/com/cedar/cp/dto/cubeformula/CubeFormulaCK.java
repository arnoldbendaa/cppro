/*     */ package com.cedar.cp.dto.cubeformula;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CubeFormulaCK extends FinanceCubeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected CubeFormulaPK mCubeFormulaPK;
/*     */ 
/*     */   public CubeFormulaCK(ModelPK paramModelPK, FinanceCubePK paramFinanceCubePK, CubeFormulaPK paramCubeFormulaPK)
/*     */   {
/*  32 */     super(paramModelPK, paramFinanceCubePK);
/*     */ 
/*  36 */     this.mCubeFormulaPK = paramCubeFormulaPK;
/*     */   }
/*     */ 
/*     */   public CubeFormulaPK getCubeFormulaPK()
/*     */   {
/*  44 */     return this.mCubeFormulaPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mCubeFormulaPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mCubeFormulaPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof CubeFormulaPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof CubeFormulaCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     CubeFormulaCK other = (CubeFormulaCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mFinanceCubePK.equals(other.mFinanceCubePK));
/*  80 */     eq = (eq) && (this.mCubeFormulaPK.equals(other.mCubeFormulaPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mCubeFormulaPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("CubeFormulaCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mCubeFormulaPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("CubeFormulaCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("FinanceCubePK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("CubeFormulaPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new CubeFormulaCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), CubeFormulaPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cubeformula.CubeFormulaCK
 * JD-Core Version:    0.6.0
 */