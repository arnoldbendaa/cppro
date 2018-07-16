/*     */ package com.cedar.cp.dto.task.group;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TaskGroupPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mGroupId;
/*     */ 
/*     */   public TaskGroupPK(int newGroupId)
/*     */   {
/*  23 */     this.mGroupId = newGroupId;
/*     */   }
/*     */ 
/*     */   public int getGroupId()
/*     */   {
/*  32 */     return this.mGroupId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mGroupId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     TaskGroupPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof TaskGroupCK)) {
/*  56 */       other = ((TaskGroupCK)obj).getTaskGroupPK();
/*     */     }
/*  58 */     else if ((obj instanceof TaskGroupPK))
/*  59 */       other = (TaskGroupPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mGroupId == other.mGroupId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" GroupId=");
/*  77 */     sb.append(this.mGroupId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mGroupId);
/*  89 */     return "TaskGroupPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static TaskGroupPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("TaskGroupPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'TaskGroupPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pGroupId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new TaskGroupPK(pGroupId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.group.TaskGroupPK
 * JD-Core Version:    0.6.0
 */