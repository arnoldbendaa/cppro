package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersLinkCK;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersLinkPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class LoggedInUsersLinkRefImpl extends EntityRefImpl implements Serializable {

   public LoggedInUsersLinkRefImpl(LoggedInUsersLinkCK key, String narrative) {
      super(key, narrative);
   }

   public LoggedInUsersLinkRefImpl(LoggedInUsersLinkPK key, String narrative) {
      super(key, narrative);
   }

   public LoggedInUsersLinkPK getLoggedInUsersLinkPK() {
      return this.mKey instanceof LoggedInUsersLinkCK?((LoggedInUsersLinkCK)this.mKey).getLoggedInUsersLinkPK():(LoggedInUsersLinkPK)this.mKey;
   }
}
