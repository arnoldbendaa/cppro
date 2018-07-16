package com.cedar.cp.dto.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarElement;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementImpl;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MappedCalendarYearImpl implements MappedCalendarYear, Cloneable, Serializable {

   private Object mKey;
   private int mYear;
   private List<MappedCalendarElement> mMappedCalendarElements;
   private String mCompanies;

   public MappedCalendarYearImpl(Object key, int year, List<MappedCalendarElement> mappedCalendarElements) {
      this.mKey = key;
      this.mYear = year;
      this.mMappedCalendarElements = mappedCalendarElements;
      this.mCompanies = "";
   }

   public MappedCalendarYearImpl(Object key, int year, List<MappedCalendarElement> mappedCalendarElements, String companies) {
	   this.mKey = key;
	   this.mYear = year;
	   this.mMappedCalendarElements = mappedCalendarElements;
	   this.mCompanies = companies;
   }
   
   public int getYear() {
      return this.mYear;
   }

   public List<MappedCalendarElement> getMappedCalendarElements() {
      return this.mMappedCalendarElements;
   }

   public void setYear(int mYear) {
      this.mYear = mYear;
   }

   public void setMappedCalendarElements(List<MappedCalendarElement> mappedCalendarElements) {
      this.mMappedCalendarElements = mappedCalendarElements;
   }

   public MappedCalendarElementImpl findMappedCalendarElement(Object key) {
      Iterator i$ = this.mMappedCalendarElements.iterator();

      MappedCalendarElement mci;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mci = (MappedCalendarElement)i$.next();
      } while(!mci.getKey().equals(key));

      return (MappedCalendarElementImpl)mci;
   }

   public MappedCalendarElementImpl findMappedCalendarElement(EntityRef calendarElementRef) {
      Iterator i$ = this.mMappedCalendarElements.iterator();

      MappedCalendarElement mci;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mci = (MappedCalendarElement)i$.next();
      } while(mci.getCalendarElementRef() == null || !mci.getCalendarElementRef().equals(calendarElementRef));

      return (MappedCalendarElementImpl)mci;
   }

   public boolean hasMappedElements() {
      if(this.mMappedCalendarElements != null) {
         Iterator i$ = this.mMappedCalendarElements.iterator();

         while(i$.hasNext()) {
            MappedCalendarElement mci = (MappedCalendarElement)i$.next();
            if(mci.getCalendarElementRef() != null && mci.getPeriod() != null) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasUnmappedElements() {
      if(this.mMappedCalendarElements == null) {
         return true;
      } else {
         Iterator i$ = this.mMappedCalendarElements.iterator();

         MappedCalendarElement mci;
         do {
            if(!i$.hasNext()) {
               return false;
            }

            mci = (MappedCalendarElement)i$.next();
         } while(mci.getCalendarElementRef() == null || mci.getPeriod() != null);

         return true;
      }
   }

   public MappedCalendarElementImpl findMappedCalendarElement(Integer period) {
      Iterator i$ = this.mMappedCalendarElements.iterator();

      MappedCalendarElement mci;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mci = (MappedCalendarElement)i$.next();
      } while((period != null || mci.getPeriod() != null) && (period == null || mci.getPeriod() == null || mci.getPeriod().intValue() != period.intValue()));

      return (MappedCalendarElementImpl)mci;
   }

   public Object clone() throws CloneNotSupportedException {
      MappedCalendarYearImpl clone = (MappedCalendarYearImpl)super.clone();
      clone.mYear = this.mYear;
      clone.mMappedCalendarElements = new ArrayList();
      if(this.mMappedCalendarElements != null) {
         Iterator i$ = this.mMappedCalendarElements.iterator();

         while(i$.hasNext()) {
            MappedCalendarElement mce = (MappedCalendarElement)i$.next();
            clone.mMappedCalendarElements.add((MappedCalendarElement)((MappedCalendarElementImpl)mce).clone());
         }
      }

      return clone;
   }

   public String toString() {
      return String.valueOf(this.mYear);
   }

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<mappedCalendarYear ");
      XmlUtils.outputAttribute(out, "year", Integer.valueOf(this.mYear));
      out.write(" >\n");
      Collections.sort(this.mMappedCalendarElements);
      if(this.mMappedCalendarElements != null) {
         XmlUtils.outputElement(out, "mappedCalendarElements", (List) this.mMappedCalendarElements);
      }

      out.write("</mappedCalendarYear>\n");
   }

	public String getCompanies() {
		return mCompanies;
	}
	
	public void setCompanies(String companies) {
		this.mCompanies = companies;
	}
}
