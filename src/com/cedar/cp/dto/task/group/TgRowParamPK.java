/*     */ package com.cedar.cp.dto.task.group;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TgRowParamPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mTgRowParamId;
/*     */ 
/*     */   public TgRowParamPK(int newTgRowParamId)
/*     */   {
/*  23 */     this.mTgRowParamId = newTgRowParamId;
/*     */   }
/*     */ 
/*     */   public int getTgRowParamId()
/*     */   {
/*  32 */     return this.mTgRowParamId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mTgRowParamId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     TgRowParamPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof TgRowParamCK)) {
/*  56 */       other = ((TgRowParamCK)obj).getTgRowParamPK();
/*     */     }
/*  58 */     else if ((obj instanceof TgRowParamPK))
/*  59 */       other = (TgRowParamPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mTgRowParamId == other.mTgRowParamId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" TgRowParamId=");
/*  77 */     sb.append(this.mTgRowParamId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mTgRowParamId);
/*  89 */     return "TgRowParamPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static TgRowParamPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("TgRowParamPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'TgRowParamPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pTgRowParamId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new TgRowParamPK(pTgRowParamId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.group.TgRowParamPK
 * JD-Core Version:    0.6.0
 */