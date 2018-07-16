/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ModelPropertyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mModelId;
/*     */   String mPropertyName;
/*     */ 
/*     */   public ModelPropertyPK(int newModelId, String newPropertyName)
/*     */   {
/*  24 */     this.mModelId = newModelId;
/*  25 */     this.mPropertyName = newPropertyName;
/*     */   }
/*     */ 
/*     */   public int getModelId()
/*     */   {
/*  34 */     return this.mModelId;
/*     */   }
/*     */ 
/*     */   public String getPropertyName()
/*     */   {
/*  41 */     return this.mPropertyName;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mModelId).hashCode();
/*  52 */       this.mHashCode += this.mPropertyName.hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ModelPropertyPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ModelPropertyCK)) {
/*  66 */       other = ((ModelPropertyCK)obj).getModelPropertyPK();
/*     */     }
/*  68 */     else if ((obj instanceof ModelPropertyPK))
/*  69 */       other = (ModelPropertyPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mModelId == other.mModelId);
/*  76 */     eq = (eq) && (this.mPropertyName.equals(other.mPropertyName));
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ModelId=");
/*  88 */     sb.append(this.mModelId);
/*  89 */     sb.append(",PropertyName=");
/*  90 */     sb.append(this.mPropertyName);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mModelId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mPropertyName);
/* 104 */     return "ModelPropertyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ModelPropertyPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ModelPropertyPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ModelPropertyPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pModelId = new Integer(extValues[(i++)]).intValue();
/* 121 */     String pPropertyName = new String(extValues[(i++)]);
/* 122 */     return new ModelPropertyPK(pModelId, pPropertyName);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.ModelPropertyPK
 * JD-Core Version:    0.6.0
 */