package mybatis.mvc.service.impl;

import com.google.common.collect.Maps;
import mybatis.mvc.dao.slaver.UserDAOSlaver;
import mybatis.mvc.service.UserService;
import mybatis.mvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with by shuangyao on 2016/10/11.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDAOSlaver userDAOSlaver;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(User user) throws Exception{
//        try {
            if (user == null || user.getUname() == null) {
                return;
            }
            userDAOSlaver.save(user);
            user.setUname("123456789123456789123456789123456789123456789123456789123456789123456789123456789");
            userDAOSlaver.save(user);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
    }

    @Override
    public User getById(int id) {
        logger.debug("UserServiceImpl -> getById : id = " + id);
        return userDAOSlaver.getById(id);
    }

    @Override
    public void update(User user) {
        try {
            userDAOSlaver.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        try {
            userDAOSlaver.delete(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userDAOSlaver.getAll();
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
            userDAOSlaver.batchSave(users);
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
            userDAOSlaver.batchDelete(ids);
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
            return userDAOSlaver.queryByUserName(params);
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
            return userDAOSlaver.queryTotalByUserName(params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
    }
}
