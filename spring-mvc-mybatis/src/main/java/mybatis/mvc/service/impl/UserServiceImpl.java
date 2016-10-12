package mybatis.mvc.service.impl;

import com.google.common.collect.Maps;
import mybatis.mvc.dao.UserDAO;
import mybatis.mvc.model.User;
import mybatis.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with by shuangyao on 2016/10/11.
 */
@Service
public class UserServiceImpl implements UserService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDAO userDAO;

    @Override
    public void save(User user) {
        try {
            if (user == null || user.getUname() == null) {
                return;
            }
            userDAO.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public User getById(int id) {
        logger.debug("UserServiceImpl -> getById : id = " + id);
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
