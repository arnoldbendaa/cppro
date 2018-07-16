package com.cedar.cp.api.model.globalmapping2;

import com.cedar.cp.api.base.EntityRef;

public interface MappedDimensionElementRef extends EntityRef {

   int MAPPING_TYPE_SINGLE = 0;
   int MAPPING_TYPE_PREFIX = 1;
   int MAPPING_TYPE_RANGE = 2;
   int MAPPING_TYPE_HIERARCHY = 3;

}
