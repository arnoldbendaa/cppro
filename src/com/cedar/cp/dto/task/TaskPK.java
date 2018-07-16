/*     */ package com.cedar.cp.dto.task;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TaskPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mTaskId;
/*     */ 
/*     */   public TaskPK(int newTaskId)
/*     */   {
/*  23 */     this.mTaskId = newTaskId;
/*     */   }
/*     */ 
/*     */   public int getTaskId()
/*     */   {
/*  32 */     return this.mTaskId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mTaskId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     TaskPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof TaskCK)) {
/*  56 */       other = ((TaskCK)obj).getTaskPK();
/*     */     }
/*  58 */     else if ((obj instanceof TaskPK))
/*  59 */       other = (TaskPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mTaskId == other.mTaskId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" TaskId=");
/*  77 */     sb.append(this.mTaskId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mTaskId);
/*  89 */     return "TaskPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static TaskPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("TaskPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'TaskPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pTaskId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new TaskPK(pTaskId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.TaskPK
 * JD-Core Version:    0.6.0
 */