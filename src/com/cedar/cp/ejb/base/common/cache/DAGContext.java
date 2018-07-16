// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.common.cache;

import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.common.cache.DAGCache;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAG;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserDAG;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.io.Serializable;
import javax.naming.InitialContext;

public class DAGContext implements Serializable {

   private DAGCache mCache;
   private transient InitialContext mInitialContext;
   private transient ModelAccessor mModelAccessor;
   private transient DimensionAccessor mDimensionAccessor;
   private transient UserAccessor mUserAccessor;


   public DAGContext(InitialContext initialContext) {
      this.mInitialContext = initialContext;
      this.mCache = new DAGCache();
   }

   public DAGCache getCache() {
      return this.mCache;
   }

   public ModelAccessor getModelAccessor() {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.mInitialContext);
      }

      return this.mModelAccessor;
   }

   public DimensionAccessor getDimensionAccessor() {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.mInitialContext);
      }

      return this.mDimensionAccessor;
   }

   public UserAccessor getUserAccessor() {
      if(this.mUserAccessor == null) {
         this.mUserAccessor = new UserAccessor(this.getInitialContext());
      }

      return this.mUserAccessor;
   }

   public void setInitialContext(InitialContext initialContext) {
      this.mInitialContext = initialContext;
   }

   public DimensionDAG getDimensionDAG(DimensionPK key) throws Exception {
      DimensionDAG dag = (DimensionDAG)this.mCache.get(DimensionDAG.class, key);
      if(dag == null) {
         DimensionAccessor accessor = this.getDimensionAccessor();
         DimensionEVO dimEVO = accessor.getDetails(key, "<0><1><2><3><4><5><6><7><8>");
         dag = new DimensionDAG(this, dimEVO);
      }

      return dag;
   }

   public ModelDAG getModelDAG(ModelPK key) throws Exception {
      ModelDAG dag = (ModelDAG)this.mCache.get(ModelDAG.class, key);
      if(dag == null) {
         ModelAccessor accessor = this.getModelAccessor();
         ModelEVO modelEVO = accessor.getDetails(key, "<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48>");
         dag = new ModelDAG(this, modelEVO);
      }

      return dag;
   }

   public UserDAG getUser(UserPK userPK) throws Exception {
      UserDAG user = (UserDAG)this.mCache.get(UserDAG.class, userPK);
      if(user == null) {
         UserEVO userEvo = this.getUserAccessor().getDetails(userPK, "<0><1><2><3>");
         user = new UserDAG(this, userEvo);
      }

      return user;
   }

   public InitialContext getInitialContext() {
      return this.mInitialContext;
   }
}
