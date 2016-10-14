package org.vica.ms.dao;

import org.vica.ms.po.Reply;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object Of Reply
 * Created by Vica-tony on 2016/8/21.
 */
public class ReplyDao extends IBaseDao {

    private UserDao userDao = null;
    private MessageDao messageDao = null;

    /**
     * Assemble The Reply List From ResultSet
     *
     * @param set The ResultSet
     * @return The Reply List
     * @throws SQLException exceptions
     */
    private List<Reply> assemble(ResultSet set, boolean loadMsg, boolean loadNext) throws SQLException {
        List<Reply> users = new ArrayList<Reply>();
        while (set.next()) {
            users.add(assembleSigle(set, loadMsg, loadNext));
        }
        return users;
    }

    /**
     * Assemble The Reply From ResultSet
     *
     * @param set The ResultSet
     * @return The Reply, Maybe null
     * @throws SQLException exceptions
     */
    private Reply assembleSigle(ResultSet set, boolean loadMsg, boolean loadNext) throws SQLException {
        Reply msg = new Reply();
        msg.setUser_id(set.getLong("user_id"));
        msg.setMsg_id(set.getLong("msg_id"));
        msg.setContent(set.getString("content"));
        msg.setCreate_time(set.getString("create_time"));
        msg.setR_id(set.getLong("r_id"));
        msg.setCount(selectAllCountByReply(msg.getR_id()));
        if (loadNext)
            msg.setReplies(selectByReply(msg.getR_id(), 1, 6, false, false));
        if (userDao == null)
            userDao = new UserDao();
        msg.setOwner(userDao.selectByIdOrName(msg.getUser_id()));
        if (loadMsg) {
            if (messageDao == null)
                messageDao = new MessageDao();
            msg.setMsg(messageDao.selectById(msg.getMsg_id()));
        }
        return msg;
    }

    /**
     * Insert A Reply Into Reply Table
     *
     * @param content Content
     * @param userId  UserId
     * @param msgId   MsgId
     * @return How many rows inserted
     */
    public int insert(String content, long userId, long msgId, Long rootReplyId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "insert into t_reply(content,user_id,msg_id,reply_id) values(?,?,?,?);";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, content);
            statement.setObject(2, userId);
            statement.setObject(3, msgId);
            if (rootReplyId == null)
                statement.setNull(4, Types.BIGINT);
            else
                statement.setObject(4, rootReplyId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Select Reply By Id
     *
     * @param id Reply Id
     * @return Reply
     */
    public Reply selectById(long id, boolean loadMsg) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select * from t_reply where r_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return null;
            return assembleSigle(set, loadMsg, false);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Select Reply List
     *
     * @param msgId By The MsgId
     * @return The List, Can be null
     */
    public List<Reply> selectByMsg(long msgId, int page, int pageSize, boolean loadMsg) {
        if (connection == null) {
            getConnection();
        }
        if (page < 1)
            page = 1;
        String sql = "select * from t_reply where msg_id=? and reply_id is null";
        sql += " order by create_time desc limit ?,?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, msgId);
            statement.setObject(2, (page - 1) * pageSize);
            statement.setObject(3, pageSize);
            ResultSet set = statement.executeQuery();
            if (set.wasNull())
                return null;
            return assemble(set, loadMsg, true);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Query The Reply Count
     * @param replyId the reply msg replied to
     * @param page page index
     * @param pageSize page size
     * @param loadMsg query the msg associated by this reply
     * @param loadNext query the next layer
     * @return
     */
    public List<Reply> selectByReply(long replyId, int page, int pageSize, boolean loadMsg, boolean loadNext) {
        if (connection == null) {
            getConnection();
        }
        if (page < 1)
            page = 1;
        String sql = "select * from t_reply where reply_id=?";
        sql += " order by create_time desc limit ?,?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, replyId);
            statement.setObject(2, (page - 1) * pageSize);
            statement.setObject(3, pageSize);
            ResultSet set = statement.executeQuery();
            if (set.wasNull())
                return null;
            return assemble(set, loadMsg, loadNext);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Query the reply count by replied msg id
     * @param replyId
     * @return
     */
    public int selectAllCountByReply(long replyId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(r_id) from t_reply where reply_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, replyId);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Select Reply List By Sender
     *
     * @param userId By The UserId
     * @return The List, Can be null
     */
    public List<Reply> selectByUser(long userId, int page, int pageSize, boolean loadMsg) {
        if (connection == null) {
            getConnection();
        }
        if (page < 1)
            page = 1;
        String sql = "select * from t_reply where user_id=? ";
        sql += " order by create_time desc limit ?,? ;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, userId);
            statement.setObject(2, (page - 1) * pageSize);
            statement.setObject(3, pageSize);
            ResultSet set = statement.executeQuery();
            if (set.wasNull())
                return null;
            return assemble(set, loadMsg, false);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Query the reply by receiver
     * @param userId receiver
     * @param page page index
     * @param pageSize page size
     * @param loadMsg load msg ...
     * @return
     */
    public List<Reply> selectByReceiver(long userId, int page, int pageSize, boolean loadMsg) {
        if (connection == null) {
            getConnection();
        }
        if (page < 1)
            page = 1;
        String sql = "select a.* from t_reply a, t_message b where b.user_id=? and b.msg_id=a.msg_id";
        sql += " order by create_time desc limit ?,? ;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, userId);
            statement.setObject(2, (page - 1) * pageSize);
            statement.setObject(3, pageSize);
            ResultSet set = statement.executeQuery();
            if (set.wasNull())
                return null;
            return assemble(set, loadMsg, false);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Query the reply count by receiver
     * @param userId receiver
     * @return
     */
    public int selectAllCountByReceiver(long userId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(a.r_id) from t_reply a, t_message b where b.user_id=? and b.msg_id=a.msg_id;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, userId);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Query the reply count by sender
     * @param userId sender
     * @return
     */
    public int selectAllCountByUser(long userId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(r_id) from t_reply where user_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, userId);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Query the reply count by msg
     * @param msgId the msg that replied to
     * @return
     */
    public int selectAllCountByMsg(long msgId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(r_id) from t_reply where msg_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, msgId);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Query the first layer reply count by msg
     * @param msgId the msg id that replied to
     * @return
     */
    public int selectMainCountByMsg(long msgId) {
        if (connection == null) {
            getConnection();
        }
        String sql = "select count(r_id) from t_reply where msg_id=? and reply_id is null;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, msgId);
            ResultSet set = statement.executeQuery();
            if (!set.next())
                return 0;
            return set.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Delete By Id
     *
     * @param id
     * @return
     */
    public int delete(long id) {
        if (connection == null) {
            getConnection();
        }
        String sql = "delete from t_reply where r_id=?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }
}
