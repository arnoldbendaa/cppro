/*     */ package com.cedar.cp.dto.task.group;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TaskGroupCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected TaskGroupPK mTaskGroupPK;
/*     */ 
/*     */   public TaskGroupCK(TaskGroupPK paramTaskGroupPK)
/*     */   {
/*  26 */     this.mTaskGroupPK = paramTaskGroupPK;
/*     */   }
/*     */ 
/*     */   public TaskGroupPK getTaskGroupPK()
/*     */   {
/*  34 */     return this.mTaskGroupPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mTaskGroupPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mTaskGroupPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof TaskGroupPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof TaskGroupCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     TaskGroupCK other = (TaskGroupCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mTaskGroupPK.equals(other.mTaskGroupPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mTaskGroupPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("TaskGroupCK|");
/*  92 */     sb.append(this.mTaskGroupPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static TaskGroupCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("TaskGroupCK", token[(i++)]);
/* 101 */     checkExpected("TaskGroupPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new TaskGroupCK(TaskGroupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.group.TaskGroupCK
 * JD-Core Version:    0.6.0
 */