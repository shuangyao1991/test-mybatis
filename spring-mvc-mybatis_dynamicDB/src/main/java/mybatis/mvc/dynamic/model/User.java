package mybatis.mvc.dynamic.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with by shuangyao on 2016/10/11.
 */
public class User implements Serializable {

    private Integer id;

    private String uname;

    private Integer age;

    private Date birthday;

    private KeyValue keyValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public KeyValue getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(KeyValue keyValue) {
        this.keyValue = keyValue;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", keyValue=" + keyValue +
                '}';
    }
}
