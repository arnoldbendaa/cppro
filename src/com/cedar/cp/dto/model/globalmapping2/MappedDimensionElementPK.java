/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedDimensionElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedDimensionElementId;
/*     */ 
/*     */   public MappedDimensionElementPK(int newMappedDimensionElementId)
/*     */   {
/*  23 */     this.mMappedDimensionElementId = newMappedDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int getMappedDimensionElementId()
/*     */   {
/*  32 */     return this.mMappedDimensionElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedDimensionElementId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedDimensionElementPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedDimensionElementCK)) {
/*  56 */       other = ((MappedDimensionElementCK)obj).getMappedDimensionElementPK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedDimensionElementPK))
/*  59 */       other = (MappedDimensionElementPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedDimensionElementId == other.mMappedDimensionElementId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedDimensionElementId=");
/*  77 */     sb.append(this.mMappedDimensionElementId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedDimensionElementId);
/*  89 */     return "MappedDimensionElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedDimensionElementPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedDimensionElementPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedDimensionElementPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedDimensionElementId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedDimensionElementPK(pMappedDimensionElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedDimensionElementPK
 * JD-Core Version:    0.6.0
 */