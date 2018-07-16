/*     */ package com.cedar.cp.dto.cm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ChangeMgmtPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mChangeMgmtId;
/*     */ 
/*     */   public ChangeMgmtPK(int newChangeMgmtId)
/*     */   {
/*  23 */     this.mChangeMgmtId = newChangeMgmtId;
/*     */   }
/*     */ 
/*     */   public int getChangeMgmtId()
/*     */   {
/*  32 */     return this.mChangeMgmtId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mChangeMgmtId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ChangeMgmtPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ChangeMgmtCK)) {
/*  56 */       other = ((ChangeMgmtCK)obj).getChangeMgmtPK();
/*     */     }
/*  58 */     else if ((obj instanceof ChangeMgmtPK))
/*  59 */       other = (ChangeMgmtPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mChangeMgmtId == other.mChangeMgmtId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ChangeMgmtId=");
/*  77 */     sb.append(this.mChangeMgmtId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mChangeMgmtId);
/*  89 */     return "ChangeMgmtPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ChangeMgmtPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ChangeMgmtPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ChangeMgmtPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pChangeMgmtId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ChangeMgmtPK(pChangeMgmtId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.cm.ChangeMgmtPK
 * JD-Core Version:    0.6.0
 */