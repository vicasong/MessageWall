package org.vica.ms.service;

import org.vica.ms.dao.MessageDao;
import org.vica.ms.dto.MsgHome;
import org.vica.ms.po.Message;
import org.vica.ms.po.User;

/**
 * The Business Layer Of Message
 * Created by Vica-tony on 2016/8/24.
 */
public class MessageService {

    private MessageDao messageDao;
    private User currentUser;

    public MessageService(User currentUser){
        messageDao = new MessageDao();
        this.currentUser = currentUser;
    }

    /**
     * Create A New Message By Title And Content
     * @param title The Title Or Theme
     * @param content The Content
     * @return Whether The Operation Succeed
     */
    public boolean addMsg(String title, String content){
        return messageDao.insert(title,content,currentUser.getUser_id())>0;
    }

    /**
     * Wipe A Message From DataBase By The Id
     * @param msgId The Message Id
     * @return Whether The Operation Succeed
     */
    public boolean deleteMsg(long msgId){
        Message deleted = messageDao.selectById(msgId);
        if(deleted == null || deleted.getUser_id()!=currentUser.getUser_id()){
            return false;
        }
        return messageDao.delete(msgId)>0;
    }

    /**
     * List All The Message Data Paged
     * @param page Current Page Index (1 at least)
     * @param size The Max Data Num For Per Page
     * @return The Packaged Data
     */
    public MsgHome queryPaged(int page, int size){
        return query(null,page,size);
    }

    /**
     * Query List By Message Title Keyword Paged
     * @param keyword The KeyWord To Search
     * @param page Current Page Index (1 at least)
     * @param size The Max Data Num For Per Page
     * @return The Packaged Data
     */
    public MsgHome queryTitle(String keyword, int page, int size){
        return query(keyword,page,size);
    }

    /**
     * Query List By Current User Paged
     * @param page Current Page Index (1 at least)
     * @param size The Max Data Num For Per Page
     * @return The Packaged Data
     */
    public MsgHome queryMy(int page, int size){
        return query(currentUser.getUser_id(),page,size);
    }

    private MsgHome query(Object key, int page, int size){
        page = page>0?page:1;
        size = size>0?size:9;
        MsgHome home = new MsgHome();
        home.setCurrentUser(currentUser);
        home.setSize(size);
        home.setList(messageDao.select(key, page, size));
        home.setCount(messageDao.selectAllCount());
        home.setIndexPage(page);
        home.setPages(home.getCount()/size+(home.getCount()%size==0?0:1));
        return home;
    }

    @Override
    protected void finalize() throws Throwable {
        if(messageDao!=null)
            messageDao.close();
        super.finalize();
    }
}
