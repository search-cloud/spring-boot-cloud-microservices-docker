package io.vincent.account.common

import io.vincent.account.domain.model.Account
import org.junit.Test

/**
 * 生成DDL
 *
 * @author Vincent.Lu
 */
class GenerateTableByEntity {

    @Test
    fun generateTable() {
        // Account
        generate(Account::class.java)
    }

    companion object {

        private const val SEPARATOR = "# ================================================================================"

        /**
         * 生成建表语句并输出
         *
         * @param clz 实体类全名
         */
        private fun generate(clz: Class<*>) {
            println(SEPARATOR)
            generateTable(clz)
        }

        private fun generateTable(clz: Class<*>) {
            val className = clz.simpleName
            val tableName = camelToUnderline(className)

            println("# $tableName DDL:")
            println(SEPARATOR)

            val drop = "DROP TABLE IF EXISTS $tableName;"
            var create = "CREATE TABLE $tableName (\n"

            val fields = clz.declaredFields
            for (i in fields.indices) {
                val field = fields[i]
                val transient = field.getAnnotation(Transient::class.java)
                if (transient != null) {
                    continue
                }
                val fieldName = field.name
                // 根据实体 fieldName 生成：columnName
                val columnName = camelToUnderline(fieldName)

                if (columnName == "id") {
                    create += "    $columnName int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '$columnName', \n"
                    continue
                }
                // field type
                when (field.type.simpleName) {
                    "Long" -> create += "    $columnName BIGINT(20) DEFAULT NULL COMMENT '$fieldName'"
                    "long" -> create += "    $columnName BIGINT(20) DEFAULT NULL COMMENT '$fieldName'"
                    "Integer" -> create += "    $columnName INT(8) DEFAULT NULL COMMENT '$fieldName'"
                    "int" -> create += "    $columnName INT(8) DEFAULT NULL COMMENT '$fieldName'"
                    "Boolean" -> create += "    $columnName TINYINT(1) DEFAULT 0 COMMENT '$fieldName'"
                    "boolean" -> create += "    $columnName TINYINT(1) DEFAULT 0 COMMENT '$fieldName'"
                    "String" -> create += "    $columnName VARCHAR(64) DEFAULT NULL COMMENT '$fieldName'"
                    "Date" -> create += "    $columnName DATETIME NOT NULL DEFAULT now() COMMENT '$fieldName'"
                    "Double" -> create += "    $columnName DECIMAL(12,4) NOT NULL DEFAULT 0.0000 COMMENT '$fieldName'"
                    "double" -> create += "    $columnName DECIMAL(12,4) NOT NULL DEFAULT 0.0000 COMMENT '$fieldName'"
                    "Float" -> create += "    $columnName DECIMAL(12,4) NOT NULL DEFAULT 0.0000 COMMENT '$fieldName'"
                    "float" -> create += "    $columnName DECIMAL(12,4) NOT NULL DEFAULT 0.0000 COMMENT '$fieldName'"
                }
                if (i < fields.size - 1) {
                    create += ", \n"
                }

            }

            create += "\n ) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='$className';"
            println(drop)
            println(create)

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

    }
}
