package mybatis.mvc.services;

import com.google.common.collect.Lists;
import mybatis.mvc.BaseUnitTest;
import mybatis.mvc.model.User;
import mybatis.mvc.service.impl.UserServiceImpl;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public class UserServiceImplTest extends BaseUnitTest {

    @Resource
    private UserServiceImpl userService;

    @Test
    public void testGetById() throws Exception {
        User user = userService.getById(1);
        println(user.toString());
    }

    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setUname("test");
        user.setAge(0);
        user.setBirthday(new Date());
        userService.save(user);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userService.getById(5);
        user.setAge(0);
        userService.update(user);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = userService.getAll();
        if (users == null || users.size() == 0) {
            println("--------------Null");
        }
        for (User user : users) {
            println("--------------" + user.toString());
        }
    }

    @Test
    public void testBatchSave() throws Exception {
        List<User> users = Lists.newArrayList();
        users.add(userService.getById(1));
        users.add(userService.getById(2));
        userService.batchSave(users);
    }

    @Test
    public void testBatchDelete() throws Exception {
        List<Integer> ids = Lists.newArrayList();
        ids.add(18);
        ids.add(19);
        ids.add(20);
        ids.add(21);
        ids.add(22);
        ids.add(23);
        ids.add(24);
        ids.add(25);
        userService.batchDelete(ids);
    }

    @Test
    public void testQueryByUserName() throws Exception {
        List<User> users = userService.queryByUserName("user", 1, 3);
        if (users == null || users.size() == 0) {
            println("-----------no user found-----------");
        }
        for (User user : users) {
            println(user);
        }
    }

    @Test
    public void testQueryTotalByUserName() throws Exception {
        int count = userService.queryTotalByUserName("test");
        println(count);

    }
}
