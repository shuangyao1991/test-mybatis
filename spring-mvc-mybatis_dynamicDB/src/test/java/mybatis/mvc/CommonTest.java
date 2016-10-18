package mybatis.mvc;

import static mybatis.mvc.TestUtil.*;

import mybatis.mvc.dynamic.model.KeyValue;
import mybatis.mvc.dynamic.utils.JsonUtil;
import org.junit.Test;

/**
 * Created with by shuangyao on 2016/10/12.
 */
public class CommonTest{

    @Test
    public void testKeyValue() throws Exception {
        KeyValue keyValue = new KeyValue();
        keyValue.setKey("123");
        keyValue.setValue("hello");
        println(keyValue);

        KeyValue temp = JsonUtil.fromJson(JsonUtil.toJson(keyValue), KeyValue.class, null);
        println(temp);
    }
}
