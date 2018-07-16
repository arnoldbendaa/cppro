/*     */ package com.cedar.cp.dto.model.globalmapping2;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class GlobalMappedModel2CK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected GlobalMappedModel2PK mMappedModelPK;
/*     */ 
/*     */   public GlobalMappedModel2CK(GlobalMappedModel2PK paramMappedModelPK)
/*     */   {
/*  26 */     this.mMappedModelPK = paramMappedModelPK;
/*     */   }
/*     */ 
/*     */   public GlobalMappedModel2PK getMappedModelPK()
/*     */   {
/*  34 */     return this.mMappedModelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mMappedModelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mMappedModelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof GlobalMappedModel2PK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof GlobalMappedModel2CK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     GlobalMappedModel2CK other = (GlobalMappedModel2CK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mMappedModelPK.equals(other.mMappedModelPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mMappedModelPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("GlobalMappedModel2CK|");
/*  92 */     sb.append(this.mMappedModelPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static GlobalMappedModel2CK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("GlobalMappedModel2CK", token[(i++)]);
/* 101 */     checkExpected("GlobalMappedModel2PK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new GlobalMappedModel2CK(GlobalMappedModel2PK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.mapping.MappedModelCK
 * JD-Core Version:    0.6.0
 */