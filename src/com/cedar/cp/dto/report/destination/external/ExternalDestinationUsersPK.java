/*     */ package com.cedar.cp.dto.report.destination.external;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExternalDestinationUsersPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mExternalDestinationId;
/*     */   int mExternalDestinationUsersId;
/*     */ 
/*     */   public ExternalDestinationUsersPK(int newExternalDestinationId, int newExternalDestinationUsersId)
/*     */   {
/*  24 */     this.mExternalDestinationId = newExternalDestinationId;
/*  25 */     this.mExternalDestinationUsersId = newExternalDestinationUsersId;
/*     */   }
/*     */ 
/*     */   public int getExternalDestinationId()
/*     */   {
/*  34 */     return this.mExternalDestinationId;
/*     */   }
/*     */ 
/*     */   public int getExternalDestinationUsersId()
/*     */   {
/*  41 */     return this.mExternalDestinationUsersId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mExternalDestinationId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mExternalDestinationUsersId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ExternalDestinationUsersPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ExternalDestinationUsersCK)) {
/*  66 */       other = ((ExternalDestinationUsersCK)obj).getExternalDestinationUsersPK();
/*     */     }
/*  68 */     else if ((obj instanceof ExternalDestinationUsersPK))
/*  69 */       other = (ExternalDestinationUsersPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mExternalDestinationId == other.mExternalDestinationId);
/*  76 */     eq = (eq) && (this.mExternalDestinationUsersId == other.mExternalDestinationUsersId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ExternalDestinationId=");
/*  88 */     sb.append(this.mExternalDestinationId);
/*  89 */     sb.append(",ExternalDestinationUsersId=");
/*  90 */     sb.append(this.mExternalDestinationUsersId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mExternalDestinationId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mExternalDestinationUsersId);
/* 104 */     return "ExternalDestinationUsersPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExternalDestinationUsersPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ExternalDestinationUsersPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExternalDestinationUsersPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pExternalDestinationId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pExternalDestinationUsersId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ExternalDestinationUsersPK(pExternalDestinationId, pExternalDestinationUsersId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.external.ExternalDestinationUsersPK
 * JD-Core Version:    0.6.0
 */