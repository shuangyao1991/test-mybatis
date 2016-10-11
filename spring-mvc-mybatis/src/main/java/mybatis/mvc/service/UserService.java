package mybatis.mvc.service;

import mybatis.mvc.model.User;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public interface UserService {
    public void save(User user);

    public User getById(int id);
}
