package mybatis.mvc.service.impl;

import com.google.common.collect.Maps;
import mybatis.mvc.dao.UserDAO;
import mybatis.mvc.model.User;
import mybatis.mvc.service.UserService;
import mybatis.mvc.utils.DataSourceHolder;
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
    private UserDAO userDAO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(User user) throws Exception{
        if (user == null || user.getUname() == null) {
            return;
        }
        DataSourceHolder.set(DataSourceHolder.MASTER);
        userDAO.save(user);
    }

    @Override
    public User getById(int id) {
        logger.debug("UserServiceImpl -> getById : id = " + id);
        DataSourceHolder.set(DataSourceHolder.SLAVER);
        return userDAO.getById(id);
    }

    @Override
    public void update(User user) {
        try {
            userDAO.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        try {
            userDAO.delete(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userDAO.getAll();
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
            userDAO.batchSave(users);
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
            userDAO.batchDelete(ids);
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
            return userDAO.queryByUserName(params);
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
            return userDAO.queryTotalByUserName(params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
    }
}
