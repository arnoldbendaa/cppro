/*     */ package com.cedar.cp.dto.task.group;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TgRowParamCK extends TgRowCK
/*     */   implements Serializable
/*     */ {
/*     */   protected TgRowParamPK mTgRowParamPK;
/*     */ 
/*     */   public TgRowParamCK(TaskGroupPK paramTaskGroupPK, TgRowPK paramTgRowPK, TgRowParamPK paramTgRowParamPK)
/*     */   {
/*  31 */     super(paramTaskGroupPK, paramTgRowPK);
/*     */ 
/*  35 */     this.mTgRowParamPK = paramTgRowParamPK;
/*     */   }
/*     */ 
/*     */   public TgRowParamPK getTgRowParamPK()
/*     */   {
/*  43 */     return this.mTgRowParamPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mTgRowParamPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mTgRowParamPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof TgRowParamPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof TgRowParamCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     TgRowParamCK other = (TgRowParamCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mTaskGroupPK.equals(other.mTaskGroupPK));
/*  78 */     eq = (eq) && (this.mTgRowPK.equals(other.mTgRowPK));
/*  79 */     eq = (eq) && (this.mTgRowParamPK.equals(other.mTgRowParamPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mTgRowParamPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("TgRowParamCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mTgRowParamPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static TaskGroupCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("TgRowParamCK", token[(i++)]);
/* 115 */     checkExpected("TaskGroupPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("TgRowPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("TgRowParamPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new TgRowParamCK(TaskGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), TgRowPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), TgRowParamPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.group.TgRowParamCK
 * JD-Core Version:    0.6.0
 */