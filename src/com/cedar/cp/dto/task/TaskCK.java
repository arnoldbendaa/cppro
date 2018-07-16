/*     */ package com.cedar.cp.dto.task;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TaskCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected TaskPK mTaskPK;
/*     */ 
/*     */   public TaskCK(TaskPK paramTaskPK)
/*     */   {
/*  26 */     this.mTaskPK = paramTaskPK;
/*     */   }
/*     */ 
/*     */   public TaskPK getTaskPK()
/*     */   {
/*  34 */     return this.mTaskPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mTaskPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mTaskPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof TaskPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof TaskCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     TaskCK other = (TaskCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mTaskPK.equals(other.mTaskPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mTaskPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("TaskCK|");
/*  92 */     sb.append(this.mTaskPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static TaskCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("TaskCK", token[(i++)]);
/* 101 */     checkExpected("TaskPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new TaskCK(TaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.TaskCK
 * JD-Core Version:    0.6.0
 */