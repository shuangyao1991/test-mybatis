package mybatis.mvc.dynamic.dao;

import mybatis.mvc.dynamic.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public interface UserDAO {

//    @Select("select * from user where id = #{id}")
//    @ResultMap("userMap")
    public User getById(@Param("id") int id);

//    @Insert("insert into user value (null, #{u.uname}, #{u.age}, #{u.birthday})")
//    @Options(useGeneratedKeys = true, keyProperty = "u.id")
    public int save(@Param("user") User user) throws Exception;


//    @Update("update user set uname = #{user.uname}, age = #{user.age} where id = #{user.id}")
    public int update(@Param("user") User user) throws Exception;

//    @Delete("delete from user where id = #{user.id}")
    public int delete(@Param("user") User user) throws Exception;

//    @Select("select * from user")
//    @ResultMap("userMap")
    public List<User> getAll() throws Exception;

    public int batchSave(@Param("users") List<User> users) throws Exception;

    public int batchDelete(@Param("ids") List<Integer> ids) throws Exception;

    /**
     *
     * @param params keys: uname, offset, limit
     * @return
     * @throws Exception
     */
    public List<User> queryByUserName(Map<String, Object> params) throws Exception;

    /**
     *
     * @param params keys : uname
     * @return
     * @throws Exception
     */
    public int queryTotalByUserName(Map<String, Object> params) throws Exception;
}
