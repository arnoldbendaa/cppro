package com.cedar.cp.api.reset;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.EntityList;

public abstract interface ChallengeQuestionEditorSession extends BusinessSession
{
  public abstract ChallengeQuestionEditor getChallengeQuestionEditor();

  public abstract EntityList getAvailableUsers();

  public abstract EntityList getOwnershipRefs();
}