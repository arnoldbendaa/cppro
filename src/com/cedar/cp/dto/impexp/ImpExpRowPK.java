/*     */ package com.cedar.cp.dto.impexp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ImpExpRowPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mBatchId;
/*     */   int mRowNo;
/*     */ 
/*     */   public ImpExpRowPK(int newBatchId, int newRowNo)
/*     */   {
/*  24 */     this.mBatchId = newBatchId;
/*  25 */     this.mRowNo = newRowNo;
/*     */   }
/*     */ 
/*     */   public int getBatchId()
/*     */   {
/*  34 */     return this.mBatchId;
/*     */   }
/*     */ 
/*     */   public int getRowNo()
/*     */   {
/*  41 */     return this.mRowNo;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mBatchId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mRowNo).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ImpExpRowPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ImpExpRowCK)) {
/*  66 */       other = ((ImpExpRowCK)obj).getImpExpRowPK();
/*     */     }
/*  68 */     else if ((obj instanceof ImpExpRowPK))
/*  69 */       other = (ImpExpRowPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mBatchId == other.mBatchId);
/*  76 */     eq = (eq) && (this.mRowNo == other.mRowNo);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" BatchId=");
/*  88 */     sb.append(this.mBatchId);
/*  89 */     sb.append(",RowNo=");
/*  90 */     sb.append(this.mRowNo);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mBatchId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mRowNo);
/* 104 */     return "ImpExpRowPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ImpExpRowPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ImpExpRowPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ImpExpRowPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pBatchId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pRowNo = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ImpExpRowPK(pBatchId, pRowNo);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.impexp.ImpExpRowPK
 * JD-Core Version:    0.6.0
 */