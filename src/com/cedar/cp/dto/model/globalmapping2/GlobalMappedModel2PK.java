/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class GlobalMappedModel2PK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedModelId;
/*     */ 
/*     */   public GlobalMappedModel2PK(int newMappedModelId)
/*     */   {
/*  23 */     this.mMappedModelId = newMappedModelId;
/*     */   }
/*     */ 
/*     */   public int getMappedModelId()
/*     */   {
/*  32 */     return this.mMappedModelId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedModelId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     GlobalMappedModel2PK other = null;
/*     */ 
/*  55 */     if ((obj instanceof GlobalMappedModel2CK)) {
/*  56 */       other = ((GlobalMappedModel2CK)obj).getMappedModelPK();
/*     */     }
/*  58 */     else if ((obj instanceof GlobalMappedModel2PK))
/*  59 */       other = (GlobalMappedModel2PK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedModelId == other.mMappedModelId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedModelId=");
/*  77 */     sb.append(this.mMappedModelId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedModelId);
/*  89 */     return "GlobalMappedModel2PK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static GlobalMappedModel2PK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("GlobalMappedModel2PK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'GlobalMappedModel2PK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedModelId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new GlobalMappedModel2PK(pMappedModelId);
/*     */   }
/*     */ }