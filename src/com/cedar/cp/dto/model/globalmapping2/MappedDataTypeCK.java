/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedDataTypeCK extends MappedFinanceCubeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedDataTypePK mMappedDataTypePK;
/*     */ 
/*     */   public MappedDataTypeCK(GlobalMappedModel2PK paramMappedModelPK, MappedFinanceCubePK paramMappedFinanceCubePK, MappedDataTypePK paramMappedDataTypePK)
/*     */   {
/*  31 */     super(paramMappedModelPK, paramMappedFinanceCubePK);
/*     */ 
/*  35 */     this.mMappedDataTypePK = paramMappedDataTypePK;
/*     */   }
/*     */ 
/*     */   public MappedDataTypePK getMappedDataTypePK()
/*     */   {
/*  43 */     return this.mMappedDataTypePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mMappedDataTypePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mMappedDataTypePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof MappedDataTypePK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof MappedDataTypeCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     MappedDataTypeCK other = (MappedDataTypeCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  78 */     eq = (eq) && (this.mMappedFinanceCubePK.equals(other.mMappedFinanceCubePK));
/*  79 */     eq = (eq) && (this.mMappedDataTypePK.equals(other.mMappedDataTypePK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mMappedDataTypePK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("MappedDataTypeCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mMappedDataTypePK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static GlobalMappedModel2CK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("MappedDataTypeCK", token[(i++)]);
/* 115 */     checkExpected("GlobalMappedModel2PK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("MappedFinanceCubePK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("MappedDataTypePK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new MappedDataTypeCK(GlobalMappedModel2PK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedFinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedDataTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedDataTypeCK
 * JD-Core Version:    0.6.0
 */