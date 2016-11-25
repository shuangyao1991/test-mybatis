package mybatis.mvc.services;

import static mybatis.mvc.TestUtil.*;
import com.google.common.collect.Lists;
import mybatis.mvc.BaseUnitTest;
import mybatis.mvc.dynamic.model.KeyValue;
import mybatis.mvc.dynamic.model.User;
import mybatis.mvc.dynamic.service.UserService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public class UserServiceTest extends BaseUnitTest {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Test
    public void testGetById() throws Exception {
        User user = userService.getById(2);
        println(user.toString());
    }

    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setUname("testtt");
        user.setAge(0);
//        user.setBirthday(new Date());

        KeyValue keyValue = new KeyValue();
        keyValue.setKey("key3");
        keyValue.setValue("value3");
        user.setKeyValue(keyValue);
        userService.save(user);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = userService.getById(38);
        user.setAge(3);
        userService.update(user);
    }

    @Test
    public void testUpdateName() throws Exception {
        userService.updateName(null, 38);

    }

    @Test
    public void testDelete() throws Exception {
        User user = new User();
        user.setId(30);
        userService.delete(user);

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
        ids.add(6);
        ids.add(7);
        ids.add(8);
        ids.add(9);
        ids.add(37);
        ids.add(36);
        ids.add(32);
        ids.add(35);
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
