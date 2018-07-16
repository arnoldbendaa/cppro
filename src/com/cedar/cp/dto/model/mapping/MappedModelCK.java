/*     */ package com.cedar.cp.dto.model.mapping;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class MappedModelCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected MappedModelPK mMappedModelPK;
/*     */ 
/*     */   public MappedModelCK(MappedModelPK paramMappedModelPK)
/*     */   {
/*  26 */     this.mMappedModelPK = paramMappedModelPK;
/*     */   }
/*     */ 
/*     */   public MappedModelPK getMappedModelPK()
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
/*  59 */     if ((obj instanceof MappedModelPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof MappedModelCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     MappedModelCK other = (MappedModelCK)obj;
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
/*  91 */     sb.append("MappedModelCK|");
/*  92 */     sb.append(this.mMappedModelPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static MappedModelCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("MappedModelCK", token[(i++)]);
/* 101 */     checkExpected("MappedModelPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new MappedModelCK(MappedModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
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