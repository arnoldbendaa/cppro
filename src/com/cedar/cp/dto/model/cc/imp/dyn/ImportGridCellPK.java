/*     */ package com.cedar.cp.dto.model.cc.imp.dyn;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ImportGridCellPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mGridId;
/*     */   int mRowNumber;
/*     */   int mColumnNumber;
/*     */ 
/*     */   public ImportGridCellPK(int newGridId, int newRowNumber, int newColumnNumber)
/*     */   {
/*  25 */     this.mGridId = newGridId;
/*  26 */     this.mRowNumber = newRowNumber;
/*  27 */     this.mColumnNumber = newColumnNumber;
/*     */   }
/*     */ 
/*     */   public int getGridId()
/*     */   {
/*  36 */     return this.mGridId;
/*     */   }
/*     */ 
/*     */   public int getRowNumber()
/*     */   {
/*  43 */     return this.mRowNumber;
/*     */   }
/*     */ 
/*     */   public int getColumnNumber()
/*     */   {
/*  50 */     return this.mColumnNumber;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mGridId).hashCode();
/*  61 */       this.mHashCode += String.valueOf(this.mRowNumber).hashCode();
/*  62 */       this.mHashCode += String.valueOf(this.mColumnNumber).hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     ImportGridCellPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof ImportGridCellCK)) {
/*  76 */       other = ((ImportGridCellCK)obj).getImportGridCellPK();
/*     */     }
/*  78 */     else if ((obj instanceof ImportGridCellPK))
/*  79 */       other = (ImportGridCellPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mGridId == other.mGridId);
/*  86 */     eq = (eq) && (this.mRowNumber == other.mRowNumber);
/*  87 */     eq = (eq) && (this.mColumnNumber == other.mColumnNumber);
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" GridId=");
/*  99 */     sb.append(this.mGridId);
/* 100 */     sb.append(",RowNumber=");
/* 101 */     sb.append(this.mRowNumber);
/* 102 */     sb.append(",ColumnNumber=");
/* 103 */     sb.append(this.mColumnNumber);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mGridId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mRowNumber);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mColumnNumber);
/* 119 */     return "ImportGridCellPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ImportGridCellPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("ImportGridCellPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ImportGridCellPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pGridId = new Integer(extValues[(i++)]).intValue();
/* 136 */     int pRowNumber = new Integer(extValues[(i++)]).intValue();
/* 137 */     int pColumnNumber = new Integer(extValues[(i++)]).intValue();
/* 138 */     return new ImportGridCellPK(pGridId, pRowNumber, pColumnNumber);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK
 * JD-Core Version:    0.6.0
 */