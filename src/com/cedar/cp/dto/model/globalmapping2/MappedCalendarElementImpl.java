package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarElement;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class MappedCalendarElementImpl implements MappedCalendarElement, Cloneable, Serializable, Comparable {

   private EntityRef mCalendarElementRef;
   private Integer mPeriod;
   private Object mKey;


   public Integer getPeriod() {
      return this.mPeriod;
   }

   public void setPeriod(Integer period) {
      this.mPeriod = period;
   }

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public EntityRef getCalendarElementRef() {
      return this.mCalendarElementRef;
   }

   public void setCalendarElementRef(EntityRef calRef) {
      this.mCalendarElementRef = calRef;
   }

   public Object clone() throws CloneNotSupportedException {
      MappedCalendarElementImpl clone = (MappedCalendarElementImpl)super.clone();
      clone.mCalendarElementRef = this.mCalendarElementRef;
      clone.mPeriod = this.mPeriod;
      clone.mKey = this.mKey;
      return clone;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<mappedCalendarElement ");
      XmlUtils.outputAttribute(out, "calendarElementRef", this.mCalendarElementRef);
      XmlUtils.outputAttribute(out, "period", this.mPeriod);
      out.write(" />\n");
   }

   public int compareTo(Object o) {
      if(o instanceof MappedCalendarElementImpl) {
         MappedCalendarElementImpl other = (MappedCalendarElementImpl)o;
         return this.mPeriod.compareTo(other.mPeriod);
      } else {
         return 0;
      }
   }
}
