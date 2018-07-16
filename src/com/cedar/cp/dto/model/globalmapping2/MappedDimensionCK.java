/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedDimensionCK extends GlobalMappedModel2CK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedDimensionPK mMappedDimensionPK;
/*     */ 
/*     */   public MappedDimensionCK(GlobalMappedModel2PK paramMappedModelPK, MappedDimensionPK paramMappedDimensionPK)
/*     */   {
/*  29 */     super(paramMappedModelPK);
/*     */ 
/*  32 */     this.mMappedDimensionPK = paramMappedDimensionPK;
/*     */   }
/*     */ 
/*     */   public MappedDimensionPK getMappedDimensionPK()
/*     */   {
/*  40 */     return this.mMappedDimensionPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mMappedDimensionPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mMappedDimensionPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof MappedDimensionPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof MappedDimensionCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     MappedDimensionCK other = (MappedDimensionCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  75 */     eq = (eq) && (this.mMappedDimensionPK.equals(other.mMappedDimensionPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mMappedDimensionPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("MappedDimensionCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mMappedDimensionPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static GlobalMappedModel2CK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("MappedDimensionCK", token[(i++)]);
/* 111 */     checkExpected("GlobalMappedModel2PK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("MappedDimensionPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new MappedDimensionCK(GlobalMappedModel2PK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedDimensionCK
 * JD-Core Version:    0.6.0
 */