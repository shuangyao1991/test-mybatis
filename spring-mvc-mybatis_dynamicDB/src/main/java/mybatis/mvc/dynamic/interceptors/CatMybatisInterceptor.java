package mybatis.mvc.dynamic.interceptors;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.cache.jcache.JCacheCache;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Created with by shuangyao on 2016/10/19.
 */
@Intercepts({
        @Signature(args = {MappedStatement.class, Object.class}, method = "update", type = Executor.class),
        @Signature(args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class},
                    method = "query", type = Executor.class)})
public class CatMybatisInterceptor implements Interceptor{

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String[] strArr = mappedStatement.getId().split("\\.");
        String classMethod = strArr[strArr.length - 2] + "." + strArr[strArr.length - 1];
        Object paramrter = null;
        if (invocation.getArgs().length > 1) {
            paramrter = invocation.getArgs()[1];

        }
        BoundSql boundSql = mappedStatement.getBoundSql(paramrter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = showSql(configuration, boundSql);
        Transaction t = Cat.newTransaction("SQL", classMethod);
        Cat.logEvent("SQL.Statement", sql.substring(0, sql.indexOf(" ")), Message.SUCCESS, sql);
        Object result = null;
        try {
            result = invocation.proceed();
            t.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            t.complete();
        }
        return result;
    }

    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry thr = configuration.getTypeHandlerRegistry();
            if (thr.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObejct = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObejct.hasGetter(propertyName)) {
                        Object obj = metaObejct.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    public String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO: 2016/10/19 Auto-generated method stub
    }
}
