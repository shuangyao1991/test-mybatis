package mybatis.mvc.sqlite.handlers;

import mybatis.mvc.sqlite.utils.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with by shuangyao on 2016/10/12.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class GenericTypeHandler<T extends Object> extends BaseTypeHandler<T> {

    private Class<T> type;

    public GenericTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null.");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtil.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JsonUtil.fromJson(rs.getString(columnName), type, null);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JsonUtil.fromJson(rs.getString(columnIndex), type, null);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JsonUtil.fromJson(cs.getString(columnIndex), type, null);
    }
}
