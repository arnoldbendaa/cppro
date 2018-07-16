/*     */ package com.cedar.cp.dto.passwordhistory;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PasswordHistoryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mPasswordHistoryId;
/*     */ 
/*     */   public PasswordHistoryPK(int newPasswordHistoryId)
/*     */   {
/*  23 */     this.mPasswordHistoryId = newPasswordHistoryId;
/*     */   }
/*     */ 
/*     */   public int getPasswordHistoryId()
/*     */   {
/*  32 */     return this.mPasswordHistoryId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mPasswordHistoryId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     PasswordHistoryPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof PasswordHistoryCK)) {
/*  56 */       other = ((PasswordHistoryCK)obj).getPasswordHistoryPK();
/*     */     }
/*  58 */     else if ((obj instanceof PasswordHistoryPK))
/*  59 */       other = (PasswordHistoryPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mPasswordHistoryId == other.mPasswordHistoryId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" PasswordHistoryId=");
/*  77 */     sb.append(this.mPasswordHistoryId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mPasswordHistoryId);
/*  89 */     return "PasswordHistoryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static PasswordHistoryPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("PasswordHistoryPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'PasswordHistoryPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pPasswordHistoryId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new PasswordHistoryPK(pPasswordHistoryId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.passwordhistory.PasswordHistoryPK
 * JD-Core Version:    0.6.0
 */