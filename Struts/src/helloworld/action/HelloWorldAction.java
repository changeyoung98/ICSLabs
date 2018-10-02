package helloworld.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import helloworld.model.MessageStore;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HelloWorldAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	private static int helloCount = 0;

	private MessageStore messageStore;
	private String userName;
	private Map<String, Object> userSession;

	public String execute() throws Exception {
		messageStore = new MessageStore();

		if (userName != null) {

			messageStore.setMessage(messageStore.getMessage() + " " + userName);

		}
		helloCount++;
		increaseHelloCount();

		return SUCCESS;
	}

	public MessageStore getMessageStore() {
		return messageStore;
	}

	public void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getHelloCount() {
		return helloCount;
	}

	public void setHelloCount(int helloCount) {
		HelloWorldAction.helloCount = helloCount;
	}

	public void setSession(Map<String, Object> session) {

		userSession = session;

	}

	private void increaseHelloCount() {

		Integer helloCount = (Integer) userSession.get("helloCount");

		if (helloCount == null) {

			helloCount = 1;

		} else {

			helloCount++;

		}

		userSession.put("helloCount", helloCount);

	}

}
