package mybatis.mvc.dynamic.model;

import java.io.Serializable;

/**
 * Created with by shuangyao on 2016/10/12.
 */
public class KeyValue implements Serializable {

    private String key;

    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValue{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
