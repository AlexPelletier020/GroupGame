package base;

import java.util.Date;

import org.json.JSONObject;

import com.groupproject.util.MessageType;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Message implements Comparable<Message> {

	private final String TYPE_KEY = "type";
	private final String SENDER_KEY = "sender";
	private final String MESSAGE_KEY = "message";
	private final String DATE_KEY = "date";

	private MessageType type;
	private String sender;
	private String message;
	private Date date;

	public Message() {
		type = MessageType.NULL;
		sender = "";
		message = "";
		date = new Date();
	}

	public MessageType getType() {
		return type;
	}

	public void setType(String type) {
		this.type = MessageType.valueOf(type);
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageDate() {
		return date;
	}

	public void setMessageDate(Date messageDate) {
		this.date = messageDate;
	}

	@Override
	public String toString() {
		return toJSON().toString();
	}

	public String toPrettyString() {
		return toJSON().toString(4);
	}

	public JSONObject toJSON() {
		JSONObject outgoingMsg = new JSONObject();
		outgoingMsg.put(DATE_KEY, date.getTime());
		outgoingMsg.put(MESSAGE_KEY, message);
		outgoingMsg.put(SENDER_KEY, sender);
		outgoingMsg.put(TYPE_KEY, type.name());
		return outgoingMsg;
	}

	public void toMessage(JSONObject jsonMsg) {
		type = MessageType.valueOf(jsonMsg.optString(TYPE_KEY, MessageType.NULL.name()));
		sender = jsonMsg.optString(SENDER_KEY);
		message = jsonMsg.optString(MESSAGE_KEY);
		date = new Date(jsonMsg.optLong(DATE_KEY, System.currentTimeMillis()));
	}

	@Override
	public boolean equals(Object obj) {
		// If the object is compared with itself then return true   
		if (obj == this) {
			return true;
		}

		//false if it's not a Message object
		if (!(obj instanceof Message)) {
			return false;
		}

		return ((Message) obj).getSender() == this.sender && ((Message) obj).getType() == this.type
				&& ((Message) obj).getMessage() == this.message && ((Message) obj).getMessageDate() == this.date;
	}

	@Override
	public int compareTo(Message comparestu) {
		return date.compareTo(comparestu.getMessageDate());
	}

}
