package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.Serializable;

public interface MappedCalendarElement extends Serializable, XMLWritable, Comparable {

   Object getKey();

   Integer getPeriod();

   EntityRef getCalendarElementRef();
}
