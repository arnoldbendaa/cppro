/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="changeManagementEventType")
/*    */ @XmlEnum
/*    */ public enum ChangeManagementEventType
/*    */ {
/* 38 */   DIMENSION("dimension"), 
/*    */ 
/* 40 */   CALENDAR("calendar"), 
/*    */ 
/* 42 */   FINANCIAL_VALUES("financial-values"), 
/*    */ 
/* 44 */   IMPORT_VALUES("import-values"), 
/*    */ 
/* 46 */   FINANCE_CUBE("finance-cube");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 51 */   private ChangeManagementEventType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 55 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static ChangeManagementEventType fromValue(String v) {
/* 59 */     for (ChangeManagementEventType c : values()) {
/* 60 */       if (c.value.equals(v)) {
/* 61 */         return c;
/*    */       }
/*    */     }
/* 64 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEventType
 * JD-Core Version:    0.6.0
 */