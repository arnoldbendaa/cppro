/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysCalElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 165 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mCompanyVisId;
/*     */   String mCalendarYearVisId;
/*     */   String mCalElementVisId;
/*     */ 
/*     */   public ExtSysCalElementPK(int newExternalSystemId, String newCompanyVisId, String newCalendarYearVisId, String newCalElementVisId)
/*     */   {
/*  26 */     this.mExternalSystemId = newExternalSystemId;
/*  27 */     this.mCompanyVisId = newCompanyVisId;
/*  28 */     this.mCalendarYearVisId = newCalendarYearVisId;
/*  29 */     this.mCalElementVisId = newCalElementVisId;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  38 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public String getCompanyVisId()
/*     */   {
/*  45 */     return this.mCompanyVisId;
/*     */   }
/*     */ 
/*     */   public String getCalendarYearVisId()
/*     */   {
/*  52 */     return this.mCalendarYearVisId;
/*     */   }
/*     */ 
/*     */   public String getCalElementVisId()
/*     */   {
/*  59 */     return this.mCalElementVisId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  67 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  69 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*  70 */       this.mHashCode += this.mCompanyVisId.hashCode();
/*  71 */       this.mHashCode += this.mCalendarYearVisId.hashCode();
/*  72 */       this.mHashCode += this.mCalElementVisId.hashCode();
/*     */     }
/*     */ 
/*  75 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  83 */     ExtSysCalElementPK other = null;
/*     */ 
/*  85 */     if ((obj instanceof ExtSysCalElementCK)) {
/*  86 */       other = ((ExtSysCalElementCK)obj).getExtSysCalElementPK();
/*     */     }
/*  88 */     else if ((obj instanceof ExtSysCalElementPK))
/*  89 */       other = (ExtSysCalElementPK)obj;
/*     */     else {
/*  91 */       return false;
/*     */     }
/*  93 */     boolean eq = true;
/*     */ 
/*  95 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/*  96 */     eq = (eq) && (this.mCompanyVisId.equals(other.mCompanyVisId));
/*  97 */     eq = (eq) && (this.mCalendarYearVisId.equals(other.mCalendarYearVisId));
/*  98 */     eq = (eq) && (this.mCalElementVisId.equals(other.mCalElementVisId));
/*     */ 
/* 100 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 108 */     StringBuffer sb = new StringBuffer();
/* 109 */     sb.append(" ExternalSystemId=");
/* 110 */     sb.append(this.mExternalSystemId);
/* 111 */     sb.append(",CompanyVisId=");
/* 112 */     sb.append(this.mCompanyVisId);
/* 113 */     sb.append(",CalendarYearVisId=");
/* 114 */     sb.append(this.mCalendarYearVisId);
/* 115 */     sb.append(",CalElementVisId=");
/* 116 */     sb.append(this.mCalElementVisId);
/* 117 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 125 */     StringBuffer sb = new StringBuffer();
/* 126 */     sb.append(" ");
/* 127 */     sb.append(this.mExternalSystemId);
/* 128 */     sb.append(",");
/* 129 */     sb.append(this.mCompanyVisId);
/* 130 */     sb.append(",");
/* 131 */     sb.append(this.mCalendarYearVisId);
/* 132 */     sb.append(",");
/* 133 */     sb.append(this.mCalElementVisId);
/* 134 */     return "ExtSysCalElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysCalElementPK getKeyFromTokens(String extKey)
/*     */   {
/* 139 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 141 */     if (extValues.length != 2) {
/* 142 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 144 */     if (!extValues[0].equals("ExtSysCalElementPK")) {
/* 145 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysCalElementPK|'");
/*     */     }
/* 147 */     extValues = extValues[1].split(",");
/*     */ 
/* 149 */     int i = 0;
/* 150 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 151 */     String pCompanyVisId = new String(extValues[(i++)]);
/* 152 */     String pCalendarYearVisId = new String(extValues[(i++)]);
/* 153 */     String pCalElementVisId = new String(extValues[(i++)]);
/* 154 */     return new ExtSysCalElementPK(pExternalSystemId, pCompanyVisId, pCalendarYearVisId, pCalElementVisId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysCalElementPK
 * JD-Core Version:    0.6.0
 */