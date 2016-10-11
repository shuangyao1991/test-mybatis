package mybatis.mvc.services;

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
        System.out.println(user.toString());
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
            System.out.println("Null--------------");
        }
        for (User user : users) {
            System.out.println("--------------" + user.toString());
        }

    }
}
