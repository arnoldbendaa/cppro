package com.cedar.cp.utc.struts.message;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class MessageListForm extends CPForm
{
  private List messages = null;
  private int mSize;
  private int mFrom;
  private int mDisplayMessages;

  public List getMessages() {
    return messages;
  }

  public void setMessages(List messages) {
    this.messages = messages;
    setSize(messages.size());
  }

  public int getSize() {
    return mSize;
  }

  public void setSize(int size) {
    mSize = size;
  }

  public int getFrom() {
    return mFrom;
  }

  public void setFrom(int from) {
    mFrom = from;
  }

  public int getDisplayMessages() {
    if (mDisplayMessages == 0)
      return 40;
    return mDisplayMessages;
  }

  public void setDisplayMessages(int displayMessages) {
    mDisplayMessages = displayMessages;
  }
}