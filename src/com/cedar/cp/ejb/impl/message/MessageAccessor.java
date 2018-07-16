// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.message.AllMessagesELO;
import com.cedar.cp.dto.message.AllMessagesToUserELO;
import com.cedar.cp.dto.message.AttatchmentForMessageELO;
import com.cedar.cp.dto.message.InBoxForUserELO;
import com.cedar.cp.dto.message.MessageCK;
import com.cedar.cp.dto.message.MessageCountELO;
import com.cedar.cp.dto.message.MessageForIdELO;
import com.cedar.cp.dto.message.MessageForIdSentItemELO;
import com.cedar.cp.dto.message.MessageFromUserELO;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.dto.message.MessageToUserELO;
import com.cedar.cp.dto.message.SentItemsForUserELO;
import com.cedar.cp.dto.message.UnreadInBoxForUserELO;
import com.cedar.cp.ejb.impl.message.MessageAttatchDAO;
import com.cedar.cp.ejb.impl.message.MessageDAO;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import com.cedar.cp.ejb.impl.message.MessageLocal;
import com.cedar.cp.ejb.impl.message.MessageLocalHome;
import com.cedar.cp.ejb.impl.message.MessageUserDAO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageAccessor implements Serializable {

   private MessageLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_MESSAGE_USERS = "<0>";
   public static final String GET_MESSAGE_ATTATCHMENTS = "<1>";
   public static final String GET_ALL_DEPENDANTS = "<0><1>";
   public static MessageEEJB server = new MessageEEJB();

   public MessageAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private MessageLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (MessageLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/MessageLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up MessageLocalHome", var2);
      }
   }

   private MessageEEJB getLocal(MessagePK pk) throws Exception {
//      MessageLocal local = (MessageLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return server;
   }

   public MessageEVO create(MessageEVO evo) throws Exception {
//      MessageLocal local = this.getLocalHome().create(evo);
	   server.ejbCreate(evo);
      MessageEVO newevo = server.getDetails("<UseLoadedEVOs>");
      MessagePK pk = newevo.getPK();
      this.mLocals.put(pk, server);
      return newevo;
   }

   public void remove(MessagePK pk) throws Exception {
      this.getLocal(pk).ejbRemove();
   }

   public MessageEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof MessageCK) {
         MessagePK pk = ((MessageCK)key).getMessagePK();
         return this.getLocal(pk).getDetails((MessageCK)key, dependants);
      } else {
//         return key instanceof MessagePK?this.getLocal((MessagePK)key).getDetails(dependants):null;
    	  if(key instanceof MessagePK){
    		  MessagePK pk = (MessagePK)key;
    		  MessageCK ck = new MessageCK(pk);
    		  return this.getLocal(pk).getDetails(ck,dependants);
    	  }else 
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(MessageEVO evo) throws Exception {
      MessagePK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public MessageEVO setAndGetDetails(MessageEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public MessagePK generateKeys(MessagePK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllMessagesELO getAllMessages() {
      MessageDAO dao = new MessageDAO();
      return dao.getAllMessages();
   }

   public InBoxForUserELO getInBoxForUser(String param1) {
      MessageDAO dao = new MessageDAO();
      return dao.getInBoxForUser(param1);
   }

   public UnreadInBoxForUserELO getUnreadInBoxForUser(String param1) {
      MessageDAO dao = new MessageDAO();
      return dao.getUnreadInBoxForUser(param1);
   }

   public SentItemsForUserELO getSentItemsForUser(String param1) {
      MessageDAO dao = new MessageDAO();
      return dao.getSentItemsForUser(param1);
   }

   public MessageForIdELO getMessageForId(long param1, String param2) {
      MessageDAO dao = new MessageDAO();
      return dao.getMessageForId(param1, param2);
   }

   public MessageForIdSentItemELO getMessageForIdSentItem(long param1, String param2) {
      MessageDAO dao = new MessageDAO();
      return dao.getMessageForIdSentItem(param1, param2);
   }

   public MessageCountELO getMessageCount(long param1) {
      MessageDAO dao = new MessageDAO();
      return dao.getMessageCount(param1);
   }

   public MessageFromUserELO getMessageFromUser(long param1) {
      MessageUserDAO dao = new MessageUserDAO();
      return dao.getMessageFromUser(param1);
   }

   public MessageToUserELO getMessageToUser(long param1) {
      MessageUserDAO dao = new MessageUserDAO();
      return dao.getMessageToUser(param1);
   }

   public AllMessagesToUserELO getAllMessagesToUser(long param1) {
      MessageUserDAO dao = new MessageUserDAO();
      return dao.getAllMessagesToUser(param1);
   }

   public AttatchmentForMessageELO getAttatchmentForMessage(long param1) {
      MessageAttatchDAO dao = new MessageAttatchDAO();
      return dao.getAttatchmentForMessage(param1);
   }
}
