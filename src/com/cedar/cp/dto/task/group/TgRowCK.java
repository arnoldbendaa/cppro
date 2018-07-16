/*     */ package com.cedar.cp.dto.task.group;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TgRowCK extends TaskGroupCK
/*     */   implements Serializable
/*     */ {
/*     */   protected TgRowPK mTgRowPK;
/*     */ 
/*     */   public TgRowCK(TaskGroupPK paramTaskGroupPK, TgRowPK paramTgRowPK)
/*     */   {
/*  29 */     super(paramTaskGroupPK);
/*     */ 
/*  32 */     this.mTgRowPK = paramTgRowPK;
/*     */   }
/*     */ 
/*     */   public TgRowPK getTgRowPK()
/*     */   {
/*  40 */     return this.mTgRowPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mTgRowPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mTgRowPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof TgRowPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof TgRowCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     TgRowCK other = (TgRowCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mTaskGroupPK.equals(other.mTaskGroupPK));
/*  75 */     eq = (eq) && (this.mTgRowPK.equals(other.mTgRowPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mTgRowPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("TgRowCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mTgRowPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static TaskGroupCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("TgRowCK", token[(i++)]);
/* 111 */     checkExpected("TaskGroupPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("TgRowPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new TgRowCK(TaskGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), TgRowPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.group.TgRowCK
 * JD-Core Version:    0.6.0
 */