package com.cedar.cp.api.reset;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface ChallengeQuestionsProcess extends BusinessProcess
{
  EntityList getAllChallengeQuestions();

  EntityList getAllQuestionsAndAnswersByUserID(int paramInt);

  ChallengeQuestionEditorSession getChallengeQuestionEditorSession(Object paramObject) throws ValidationException;
  
  EntityList getChallengeWord(int userId);
  
  void setChallengeWord(int userKey, String words);
}