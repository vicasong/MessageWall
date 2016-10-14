package org.vica.ms.po;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The Reply Object
 * Created by Vica-tony on 2016/8/21.
 */
public class Reply {
    private long r_id;
    private String content;
    private long user_id;
    private long msg_id;
    private Date create_time;

    // ===================
    // Foreign Key Fields
    private User owner;
    private Message msg;
    private List<Reply> replies;
    private int count;
    // ===================


    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getR_id() {
        return r_id;
    }

    public void setR_id(long r_id) {
        this.r_id = r_id;
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

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "r_id=" + r_id +
                ", content='" + content + '\'' +
                ", user_id=" + user_id +
                ", create_time=" + create_time +
                ", owner=" + owner +
                ", msg=" + msg +
                '}';
    }
}
