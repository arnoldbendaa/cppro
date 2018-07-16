/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedDataTypePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedDataTypeId;
/*     */ 
/*     */   public MappedDataTypePK(int newMappedDataTypeId)
/*     */   {
/*  23 */     this.mMappedDataTypeId = newMappedDataTypeId;
/*     */   }
/*     */ 
/*     */   public int getMappedDataTypeId()
/*     */   {
/*  32 */     return this.mMappedDataTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedDataTypeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedDataTypePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedDataTypeCK)) {
/*  56 */       other = ((MappedDataTypeCK)obj).getMappedDataTypePK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedDataTypePK))
/*  59 */       other = (MappedDataTypePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedDataTypeId == other.mMappedDataTypeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedDataTypeId=");
/*  77 */     sb.append(this.mMappedDataTypeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedDataTypeId);
/*  89 */     return "MappedDataTypePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedDataTypePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedDataTypePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedDataTypePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedDataTypeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedDataTypePK(pMappedDataTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedDataTypePK
 * JD-Core Version:    0.6.0
 */