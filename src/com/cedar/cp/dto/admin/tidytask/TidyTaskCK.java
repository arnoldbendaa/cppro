/*     */ package com.cedar.cp.dto.admin.tidytask;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TidyTaskCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected TidyTaskPK mTidyTaskPK;
/*     */ 
/*     */   public TidyTaskCK(TidyTaskPK paramTidyTaskPK)
/*     */   {
/*  26 */     this.mTidyTaskPK = paramTidyTaskPK;
/*     */   }
/*     */ 
/*     */   public TidyTaskPK getTidyTaskPK()
/*     */   {
/*  34 */     return this.mTidyTaskPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mTidyTaskPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mTidyTaskPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof TidyTaskPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof TidyTaskCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     TidyTaskCK other = (TidyTaskCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mTidyTaskPK.equals(other.mTidyTaskPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mTidyTaskPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("TidyTaskCK|");
/*  92 */     sb.append(this.mTidyTaskPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static TidyTaskCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("TidyTaskCK", token[(i++)]);
/* 101 */     checkExpected("TidyTaskPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new TidyTaskCK(TidyTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.admin.tidytask.TidyTaskCK
 * JD-Core Version:    0.6.0
 */