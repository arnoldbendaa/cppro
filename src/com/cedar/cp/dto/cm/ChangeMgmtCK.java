/*     */ package com.cedar.cp.dto.cm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ChangeMgmtCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ChangeMgmtPK mChangeMgmtPK;
/*     */ 
/*     */   public ChangeMgmtCK(ChangeMgmtPK paramChangeMgmtPK)
/*     */   {
/*  26 */     this.mChangeMgmtPK = paramChangeMgmtPK;
/*     */   }
/*     */ 
/*     */   public ChangeMgmtPK getChangeMgmtPK()
/*     */   {
/*  34 */     return this.mChangeMgmtPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mChangeMgmtPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mChangeMgmtPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ChangeMgmtPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ChangeMgmtCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ChangeMgmtCK other = (ChangeMgmtCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mChangeMgmtPK.equals(other.mChangeMgmtPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mChangeMgmtPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ChangeMgmtCK|");
/*  92 */     sb.append(this.mChangeMgmtPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ChangeMgmtCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ChangeMgmtCK", token[(i++)]);
/* 101 */     checkExpected("ChangeMgmtPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ChangeMgmtCK(ChangeMgmtPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cm.ChangeMgmtCK
 * JD-Core Version:    0.6.0
 */