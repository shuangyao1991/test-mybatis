package mybatis.mvc.service;

import mybatis.mvc.model.User;

import java.util.List;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public interface UserService {
    public void save(User user) throws Exception;

    public User getById(int id);

    public void update(User user);

    public void delete(User user);

    public List<User> getAll();

    public void batchSave(List<User> users);

    public void batchDelete(List<Integer> ids);

    public List<User> queryByUserName(String uname, int offset, int limit);

    public int queryTotalByUserName(String uname);
}
