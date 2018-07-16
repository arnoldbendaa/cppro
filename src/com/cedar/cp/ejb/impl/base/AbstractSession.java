// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.util.Cryptography;

import cppro.utils.DBUtils;

import java.sql.Connection;
import javax.ejb.SessionBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class AbstractSession implements SessionBean {

   private int mUserId;
   private transient UserEVO mUserEVO;
   private transient InitialContext mInitialContext;
   protected transient CPConnection mCPConnection;


   protected int getUserId() {
      return this.mUserId;
   }

   protected void setUserId(int userId) {
      if(userId != this.mUserId) {
         this.mUserId = userId;
         this.mUserEVO = null;
         this.closeCPConnection();
      }

   }

   protected InitialContext getInitialContext() throws NamingException {
      if(this.mInitialContext == null) {
         this.mInitialContext = new InitialContext();
      }

      return this.mInitialContext;
   }

   protected CPConnection getCPConnection() {
      if(this.mCPConnection == null) {
//         try {
//            UserEVO e = this.getUserEVO();
//            String connectionURL = SystemPropertyHelper.queryStringSystemProperty((Connection)null, "WEB: Connection URL", (String)null);
//            String password = e.getPasswordBytes() != null && e.getPasswordBytes().length() != 0?Cryptography.decrypt(e.getPasswordBytes(), "fc30"):null;
//            this.mCPConnection = DriverManager.getConnection(connectionURL, e.getName(), password, true, false, ConnectionContext.SERVER_SESSION);
//         } catch (Exception var4) {
//            throw new CPException(var4.getMessage(), var4);
//         }
    	  CPContext context = DBUtils.mContext;
    	  this.mCPConnection = context.getCPConnection();
      }

      return this.mCPConnection;
   }
   

   public void setmCPConnection(CPConnection mCPConnection) {
		this.mCPConnection = mCPConnection;
	}

protected UserEVO getUserEVO() throws ValidationException {
      if(this.mUserEVO == null) {
         this.mUserEVO = (new UserDAO()).getDetails(new UserPK(this.getUserId()), "");
      }

      return this.mUserEVO;
   }

   protected void closeCPConnection() {
      if(this.mCPConnection != null) {
         this.mCPConnection.close();
         this.mCPConnection = null;
      }

   }
}
