package com.cedar.cp.utc.struts.admin.reset;

public class ChallengeDTO
{
  private String mQuestion;
  private String mAnswer;

  public ChallengeDTO()
  {
  }

  public ChallengeDTO(String question, String answer)
  {
    mQuestion = question;
    mAnswer = answer;
  }

  public String getAnswer() {
    return mAnswer;
  }

  public void setAnswer(String answer) {
    mAnswer = answer;
  }

  public String getQuestion() {
    return mQuestion;
  }

  public void setQuestion(String question) {
    mQuestion = question;
  }
}