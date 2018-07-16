/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementAuthorisersPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mAuthPointId;
/*     */   int mUserId;
/*     */ 
/*     */   public VirementAuthorisersPK(int newAuthPointId, int newUserId)
/*     */   {
/*  24 */     this.mAuthPointId = newAuthPointId;
/*  25 */     this.mUserId = newUserId;
/*     */   }
/*     */ 
/*     */   public int getAuthPointId()
/*     */   {
/*  34 */     return this.mAuthPointId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  41 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mAuthPointId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     VirementAuthorisersPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof VirementAuthorisersCK)) {
/*  66 */       other = ((VirementAuthorisersCK)obj).getVirementAuthorisersPK();
/*     */     }
/*  68 */     else if ((obj instanceof VirementAuthorisersPK))
/*  69 */       other = (VirementAuthorisersPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mAuthPointId == other.mAuthPointId);
/*  76 */     eq = (eq) && (this.mUserId == other.mUserId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" AuthPointId=");
/*  88 */     sb.append(this.mAuthPointId);
/*  89 */     sb.append(",UserId=");
/*  90 */     sb.append(this.mUserId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mAuthPointId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mUserId);
/* 104 */     return "VirementAuthorisersPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementAuthorisersPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("VirementAuthorisersPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementAuthorisersPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pAuthPointId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new VirementAuthorisersPK(pAuthPointId, pUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementAuthorisersPK
 * JD-Core Version:    0.6.0
 */