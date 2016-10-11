package mybatis.mvc.service.impl;

import mybatis.mvc.dao.UserDAO;
import mybatis.mvc.model.User;
import mybatis.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
