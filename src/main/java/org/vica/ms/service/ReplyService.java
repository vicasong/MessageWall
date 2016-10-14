package org.vica.ms.service;

import org.vica.ms.dao.MessageDao;
import org.vica.ms.dao.ReplyDao;
import org.vica.ms.dto.MsgDetail;
import org.vica.ms.dto.MsgHome;
import org.vica.ms.po.Message;
import org.vica.ms.po.Reply;
import org.vica.ms.po.User;

/**
 * The Business Layer Of Message
 * Created by Vica-tony on 2016/8/24.
 */
public class ReplyService {

    private ReplyDao replyDao;
    private User currentUser;

    public ReplyService(User currentUser){
        replyDao = new ReplyDao();
        this.currentUser = currentUser;
    }

    /**
     * Post A New Reply By MsgId And Content
     * @param content The Content
     * @param msgId The Message Id
     * @param replyId The MainRoot Reply Id (can be null)
     * @return Whether The Operation Succeed
     */
    public boolean reply(long msgId, String content, Long replyId){
        return replyDao.insert(content,currentUser.getUser_id(),msgId, replyId)>0;
    }

    /**
     * Wipe A Reply From DataBase By The Id
     * @param rId The Reply Id
     * @return Whether The Operation Succeed
     */
    public boolean deleteReply(long rId){
        Reply deleted = replyDao.selectById(rId,false);
        if(deleted == null || deleted.getUser_id()!=currentUser.getUser_id()){
            return false;
        }
        return replyDao.delete(rId)>0;
    }

    /**
     * List All The Reply Data Paged By Msg
     * @param msgId The Msg Id
     * @param page Current Page Index (1 at least)
     * @param size The Max Data Num For Per Page
     * @return The Packaged Data
     */
    public MsgDetail queryMsg(long msgId, int page, int size){
        MsgDetail detail = buildBase(page,size);
        detail.setCount(replyDao.selectMainCountByMsg(msgId));
        detail.setList(replyDao.selectByMsg(msgId,detail.getIndexPage(),detail.getSize(),true));
        detail.setPages(detail.getCount()/size+(detail.getCount()%detail.getSize()==0?0:1));
        MessageDao service = new MessageDao();
        detail.setMessage(service.selectById(msgId));
        service.close();
        return detail;
    }

    /**
     * Query List By Current User Paged
     * @param page Current Page Index (1 at least)
     * @param size The Max Data Num For Per Page
     * @return The Packaged Data
     */
    public MsgDetail queryMy(int page, int size){
        MsgDetail detail = buildBase(page,size);
        detail.setCount(replyDao.selectAllCountByUser(currentUser.getUser_id()));
        detail.setList(replyDao.selectByUser(currentUser.getUser_id(),detail.getIndexPage(),detail.getSize(),true));
        detail.setPages(detail.getCount()/size+(detail.getCount()%detail.getSize()==0?0:1));
        detail.setMessage(null);
        return detail;
    }

    /**
     * Query By Sent to me
     * @param page page index
     * @param size page size
     * @return list
     */
    public MsgDetail queryToMe(int page, int size){
        MsgDetail detail = buildBase(page,size);
        detail.setCount(replyDao.selectAllCountByReceiver(currentUser.getUser_id()));
        detail.setList(replyDao.selectByReceiver(currentUser.getUser_id(),detail.getIndexPage(),detail.getSize(),true));
        detail.setPages(detail.getCount()/size+(detail.getCount()%detail.getSize()==0?0:1));
        detail.setMessage(null);
        return detail;
    }

    /**
     * Package the info
     * @param page page index
     * @param size page size
     * @return
     */
    private MsgDetail buildBase(int page, int size){
        page = page>0?page:1;
        size = size>0?size:9;
        MsgDetail detail = new MsgDetail();
        detail.setCurrentUser(currentUser);
        detail.setSize(size);
        detail.setIndexPage(page);
        return detail;
    }

    @Override
    protected void finalize() throws Throwable {
        if(replyDao!=null)
            replyDao.close();
        super.finalize();
    }
}
