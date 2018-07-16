/*    */ package com.cedar.cp.ejb.impl.cm.xml;
/*    */ 
/*    */ import javax.xml.bind.JAXBElement;
/*    */ import javax.xml.bind.annotation.XmlElementDecl;
/*    */ import javax.xml.bind.annotation.XmlRegistry;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ @XmlRegistry
/*    */ public class ObjectFactory
/*    */ {
/* 34 */   private static final QName _ChangeManagement_QNAME = new QName("", "change-management");
/*    */ 
/*    */   public HierarchyEvent createHierarchyEvent()
/*    */   {
/* 48 */     return new HierarchyEvent();
/*    */   }
/*    */ 
/*    */   public ChangeManagementType createChangeManagementType()
/*    */   {
/* 56 */     return new ChangeManagementType();
/*    */   }
/*    */ 
/*    */   public CubeEvent createCubeEvent()
/*    */   {
/* 64 */     return new CubeEvent();
/*    */   }
/*    */ 
/*    */   public YearSpecType createYearSpecType()
/*    */   {
/* 72 */     return new YearSpecType();
/*    */   }
/*    */ 
/*    */   public ChangeManagementEvent createChangeManagementEvent()
/*    */   {
/* 80 */     return new ChangeManagementEvent();
/*    */   }
/*    */ 
/*    */   public DimensionEvent createDimensionEvent()
/*    */   {
/* 88 */     return new DimensionEvent();
/*    */   }
/*    */ 
/*    */   @XmlElementDecl(namespace="", name="change-management")
/*    */   public JAXBElement<ChangeManagementType> createChangeManagement(ChangeManagementType value)
/*    */   {
/* 97 */     return new JAXBElement(_ChangeManagement_QNAME, ChangeManagementType.class, null, value);
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.ObjectFactory
 * JD-Core Version:    0.6.0
 */