package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.util.db.DBAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResetAccessor extends DBAccessor
{
  private int mProcessing;
  private UserResetDTO mReset;
  private List mChallenges;
  private static String SQL_USER_DETS = "select USER_ID, NAME, E_MAIL_ADDRESS, RESET_STRIKES, USER_DISABLED from USR ";
  private static String SQL_WHERE_NAME = "where NAME = ?";
  private static String SQL_WHERE_EMAIL = "where E_MAIL_ADDRESS = ?";

  private static String SQL_GET_CHALLENGES = "select * from CHALLENGE_QUESTION where USER_ID = ?";
  private static String SQL_GET_CHALLENGES_NO_ANSWERS = "select USER_ID, QUESTION_TEXT,NULL as QUESTION_ANSWER from CHALLENGE_QUESTION where USER_ID = ?";

  private static String SQL_UPDATE_USER_LINK = "insert into USER_RESET_LINK (USER_ID, PWD_LINK) values (?,?)";
  private static String SQL_GET_UPDATE_LINK = "select U.USER_ID, U.NAME, RL.PWD_LINK from USER_RESET_LINK RL, USR U where U.USER_ID = RL.USER_ID and RL.PWD_LINK = ?";
  private static String SQL_DELETE_PASSWD_LINK = "delete from USER_RESET_LINK where PWD_LINK = ?";

  private static String SQL_UPDATE_USER_STRIKE = "update USR set RESET_STRIKES = ? where user_id = ?";
  private static String SQL_DISABLE_USER = "update USR set USER_DISABLED = 'Y' where user_id = ?";
  
  private static String SQL_GET_MEMORABLE_WORD = "select USER_ID, 'HIDDEN_2ND_PASSWORD', WORD from CHALLENGE_WORD where USER_ID = ?";

  public ResetAccessor()
  {
    mProcessing = 0;
  }

  protected void processResultSet(ResultSet rs)
    throws SQLException
  {
    int rowCount = 0;
    if (mProcessing == 1)
    {
      mReset = new UserResetDTO();
      while ((rs != null) && (rs.next()))
      {
        rowCount++;
        mReset.setUserId(rs.getInt(1));
        mReset.setName(rs.getString(2));
        mReset.setEmail(rs.getString(3));
        mReset.setStrikes(rs.getInt(4));

        String dis = rs.getString(5);

        if ((dis == null) || (dis.trim().length() == 0))
        {
          mReset.setDisabled(false);
        }
        else if ((dis.equals("Y")) || (dis.equals("y")))
        {
          mReset.setDisabled(true);
        }

        mReset.setValid(rowCount == 1);
      }
    }
    if (mProcessing == 2)
    {
      mReset = new UserResetDTO();
      while ((rs != null) && (rs.next()))
      {
        rowCount++;
        mReset.setUserId(rs.getInt(1));
        mReset.setName(rs.getString(2));

        mReset.setValid(rowCount == 1);
      }

    }

    List questions = new ArrayList();
    while ((rs != null) && (rs.next()))
    {
      String text = rs.getString(2);
      String answer = rs.getString(3);
      ChallengeDTO dto = new ChallengeDTO(text, answer);
      questions.add(dto);
    }
    mChallenges = questions;
  }

  public void runUserDetsQueryName(String userName)
  {
    mProcessing = 1;
    StringBuilder sb = new StringBuilder();
    sb.append(SQL_USER_DETS).append(SQL_WHERE_NAME);
    executePreparedQuery(sb.toString(), new Object[] { userName });
  }

  public void runUserDetsQueryEMail(String eMail)
  {
    mProcessing = 1;
    StringBuilder sb = new StringBuilder();
    sb.append(SQL_USER_DETS).append(SQL_WHERE_EMAIL);
    executePreparedQuery(sb.toString(), new Object[] { eMail });
  }

  public UserResetDTO getReset()
  {
    return mReset;
  }

  public void runUserLink(String link)
  {
    mProcessing = 2;
    StringBuilder sb = new StringBuilder();
    sb.append(SQL_GET_UPDATE_LINK);
    executePreparedQuery(sb.toString(), new Object[] { link });

    List params = new ArrayList();
    params.add(link);
    executeUpdate(SQL_DELETE_PASSWD_LINK, params);
  }

  public List getUserChallenges(int userId, boolean hideAnswers)
  {
    mProcessing = 4;
    StringBuilder sb;
    if (hideAnswers)
    {
      sb = new StringBuilder(SQL_GET_CHALLENGES);
    }
    else
    {
      sb = new StringBuilder(SQL_GET_CHALLENGES_NO_ANSWERS);
    }

    executePreparedQuery(sb.toString(), new Object[] { Integer.valueOf(userId) });
    return mChallenges;
  }
  
  public List getUserChallengeWord(int userId) throws SQLException
  {    
	  	mProcessing = 4;
	    StringBuilder sb = new StringBuilder(SQL_GET_MEMORABLE_WORD);
	    executePreparedQuery(sb.toString(), new Object[] { Integer.valueOf(userId) });
	    return mChallenges;
  }

  public void updateUserLink(int id, String link)
  {
    List params = new ArrayList();
    params.add(Integer.valueOf(id));
    params.add(link);

    executeUpdate(SQL_UPDATE_USER_LINK, params);
  }

  public void updateStrike(int strike, int id)
  {
    List params = new ArrayList();
    params.add(Integer.valueOf(strike));
    params.add(Integer.valueOf(id));

    executeUpdate(SQL_UPDATE_USER_STRIKE, params);
  }

  public void diableUSER(int id)
  {
    List params = new ArrayList();
    params.add(Integer.valueOf(id));

    executeUpdate(SQL_DISABLE_USER, params);
  }
  class UserResetDTO { private int mUserId;
    private String mName;
    private String mEmail;
    private int mStrikes;
    private boolean mDisabled;
    private boolean mValid;

    UserResetDTO() {  } 
    public int getUserId() { return mUserId; }


    public void setUserId(int userId)
    {
      mUserId = userId;
    }

    public String getName()
    {
      return mName;
    }

    public void setName(String name)
    {
      mName = name;
    }

    public String getEmail()
    {
      return mEmail;
    }

    public void setEmail(String email)
    {
      mEmail = email;
    }

    public int getStrikes()
    {
      return mStrikes;
    }

    public void setStrikes(int strikes)
    {
      mStrikes = strikes;
    }

    public boolean isDisabled()
    {
      return mDisabled;
    }

    public void setDisabled(boolean disabled)
    {
      mDisabled = disabled;
    }

    public boolean isValid()
    {
      return mValid;
    }

    public void setValid(boolean valid)
    {
      mValid = valid;
    }
  }
}