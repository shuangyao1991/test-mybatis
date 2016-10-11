package mybatis.mvc.services;

import mybatis.mvc.BaseUnitTest;
import mybatis.mvc.model.User;
import mybatis.mvc.service.impl.UserServiceImpl;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

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
}
