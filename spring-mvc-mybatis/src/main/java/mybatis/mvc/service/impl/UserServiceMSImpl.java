package mybatis.mvc.service.impl;

import com.google.common.collect.Maps;
import mybatis.mvc.dao.master.UserDAOMaster;
import mybatis.mvc.dao.slaver.UserDAOSlaver;
import mybatis.mvc.model.User;
import mybatis.mvc.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with by shuangyao on 2016/10/13.
 */
@Service("userServiceMSImpl")
public class UserServiceMSImpl implements UserService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceMSImpl.class);

    @Resource(name = "masterSqlSession")
    private SqlSession masterSqlSession;

    @Resource(name = "slaverSqlSession")
    private SqlSession slaverSqlSession;

//    private UserDAOSlaver reader = masterSqlSession.getMapper(UserDAOSlaver.class);
//
//    private UserDAOMaster writer = slaverSqlSession.getMapper(UserDAOMaster.class);

    private UserDAOSlaver reader = null;

    private UserDAOMaster writer = null;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(User user) throws Exception{
//        try {
        if (user == null || user.getUname() == null) {
            return;
        }
        writer.save(user);
        user.setUname("123456789123456789123456789123456789123456789123456789123456789123456789123456789");
        writer.save(user);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
    }

    @Override
    public User getById(int id) {
        logger.debug("UserServiceImpl -> getById : id = " + id);
        reader = slaverSqlSession.getMapper(UserDAOSlaver.class);
        return reader.getById(id);
    }

    @Override
    public void update(User user) {
        try {
            writer.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        try {
            writer.delete(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return reader.getAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void batchSave(List<User> users) {
        try {
            if (users == null || users.size() == 0) {
                return;
            }
            writer.batchSave(users);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        try {
            if (ids == null || ids.size() == 0) {
                return;
            }
            writer.batchDelete(ids);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<User> queryByUserName(String uname, int offset, int limit) {
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("uname", uname);
            params.put("offset", offset);
            params.put("limit", limit);
            return reader.queryByUserName(params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public int queryTotalByUserName(String uname) {
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("uname", uname);
            return reader.queryTotalByUserName(params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
    }
}
