/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ModelDimensionRelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mModelId;
/*     */   int mDimensionId;
/*     */ 
/*     */   public ModelDimensionRelPK(int newModelId, int newDimensionId)
/*     */   {
/*  24 */     this.mModelId = newModelId;
/*  25 */     this.mDimensionId = newDimensionId;
/*     */   }
/*     */ 
/*     */   public int getModelId()
/*     */   {
/*  34 */     return this.mModelId;
/*     */   }
/*     */ 
/*     */   public int getDimensionId()
/*     */   {
/*  41 */     return this.mDimensionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mModelId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mDimensionId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ModelDimensionRelPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ModelDimensionRelCK)) {
/*  66 */       other = ((ModelDimensionRelCK)obj).getModelDimensionRelPK();
/*     */     }
/*  68 */     else if ((obj instanceof ModelDimensionRelPK))
/*  69 */       other = (ModelDimensionRelPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mModelId == other.mModelId);
/*  76 */     eq = (eq) && (this.mDimensionId == other.mDimensionId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ModelId=");
/*  88 */     sb.append(this.mModelId);
/*  89 */     sb.append(",DimensionId=");
/*  90 */     sb.append(this.mDimensionId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mModelId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mDimensionId);
/* 104 */     return "ModelDimensionRelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ModelDimensionRelPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ModelDimensionRelPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ModelDimensionRelPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pModelId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pDimensionId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ModelDimensionRelPK(pModelId, pDimensionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.ModelDimensionRelPK
 * JD-Core Version:    0.6.0
 */