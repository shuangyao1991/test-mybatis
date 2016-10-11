package mybatis.mvc.dao;

import mybatis.mvc.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public interface UserDAO {

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id", javaType = Integer.class),
            @Result(property = "uname", column = "uname", javaType = String.class),
            @Result(property = "age", column = "age", javaType = Integer.class),
            @Result(property = "birthday", column = "birthday", javaType = Date.class)
    })
    public User getById(@Param("id") int id);

    @Insert("insert into user value (null, #{u.uname}, #{u.age}, #{u.birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "u.id")
    public int save(@Param("u") User user) throws Exception;

}
