package org.vica.ms.dto;

import org.vica.ms.po.Message;
import org.vica.ms.po.User;

import java.util.List;

/**
 * The DTO Of Message Home
 * Created by Vica-tony on 2016/8/24.
 */
public class MsgHome {

    private int count;
    private int pages;
    private int size;
    private int indexPage;
    private List<Message> list;
    private User currentUser;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
