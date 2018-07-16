/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ModelPropertyCK extends ModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected ModelPropertyPK mModelPropertyPK;
/*     */ 
/*     */   public ModelPropertyCK(ModelPK paramModelPK, ModelPropertyPK paramModelPropertyPK)
/*     */   {
/*  29 */     super(paramModelPK);
/*     */ 
/*  32 */     this.mModelPropertyPK = paramModelPropertyPK;
/*     */   }
/*     */ 
/*     */   public ModelPropertyPK getModelPropertyPK()
/*     */   {
/*  40 */     return this.mModelPropertyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mModelPropertyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mModelPropertyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof ModelPropertyPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof ModelPropertyCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     ModelPropertyCK other = (ModelPropertyCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  75 */     eq = (eq) && (this.mModelPropertyPK.equals(other.mModelPropertyPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mModelPropertyPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("ModelPropertyCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mModelPropertyPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("ModelPropertyCK", token[(i++)]);
/* 111 */     checkExpected("ModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("ModelPropertyPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new ModelPropertyCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ModelPropertyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.ModelPropertyCK
 * JD-Core Version:    0.6.0
 */