package ba.work.chbla.ba_eresamont.Models;

import java.util.Date;

/**
 * Created by chbla on 17.12.2017.
 */

public class ChatMessage {
    private String messageText="leer";
    private String messageUser="leer";
    private long messageTime=123456;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        // Initialize to current time
        this.messageTime = new Date().getTime();
    }

    public ChatMessage(){
    }
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public String getMessageUser() {
        return messageUser;
    }
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
    public long getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
