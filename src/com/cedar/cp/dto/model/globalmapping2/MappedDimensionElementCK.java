/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedDimensionElementCK extends MappedDimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedDimensionElementPK mMappedDimensionElementPK;
/*     */ 
/*     */   public MappedDimensionElementCK(GlobalMappedModel2PK paramMappedModelPK, MappedDimensionPK paramMappedDimensionPK, MappedDimensionElementPK paramMappedDimensionElementPK)
/*     */   {
/*  31 */     super(paramMappedModelPK, paramMappedDimensionPK);
/*     */ 
/*  35 */     this.mMappedDimensionElementPK = paramMappedDimensionElementPK;
/*     */   }
/*     */ 
/*     */   public MappedDimensionElementPK getMappedDimensionElementPK()
/*     */   {
/*  43 */     return this.mMappedDimensionElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mMappedDimensionElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mMappedDimensionElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof MappedDimensionElementPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof MappedDimensionElementCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     MappedDimensionElementCK other = (MappedDimensionElementCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*  78 */     eq = (eq) && (this.mMappedDimensionPK.equals(other.mMappedDimensionPK));
/*  79 */     eq = (eq) && (this.mMappedDimensionElementPK.equals(other.mMappedDimensionElementPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mMappedDimensionElementPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("MappedDimensionElementCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mMappedDimensionElementPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static GlobalMappedModel2CK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("MappedDimensionElementCK", token[(i++)]);
/* 115 */     checkExpected("GlobalMappedModel2PK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("MappedDimensionPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("MappedDimensionElementPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new MappedDimensionElementCK(GlobalMappedModel2PK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), MappedDimensionElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedDimensionElementCK
 * JD-Core Version:    0.6.0
 */