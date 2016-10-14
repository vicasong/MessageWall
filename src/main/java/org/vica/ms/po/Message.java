package org.vica.ms.po;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The Message Object
 * Created by Vica-tony on 2016/8/21.
 */
public class Message {
    private long msg_id;
    private String title;
    private String content;
    private long user_id;
    private Date create_time;
    private String topped;

    // ===================
    // Foreign Key Fields
    private User owner;
//    private List<Reply> replys;
    // ===================
    private int replyCount;

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.create_time = format.parse(create_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTopped() {
        return topped;
    }

    public void setTopped(String topped) {
        this.topped = topped;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

//    public List<Reply> getReplys() {
//        return replys;
//    }
//
//    public void setReplys(List<Reply> replys) {
//        this.replys = replys;
//    }

    @Override
    public String toString() {
        return "Message{" +
                "msg_id=" + msg_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user_id=" + user_id +
                ", create_time=" + create_time +
                ", topped='" + topped + '\'' +
                ", owner=" + owner +
//                ", replys=" + replys +
                '}';
    }
}
