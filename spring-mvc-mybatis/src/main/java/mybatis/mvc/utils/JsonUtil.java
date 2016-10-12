package mybatis.mvc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created with by shuangyao on 2016/10/9.
 */
public class JsonUtil {

    private static final Logger logger = Logger.getLogger(JsonUtil.class);

    public static final String EMPTY_JSON = "{}";

    public static final String EMPTY_JSON_ARRAY = "[]";

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String toJson(Object target) {
        return toJson(target, null, true, null, null, false);
    }

    public static String toJson(Object target, boolean isSerializeNulls) {
        return toJson(target, null, isSerializeNulls, null, null, false);
    }

    public static String toJson(Object target, Type targetType,
                                boolean isSerializeNulls, Double version, String datePattern,
                                boolean excludesFieldsWithoutExpose) {
        if (target == null) {
            return EMPTY_JSON;
        }

        GsonBuilder builder = new GsonBuilder();
        if (isSerializeNulls) {
            builder.serializeNulls();
        }
        if (version != null) {
            builder.setVersion(version.doubleValue());
        }
        if (excludesFieldsWithoutExpose) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        if (StringUtils.isEmpty(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);

        String result;
        Gson gson = builder.create();
        try {
            if (targetType != null) {
                result = gson.toJson(target, targetType);
            } else {
                result = gson.toJson(target);
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("Failed to transform the target type " +
                        targetType.getClass().getName()+ "{} to Json.");
            }
            if (target instanceof Collection
                    || target instanceof Iterator
                    || target instanceof Enum
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            } else {
                result = EMPTY_JSON;
            }
        }

        return result;
    }

    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (StringUtils.isBlank(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception ex) {
            logger.error("Failed to transform the json string to " +
                            clazz.getName() + "type", ex);
            return null;
        }
    }
}
