/*     */ package com.cedar.cp.dto.admin.tidytask;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TidyTaskPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mTidyTaskId;
/*     */ 
/*     */   public TidyTaskPK(int newTidyTaskId)
/*     */   {
/*  23 */     this.mTidyTaskId = newTidyTaskId;
/*     */   }
/*     */ 
/*     */   public int getTidyTaskId()
/*     */   {
/*  32 */     return this.mTidyTaskId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mTidyTaskId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     TidyTaskPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof TidyTaskCK)) {
/*  56 */       other = ((TidyTaskCK)obj).getTidyTaskPK();
/*     */     }
/*  58 */     else if ((obj instanceof TidyTaskPK))
/*  59 */       other = (TidyTaskPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mTidyTaskId == other.mTidyTaskId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" TidyTaskId=");
/*  77 */     sb.append(this.mTidyTaskId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mTidyTaskId);
/*  89 */     return "TidyTaskPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static TidyTaskPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("TidyTaskPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'TidyTaskPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pTidyTaskId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new TidyTaskPK(pTidyTaskId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.admin.tidytask.TidyTaskPK
 * JD-Core Version:    0.6.0
 */