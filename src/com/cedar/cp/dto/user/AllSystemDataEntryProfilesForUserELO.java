package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllSystemDataEntryProfilesForUserELO extends AbstractELO
  implements Serializable
{
  private static final String[] mEntity = { "DataEntryProfile", "User", "DataEntryProfileHistory", "XmlForm" };
  private transient DataEntryProfileRef mDataEntryProfileEntityRef;
  private transient UserRef mUserEntityRef;
  private transient String mDescription;
  private transient int mXmlformId;

  public AllSystemDataEntryProfilesForUserELO()
  {
    super(new String[] { "DataEntryProfile", "User", "Description", "XmlformId" });
  }

  public void add(DataEntryProfileRef eRefDataEntryProfile, UserRef eRefUser, String col1, int col2)
  {
    List l = new ArrayList();
    l.add(eRefDataEntryProfile);
    l.add(eRefUser);
    l.add(col1);
    l.add(new Integer(col2));
    mCollection.add(l);
  }

  public void next()
  {
    if (mIterator == null) {
      reset();
    }
    mCurrRowIndex += 1;
    List l = (List)mIterator.next();
    int index = 0;
    mDataEntryProfileEntityRef = ((DataEntryProfileRef)l.get(index++));
    mUserEntityRef = ((UserRef)l.get(index++));
    mDescription = ((String)l.get(index++));
    mXmlformId = ((Integer)l.get(index++)).intValue();
  }

  public DataEntryProfileRef getDataEntryProfileEntityRef()
  {
    return mDataEntryProfileEntityRef;
  }

  public UserRef getUserEntityRef()
  {
    return mUserEntityRef;
  }

  public String getDescription()
  {
    return mDescription;
  }

  public int getXmlformId()
  {
    return mXmlformId;
  }

  public boolean includesEntity(String name)
  {
    for (int i = 0; i < mEntity.length; i++)
      if (name.equals(mEntity[i]))
        return true;
    return false;
  }
}