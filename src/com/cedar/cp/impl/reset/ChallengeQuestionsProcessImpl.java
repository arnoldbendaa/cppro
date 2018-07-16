package com.cedar.cp.impl.reset;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.reset.ChallengeQuestionEditorSession;
import com.cedar.cp.api.reset.ChallengeQuestionsProcess;
import com.cedar.cp.ejb.api.reset.ChallengeQuestionEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ChallengeQuestionsProcessImpl extends BusinessProcessImpl implements ChallengeQuestionsProcess {
	private Log mLog = new Log(getClass());

	public ChallengeQuestionsProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;

		ChallengeQuestionEditorSessionServer es = new ChallengeQuestionEditorSessionServer(getConnection());
		try {
			es.delete(primaryKey);
		} catch (ValidationException e) {
			throw e;
		} catch (CPException e) {
			throw new RuntimeException("can't delete " + primaryKey, e);
		}

		if (timer != null)
			timer.logDebug("deleteObject", primaryKey);
	}

	public ChallengeQuestionEditorSession getChallengeQuestionEditorSession(Object key) throws ValidationException {
		ChallengeQuestionEditorSessionImpl sess = new ChallengeQuestionEditorSessionImpl(this, key);
		mActiveSessions.add(sess);
		return sess;
	}

	public EntityList getAllChallengeQuestions() {
		try {
			return getConnection().getListHelper().getAllChallengeQuestions();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get AllChallengeQuestions", e);
		}
	}

	public EntityList getAllQuestionsAndAnswersByUserID(int param1) {
		try {
			return getConnection().getListHelper().getAllQuestionsAndAnswersByUserID(param1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get AllQuestionsAndAnswersByUserID", e);
		}
	}
	
	@Override
	public EntityList getChallengeWord(int userId) {
		try {
			return getConnection().getListHelper().getChallengeWord(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get ChallengeWord", e);
		}
	}
	
	public void setChallengeWord(int userId, String challengeWord) {
		try {
			getConnection().getListHelper().setChallengeWord(userId, challengeWord);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't set ChallengeWord", e);
		}
	}

	public String getProcessName() {
		String ret = "Processing ChallengeQuestion";

		return ret;
	}

	protected int getProcessID() {
		return 103;
	}

}