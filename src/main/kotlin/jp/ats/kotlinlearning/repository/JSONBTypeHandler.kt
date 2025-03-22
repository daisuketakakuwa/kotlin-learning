package jp.ats.kotlinlearning.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import org.postgresql.util.PGobject
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

// Java側の型を指定する
@MappedJdbcTypes(JdbcType.JAVA_OBJECT)
class JSONBTypeHandler<E>(
    private val type: Class<E>
) : BaseTypeHandler<E>() {

    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
    }

    // 登録時 Javaクラス → DB型(jsonb)
    override fun setNonNullParameter(ps: PreparedStatement, index: Int, parameter: E, jdbcType: JdbcType?) {
        ps.setObject(index, PGobject().also {
            it.type = "jsonb"
            it.value = objectMapper.writeValueAsString(parameter)
        })
    }

    // 取得時 DB型(jsob) → Javaクラス
    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): E? = mapJsonbToObj(cs.getString(columnIndex))
    override fun getNullableResult(rs: ResultSet, columnIndex: Int): E? = mapJsonbToObj(rs.getString(columnIndex))
    override fun getNullableResult(rs: ResultSet, columnName: String?): E? = mapJsonbToObj(rs.getString(columnName))
    private fun mapJsonbToObj(jsonText: String?): E? = jsonText?.let { objectMapper.readValue(it, type) }

}