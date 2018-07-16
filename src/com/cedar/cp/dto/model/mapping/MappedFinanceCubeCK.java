/*     */ package com.cedar.cp.dto.model.mapping;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedFinanceCubeCK extends MappedModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedFinanceCubePK mMappedFinanceCubePK;
/*     */ 
/*     */   public MappedFinanceCubeCK(MappedModelPK paramMappedModelPK, MappedFinanceCubePK paramMappedFinanceCubePK)
/*     */   {
/*  29 */     super(paramMappedModelPK);
/*     */ 
/*  32 */     this.mMappedFinanceCubePK = paramMappedFinanceCubePK;
/*     */   }
/*     */ 
/*     */   public MappedFinanceCubePK getMappedFinanceCubePK()
/*     */   {
/*  40 */     return this.mMappedFinanceCubePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mMappedFinanceCubePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mMappedFinanceCubePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof MappedFinanceCubePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof MappedFinanceCubeCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     MappedFinanceCubeCK other = (MappedFinanceCubeCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  75 */     eq = (eq) && (this.mMappedFinanceCubePK.equals(other.mMappedFinanceCubePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mMappedFinanceCubePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("MappedFinanceCubeCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mMappedFinanceCubePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MappedModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("MappedFinanceCubeCK", token[(i++)]);
/* 111 */     checkExpected("MappedModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("MappedFinanceCubePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new MappedFinanceCubeCK(MappedModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedFinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedFinanceCubeCK
 * JD-Core Version:    0.6.0
 */