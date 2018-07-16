/*     */ package com.cedar.cp.dto.task.group;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TgRowPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mTgRowId;
/*     */ 
/*     */   public TgRowPK(int newTgRowId)
/*     */   {
/*  23 */     this.mTgRowId = newTgRowId;
/*     */   }
/*     */ 
/*     */   public int getTgRowId()
/*     */   {
/*  32 */     return this.mTgRowId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mTgRowId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     TgRowPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof TgRowCK)) {
/*  56 */       other = ((TgRowCK)obj).getTgRowPK();
/*     */     }
/*  58 */     else if ((obj instanceof TgRowPK))
/*  59 */       other = (TgRowPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mTgRowId == other.mTgRowId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" TgRowId=");
/*  77 */     sb.append(this.mTgRowId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mTgRowId);
/*  89 */     return "TgRowPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static TgRowPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("TgRowPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'TgRowPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pTgRowId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new TgRowPK(pTgRowId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.task.group.TgRowPK
 * JD-Core Version:    0.6.0
 */