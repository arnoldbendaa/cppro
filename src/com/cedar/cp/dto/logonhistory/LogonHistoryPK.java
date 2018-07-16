/*     */ package com.cedar.cp.dto.logonhistory;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class LogonHistoryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mLogonHistoryId;
/*     */ 
/*     */   public LogonHistoryPK(int newLogonHistoryId)
/*     */   {
/*  23 */     this.mLogonHistoryId = newLogonHistoryId;
/*     */   }
/*     */ 
/*     */   public int getLogonHistoryId()
/*     */   {
/*  32 */     return this.mLogonHistoryId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mLogonHistoryId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     LogonHistoryPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof LogonHistoryCK)) {
/*  56 */       other = ((LogonHistoryCK)obj).getLogonHistoryPK();
/*     */     }
/*  58 */     else if ((obj instanceof LogonHistoryPK))
/*  59 */       other = (LogonHistoryPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mLogonHistoryId == other.mLogonHistoryId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" LogonHistoryId=");
/*  77 */     sb.append(this.mLogonHistoryId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mLogonHistoryId);
/*  89 */     return "LogonHistoryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static LogonHistoryPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("LogonHistoryPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'LogonHistoryPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pLogonHistoryId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new LogonHistoryPK(pLogonHistoryId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.logonhistory.LogonHistoryPK
 * JD-Core Version:    0.6.0
 */