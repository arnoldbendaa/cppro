/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedFinanceCubePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mMappedFinanceCubeId;
/*     */ 
/*     */   public MappedFinanceCubePK(int newMappedFinanceCubeId)
/*     */   {
/*  23 */     this.mMappedFinanceCubeId = newMappedFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int getMappedFinanceCubeId()
/*     */   {
/*  32 */     return this.mMappedFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mMappedFinanceCubeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     MappedFinanceCubePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof MappedFinanceCubeCK)) {
/*  56 */       other = ((MappedFinanceCubeCK)obj).getMappedFinanceCubePK();
/*     */     }
/*  58 */     else if ((obj instanceof MappedFinanceCubePK))
/*  59 */       other = (MappedFinanceCubePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mMappedFinanceCubeId == other.mMappedFinanceCubeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" MappedFinanceCubeId=");
/*  77 */     sb.append(this.mMappedFinanceCubeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mMappedFinanceCubeId);
/*  89 */     return "MappedFinanceCubePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static MappedFinanceCubePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("MappedFinanceCubePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'MappedFinanceCubePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pMappedFinanceCubeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new MappedFinanceCubePK(pMappedFinanceCubeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedFinanceCubePK
 * JD-Core Version:    0.6.0
 */