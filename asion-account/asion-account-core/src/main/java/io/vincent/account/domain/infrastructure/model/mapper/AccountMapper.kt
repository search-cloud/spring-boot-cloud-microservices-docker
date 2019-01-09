package io.vincent.account.domain.infrastructure.model.mapper

import io.vincent.account.domain.model.Account
import org.apache.ibatis.annotations.*
import org.apache.ibatis.mapping.StatementType

/**
 * MyBatis Mapper.
 *
 * @author Vincent.
 * @since 1.0, 11/2/18
 */
@Mapper
interface AccountMapper {
    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be null.
     * @return the saved entity will never be null.
     */
    @Insert("insert into account(code, username, nick_name, password, used_password, mobile, email, we_chat_id, enabled, parent_id, status, last_login_at, operation_error_count, created_at, updated_at) values (#{account.code}, #{account.username}, #{account.nickName}, #{account.password}, #{account.usedPassword}, #{account.mobile}, #{account.email}, #{account.weChatId}, #{account.enabled}, #{account.parentId}, #{account.status}, #{account.lastLoginAt}, #{account.operationErrorCount}, now(), now())")
    @SelectKey(before=false, keyProperty="account.id", resultType=Long::class, statementType=StatementType.STATEMENT, statement=["SELECT LAST_INSERT_ID() AS id"])
    fun create(@Param("account") entity: Account): Int

    /**
     * Update entity
     *
     * @param entity must not be null.
     * @return the updated entity will never be null.
     */
    @Update("update account set code=#{account.code}, username=#{account.username}, nick_name=#{account.nickName}, password=#{account.password}, used_password=#{account.usedPassword}, mobile=#{account.mobile}, email=#{account.email}, we_chat_id=#{account.weChatId}, enabled=#{account.enabled}, parent_id=#{account.parentId}, status=#{account.status}, last_login_at=#{account.lastLoginAt}, operation_error_count=#{account.operationErrorCount}, updated_at=now() where id=#{account.id, jdbcType=BIGINT}")
    fun update(@Param("account") entity: Account): Int

    /**
     * Retrieves an entity by it id.
     *
     * @param id must not be null.
     * @return the entity with the given id or null
     * @throws IllegalArgumentException if `id` matches null.
     */
    @Select("select $SELECT_COLUMNS from account where id=#{id, jdbcType=BIGINT}")
    fun findById(@Param("id") id: Long): Account?

    /**
     * Find account by username.
     */
    @Select("select $SELECT_COLUMNS from account where username=#{username, jdbcType=VARCHAR}")
    fun findByUsername(@Param("username") username: String): Account?

    /**
     * 查询所有的account
     */
    @Select("select $SELECT_COLUMNS from account")
    fun findAll(): List<Account>

    @Update("update account set status=#{statusCode}, updated_at=now() where id=#{id, jdbcType=BIGINT}")
    fun updateStatus(@Param("id") id: Long, @Param("statusCode") statusCode: Int): Int

    companion object {
        private const val SELECT_COLUMNS = "id, code, username, nick_name as nickName, password, used_password as usedPassword, mobile, email, we_chat_id as weChatId, enabled, parent_id as parentId, status, last_login_at as lastLoginAt, operation_error_count as operationErrorCount, created_at as createdAt, updated_at as updatedAt"
    }

}