/*     */ package com.cedar.cp.ejb.impl.cm.xml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="dimensionEvent", propOrder={"event", "yearSpec"})
/*     */ public class DimensionEvent
/*     */ {
/*     */   protected List<HierarchyEvent> event;
/*     */ 
/*     */   @XmlElement(name="year-spec")
/*     */   protected YearSpecType yearSpec;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected DimensionActionType action;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected DimensionEventType type;
/*     */ 
/*     */   @XmlAttribute(name="vis_id")
/*     */   protected String visId;
/*     */ 
/*     */   @XmlAttribute(name="orig_vis_id")
/*     */   protected String origVisId;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected String description;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected NullableBoolean disabled;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected NullableBoolean credit;
/*     */ 
/*     */   @XmlAttribute(name="aug_credit")
/*     */   protected CrDrOverrideType augCredit;
/*     */ 
/*     */   @XmlAttribute(name="not_plannable")
/*     */   protected NullableBoolean notPlannable;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected Boolean augment;
/*     */ 
/*     */   @XmlAttribute(name="null")
/*     */   protected NullableBoolean _null;
/*     */ 
/*     */   public List<HierarchyEvent> getEvent()
/*     */   {
/* 107 */     if (this.event == null) {
/* 108 */       this.event = new ArrayList();
/*     */     }
/* 110 */     return this.event;
/*     */   }
/*     */ 
/*     */   public YearSpecType getYearSpec()
/*     */   {
/* 122 */     return this.yearSpec;
/*     */   }
/*     */ 
/*     */   public void setYearSpec(YearSpecType value)
/*     */   {
/* 134 */     this.yearSpec = value;
/*     */   }
/*     */ 
/*     */   public DimensionActionType getAction()
/*     */   {
/* 146 */     return this.action;
/*     */   }
/*     */ 
/*     */   public void setAction(DimensionActionType value)
/*     */   {
/* 158 */     this.action = value;
/*     */   }
/*     */ 
/*     */   public DimensionEventType getType()
/*     */   {
/* 170 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(DimensionEventType value)
/*     */   {
/* 182 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getVisId()
/*     */   {
/* 194 */     return this.visId;
/*     */   }
/*     */ 
/*     */   public void setVisId(String value)
/*     */   {
/* 206 */     this.visId = value;
/*     */   }
/*     */ 
/*     */   public String getOrigVisId()
/*     */   {
/* 218 */     return this.origVisId;
/*     */   }
/*     */ 
/*     */   public void setOrigVisId(String value)
/*     */   {
/* 230 */     this.origVisId = value;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 242 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String value)
/*     */   {
/* 254 */     this.description = value;
/*     */   }
/*     */ 
/*     */   public NullableBoolean getDisabled()
/*     */   {
/* 266 */     return this.disabled;
/*     */   }
/*     */ 
/*     */   public void setDisabled(NullableBoolean value)
/*     */   {
/* 278 */     this.disabled = value;
/*     */   }
/*     */ 
/*     */   public NullableBoolean getCredit()
/*     */   {
/* 290 */     return this.credit;
/*     */   }
/*     */ 
/*     */   public void setCredit(NullableBoolean value)
/*     */   {
/* 302 */     this.credit = value;
/*     */   }
/*     */ 
/*     */   public CrDrOverrideType getAugCredit()
/*     */   {
/* 314 */     return this.augCredit;
/*     */   }
/*     */ 
/*     */   public void setAugCredit(CrDrOverrideType value)
/*     */   {
/* 326 */     this.augCredit = value;
/*     */   }
/*     */ 
/*     */   public NullableBoolean getNotPlannable()
/*     */   {
/* 338 */     return this.notPlannable;
/*     */   }
/*     */ 
/*     */   public void setNotPlannable(NullableBoolean value)
/*     */   {
/* 350 */     this.notPlannable = value;
/*     */   }
/*     */ 
/*     */   public Boolean isAugment()
/*     */   {
/* 362 */     return this.augment;
/*     */   }
/*     */ 
/*     */   public void setAugment(Boolean value)
/*     */   {
/* 374 */     this.augment = value;
/*     */   }
/*     */ 
/*     */   public NullableBoolean getNull()
/*     */   {
/* 386 */     return this._null;
/*     */   }
/*     */ 
/*     */   public void setNull(NullableBoolean value)
/*     */   {
/* 398 */     this._null = value;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.DimensionEvent
 * JD-Core Version:    0.6.0
 */