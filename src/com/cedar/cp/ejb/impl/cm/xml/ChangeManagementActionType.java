/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ @XmlType(name="changeManagementActionType")
/*    */ @XmlEnum
/*    */ public enum ChangeManagementActionType
/*    */ {
/* 36 */   AMEND("amend"), 
/*    */ 
/* 38 */   IMPORT("import"), 
/*    */ 
/* 40 */   EXPORT_VIEWS("export-views");
/*    */ 
/*    */   private final String value;
/*    */ 
/* 45 */   private ChangeManagementActionType(String v) { this.value = v; }
/*    */ 
/*    */   public String value()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public static ChangeManagementActionType fromValue(String v) {
/* 53 */     for (ChangeManagementActionType c : values()) {
/* 54 */       if (c.value.equals(v)) {
/* 55 */         return c;
/*    */       }
/*    */     }
/* 58 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.ChangeManagementActionType
 * JD-Core Version:    0.6.0
 */