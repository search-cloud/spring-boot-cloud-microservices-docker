package io.vincent.account.common

import io.vincent.account.domain.model.Account
import org.junit.Test

/**
 * mybatis生成注解类
 *
 * @author Vincent.Lu
 */
class GenerateMybatisMapperAnnotation {

    @Test
    fun generateMapper() {
        println(SEPARATOR0 + "Account" + SEPARATOR0)
        // Account
        generate(Account::class.java)
    }

    companion object {

        private const val SEPARATOR = "================================================================================"
        private const val SEPARATOR0 = "==========================================="

        /**
         * 生成语句并输出
         *
         * @param clz 实体类全名
         * @throws Exception
         */
        @Throws(Exception::class)
        private fun generate(clz: Class<*>) {
            println("insert:")
            println(SEPARATOR)
            generateInsertAnnotation(clz)
            println(SEPARATOR)
            println("update:")
            println(SEPARATOR)
            generateUpdateAnnotation(clz)
            println(SEPARATOR)
            println("select:")
            println(SEPARATOR)
            generateSelectAnnotation(clz)
            println(SEPARATOR)
            println("delete:")
            println(SEPARATOR)
            generateDeleteAnnotation(clz)
            println(SEPARATOR)
        }

        @Throws(Exception::class)
        private fun generateDeleteAnnotation(clz: Class<*>) {
            //        Class<?> clz = Class.forName(clzName);
            val className = clz.simpleName
            val tableName = camelToUnderline(className)
            val stringBuilder = StringBuilder()
            stringBuilder.append("@Delete(")
            stringBuilder.append("\"")
            stringBuilder.append("delete from ")
            stringBuilder.append(tableName)


            stringBuilder.append(" where id=#{")


            //        stringBuilder.append(convertFirstCharacterLower(className));
            stringBuilder.append("id, jdbcType=BIGINT}")
            stringBuilder.append("\"")
            stringBuilder.append(")")
            println(stringBuilder.toString())
        }

        @Throws(Exception::class)
        private fun generateSelectAnnotation(clz: Class<*>) {
            //        Class<?> clz = Class.forName(clzName);
            val className = clz.simpleName
            val tableName = camelToUnderline(className)
            val stringBuilder = StringBuilder()
            stringBuilder.append("@Select(")
            stringBuilder.append("\"")
            stringBuilder.append("select ")
            val fields = clz.declaredFields
            for (i in fields.indices) {
                val columnName = fields[i].name

                if (!isAcronym(columnName)) {
                    stringBuilder.append(camelToUnderline(columnName))
                    stringBuilder.append(" as ")
                    stringBuilder.append(convertFirstCharacterLower(columnName))


                } else {
                    stringBuilder.append(camelToUnderline(columnName))
                }
                if (i < fields.size - 1) {
                    stringBuilder.append(", ")
                }
            }
            stringBuilder.append(" from ")
            stringBuilder.append(tableName)
            stringBuilder.append(" where id=#{")


            stringBuilder.append(convertFirstCharacterLower(className))
            stringBuilder.append(".id, jdbcType=BIGINT}")
            stringBuilder.append("\"")
            stringBuilder.append(")")
            //        stringBuilder.append("\r\n");
            //        stringBuilder.append("@SelectKey(before=false, keyProperty=\"" + convertFirstCharacterLower(className) + ".id\", resultType=Long.class, statementType= StatementType.STATEMENT, statement=\"SELECT LAST_INSERT_ID() AS id\"");
            //        stringBuilder.append(")");
            println(stringBuilder.toString())
        }

        /**
         * 为类生成update注解语句
         *
         * @param clz 实体类全名
         * @throws Exception
         */
        @Throws(Exception::class)
        private fun generateUpdateAnnotation(clz: Class<*>) {
            //        Class<?> clz = Class.forName(clzName);
            val className = clz.simpleName
            val tableName = camelToUnderline(className)
            val stringBuilder = StringBuilder()
            stringBuilder.append("@Update(")
            stringBuilder.append("\"")
            stringBuilder.append("update ")
            stringBuilder.append(tableName)
            stringBuilder.append(" set ")
            val fields = clz.declaredFields
            for (i in fields.indices) {
                val columnName = fields[i].name
                if (columnName == "id") {
                    continue
                }
                stringBuilder.append(camelToUnderline(columnName))
                stringBuilder.append("=#{")
                stringBuilder.append(convertFirstCharacterLower(className))
                stringBuilder.append(".")
                stringBuilder.append(columnName)
                stringBuilder.append("}")
                if (i < fields.size - 1) {
                    stringBuilder.append(", ")
                }
            }
            stringBuilder.append(" where id=#{")
            stringBuilder.append(convertFirstCharacterLower(className))
            stringBuilder.append(".id, jdbcType=BIGINT}")
            stringBuilder.append("\"")
            stringBuilder.append(")")
            println(stringBuilder.toString())
        }

        /**
         * 为类生成insert注解语句
         *
         * @param clz 实体类全名
         * @throws Exception
         */
        @Throws(Exception::class)
        private fun generateInsertAnnotation(clz: Class<*>) {
            //        Class<?> clz = Class.forName(clzName);
            val className = clz.simpleName
            val tableName = camelToUnderline(className)
            val stringBuilder = StringBuilder()
            stringBuilder.append("@Insert(")
            stringBuilder.append("\"")
            stringBuilder.append("insert into ")
            stringBuilder.append(tableName)
            stringBuilder.append("(")

            val fields = clz.declaredFields
            for (i in fields.indices) {
                val columnName = fields[i].name
                if (columnName == "id") {
                    continue
                }
                stringBuilder.append(camelToUnderline(columnName))
                if (i < fields.size - 1) {
                    stringBuilder.append(", ")
                }
            }
            stringBuilder.append(")")
            stringBuilder.append(" values (")
            for (i in fields.indices) {

                val columnName = fields[i].name
                if (columnName == "id") {
                    continue
                }
                stringBuilder.append("#{")
                stringBuilder.append(convertFirstCharacterLower(className))
                stringBuilder.append(".")
                stringBuilder.append(columnName)
                stringBuilder.append("}")
                if (i < fields.size - 1) {
                    stringBuilder.append(", ")
                }
            }
            stringBuilder.append(")")
            stringBuilder.append("\")")
            stringBuilder.append("\r\n")
            stringBuilder.append("@SelectKey(before=false, keyProperty=\"" + convertFirstCharacterLower(className) + ".id\", resultType=Long::class, statementType=StatementType.STATEMENT, statement=[\"SELECT LAST_INSERT_ID() AS id\"]")
            stringBuilder.append(")")
            println(stringBuilder.toString())
        }

        /**
         * 将字符串转化成小写,并以"_"分割
         *
         * @param param
         * @return
         */
        private fun camelToUnderline(param: String?): String {
            val underline = '_'
            if (param == null || "" == param.trim { it <= ' ' }) {
                return ""
            }
            val len = param.length
            val sb = StringBuilder(len)
            for (i in 0 until len) {
                val c = param[i]
                if (Character.isUpperCase(c)) {
                    if (i != 0) {
                        sb.append(underline)
                    }
                    sb.append(Character.toLowerCase(c))
                } else {
                    sb.append(c)
                }
            }
            return sb.toString()
        }

        /**
         * 将字符串首字母转化成小写
         *
         * @param param
         * @return
         */
        private fun convertFirstCharacterLower(param: String?): String {
            if (param == null || "" == param.trim { it <= ' ' }) {
                return ""
            }
            val len = param.length
            val sb = StringBuilder(len)
            for (i in 0 until len) {
                val c = param[i]
                if (i == 0) {
                    sb.append(Character.toLowerCase(c))
                } else {
                    sb.append(c)
                }
            }
            return sb.toString()
        }

        private fun isAcronym(word: String): Boolean {
            for (i in 0 until word.length) {
                val c = word[i]
                if (!Character.isLowerCase(c)) {
                    return false
                }
            }
            return true
        }
    }
}
