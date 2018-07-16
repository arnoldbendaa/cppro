/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ModelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mModelId;
/*     */ 
/*     */   public ModelPK(int newModelId)
/*     */   {
/*  23 */     this.mModelId = newModelId;
/*     */   }
/*     */ 
/*     */   public int getModelId()
/*     */   {
/*  32 */     return this.mModelId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mModelId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ModelPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ModelCK)) {
/*  56 */       other = ((ModelCK)obj).getModelPK();
/*     */     }
/*  58 */     else if ((obj instanceof ModelPK))
/*  59 */       other = (ModelPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mModelId == other.mModelId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ModelId=");
/*  77 */     sb.append(this.mModelId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mModelId);
/*  89 */     return "ModelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ModelPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
                int modelId = new Integer(extKey).intValue();
                return new ModelPK(modelId);
/*     */     }
/*  99 */     if (!extValues[0].equals("ModelPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ModelPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pModelId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ModelPK(pModelId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.ModelPK
 * JD-Core Version:    0.6.0
 */