package mybatis.mvc.dao;

import mybatis.mvc.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public interface UserDAO {

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id", javaType = Integer.class),
            @Result(property = "uname", column = "uname", javaType = String.class),
            @Result(property = "age", column = "age", javaType = Integer.class),
            @Result(property = "birthday", column = "birthday", jdbcType = JdbcType.DATE)
    })
    public User getById(@Param("id") int id);

    @Insert("insert into user value (null, #{u.uname}, #{u.age}, #{u.birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "u.id")
    public int save(@Param("u") User user) throws Exception;


    @Update("update user set uname = #{u.uname}, age = #{u.age} where id = #{u.id}")
    public int update(@Param("u") User user) throws Exception;

    @Delete("delete from user where id = #{u.id}")
    public int delete(@Param("u") User user) throws Exception;

    @Select("select * from user")
    @ResultMap("userMap")
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
