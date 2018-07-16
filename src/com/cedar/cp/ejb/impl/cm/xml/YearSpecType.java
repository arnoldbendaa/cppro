/*     */ package com.cedar.cp.ejb.impl.cm.xml;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="yearSpecType")
/*     */ public class YearSpecType
/*     */ {
/*     */ 
/*     */   @XmlAttribute(required=true)
/*     */   protected int year;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean opening;
/*     */ 
/*     */   @XmlAttribute(name="half_year")
/*     */   protected Boolean halfYear;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean quarter;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean month;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean week;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean day;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean adjustment;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean period13;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean period14;
/*     */ 
/*     */   public int getYear()
/*     */   {
/*  73 */     return this.year;
/*     */   }
/*     */ 
/*     */   public void setYear(int value)
/*     */   {
/*  81 */     this.year = value;
/*     */   }
/*     */ 
/*     */   public boolean isOpening()
/*     */   {
/*  93 */     if (this.opening == null) {
/*  94 */       return false;
/*     */     }
/*  96 */     return this.opening.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setOpening(Boolean value)
/*     */   {
/* 109 */     this.opening = value;
/*     */   }
/*     */ 
/*     */   public boolean isHalfYear()
/*     */   {
/* 121 */     if (this.halfYear == null) {
/* 122 */       return false;
/*     */     }
/* 124 */     return this.halfYear.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setHalfYear(Boolean value)
/*     */   {
/* 137 */     this.halfYear = value;
/*     */   }
/*     */ 
/*     */   public boolean isQuarter()
/*     */   {
/* 149 */     if (this.quarter == null) {
/* 150 */       return false;
/*     */     }
/* 152 */     return this.quarter.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setQuarter(Boolean value)
/*     */   {
/* 165 */     this.quarter = value;
/*     */   }
/*     */ 
/*     */   public boolean isMonth()
/*     */   {
/* 177 */     if (this.month == null) {
/* 178 */       return false;
/*     */     }
/* 180 */     return this.month.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setMonth(Boolean value)
/*     */   {
/* 193 */     this.month = value;
/*     */   }
/*     */ 
/*     */   public boolean isWeek()
/*     */   {
/* 205 */     if (this.week == null) {
/* 206 */       return false;
/*     */     }
/* 208 */     return this.week.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setWeek(Boolean value)
/*     */   {
/* 221 */     this.week = value;
/*     */   }
/*     */ 
/*     */   public boolean isDay()
/*     */   {
/* 233 */     if (this.day == null) {
/* 234 */       return false;
/*     */     }
/* 236 */     return this.day.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setDay(Boolean value)
/*     */   {
/* 249 */     this.day = value;
/*     */   }
/*     */ 
/*     */   public boolean isAdjustment()
/*     */   {
/* 261 */     if (this.adjustment == null) {
/* 262 */       return false;
/*     */     }
/* 264 */     return this.adjustment.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setAdjustment(Boolean value)
/*     */   {
/* 277 */     this.adjustment = value;
/*     */   }
/*     */ 
/*     */   public boolean isPeriod13()
/*     */   {
/* 289 */     if (this.period13 == null) {
/* 290 */       return false;
/*     */     }
/* 292 */     return this.period13.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setPeriod13(Boolean value)
/*     */   {
/* 305 */     this.period13 = value;
/*     */   }
/*     */ 
/*     */   public boolean isPeriod14()
/*     */   {
/* 317 */     if (this.period14 == null) {
/* 318 */       return false;
/*     */     }
/* 320 */     return this.period14.booleanValue();
/*     */   }
/*     */ 
/*     */   public void setPeriod14(Boolean value)
/*     */   {
/* 333 */     this.period14 = value;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.YearSpecType
 * JD-Core Version:    0.6.0
 */