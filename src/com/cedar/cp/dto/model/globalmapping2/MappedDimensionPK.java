/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedDimensionPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedDimensionId;
/*     */ 
/*     */   public MappedDimensionPK(int newMappedDimensionId)
/*     */   {
/*  23 */     this.mMappedDimensionId = newMappedDimensionId;
/*     */   }
/*     */ 
/*     */   public int getMappedDimensionId()
/*     */   {
/*  32 */     return this.mMappedDimensionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedDimensionId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedDimensionPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedDimensionCK)) {
/*  56 */       other = ((MappedDimensionCK)obj).getMappedDimensionPK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedDimensionPK))
/*  59 */       other = (MappedDimensionPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedDimensionId == other.mMappedDimensionId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedDimensionId=");
/*  77 */     sb.append(this.mMappedDimensionId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedDimensionId);
/*  89 */     return "MappedDimensionPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedDimensionPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedDimensionPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedDimensionPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedDimensionId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedDimensionPK(pMappedDimensionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedDimensionPK
 * JD-Core Version:    0.6.0
 */