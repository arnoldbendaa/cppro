/*     */ package com.cedar.cp.dto.admin.tidytask;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class TidyTaskLinkPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mTidyTaskId;
/*     */   int mTidyTaskLinkId;
/*     */ 
/*     */   public TidyTaskLinkPK(int newTidyTaskId, int newTidyTaskLinkId)
/*     */   {
/*  24 */     this.mTidyTaskId = newTidyTaskId;
/*  25 */     this.mTidyTaskLinkId = newTidyTaskLinkId;
/*     */   }
/*     */ 
/*     */   public int getTidyTaskId()
/*     */   {
/*  34 */     return this.mTidyTaskId;
/*     */   }
/*     */ 
/*     */   public int getTidyTaskLinkId()
/*     */   {
/*  41 */     return this.mTidyTaskLinkId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mTidyTaskId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mTidyTaskLinkId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     TidyTaskLinkPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof TidyTaskLinkCK)) {
/*  66 */       other = ((TidyTaskLinkCK)obj).getTidyTaskLinkPK();
/*     */     }
/*  68 */     else if ((obj instanceof TidyTaskLinkPK))
/*  69 */       other = (TidyTaskLinkPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mTidyTaskId == other.mTidyTaskId);
/*  76 */     eq = (eq) && (this.mTidyTaskLinkId == other.mTidyTaskLinkId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" TidyTaskId=");
/*  88 */     sb.append(this.mTidyTaskId);
/*  89 */     sb.append(",TidyTaskLinkId=");
/*  90 */     sb.append(this.mTidyTaskLinkId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mTidyTaskId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mTidyTaskLinkId);
/* 104 */     return "TidyTaskLinkPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static TidyTaskLinkPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("TidyTaskLinkPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'TidyTaskLinkPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pTidyTaskId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pTidyTaskLinkId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new TidyTaskLinkPK(pTidyTaskId, pTidyTaskLinkId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.admin.tidytask.TidyTaskLinkPK
 * JD-Core Version:    0.6.0
 */