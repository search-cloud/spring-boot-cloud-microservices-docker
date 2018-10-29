package org.asion.webflux.common;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;

/**
 * mybatis生成注解类
 *
 * @author Asion
 */
@SuppressWarnings("all")
public class GenerateMapperAnnotation {

    /**
     * 实体类全名
     */
    private static String className = "org.asion.search.SeekItem";
    private static Class<?> clz;

    static {
        try {
            clz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generate() throws Exception {
        generator();
    }

    private static final String separator = "================================================================================";

    /**
     * 生成语句并输出
     */
    private static void generator() {
        System.out.println("insert:");
        System.out.println(separator);
        generateInsertAnnotation();
        System.out.println(separator);
        System.out.println("update:");
        System.out.println(separator);
        generateUpdateAnnotation();
        System.out.println(separator);
        System.out.println("select:");
        System.out.println(separator);
        generateSelectAnnotation();
        System.out.println(separator);
        System.out.println("delete:");
        System.out.println(separator);
        generateDeleteAnnotation();
        System.out.println(separator);
    }

    private static void generateDeleteAnnotation() {

        String className = clz.getSimpleName();
        String tableName = camelToUnderline(className);
        String deleteSql = "@Delete(" +
                "delete from " + tableName +
                " where id=#{id, jdbcType=BIGINT}" +
                ")";
        System.out.println(deleteSql);
    }

    private static void generateSelectAnnotation() {

        String className = clz.getSimpleName();
        String tableName = camelToUnderline(className);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Select(");
        stringBuilder.append("\"");
        stringBuilder.append("select ");
        Field[] fields = clz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String columnName = fields[i].getName();

            if (!isAcronym(columnName)) {
                stringBuilder.append(camelToUnderline(columnName));
                stringBuilder.append(" as ");
                stringBuilder.append(convertFirstCharacterLower(columnName));


            } else {
                stringBuilder.append(camelToUnderline(columnName));
            }
            if (i < fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" from ");
        stringBuilder.append(tableName);
        stringBuilder.append(" where id=#{");


        stringBuilder.append(convertFirstCharacterLower(className));
        stringBuilder.append(".id, jdbcType=BIGINT}");
        stringBuilder.append("\"");
        stringBuilder.append(")");
//        stringBuilder.append("\r\n");
//        stringBuilder.append("@SelectKey(before=false, keyProperty=\"" + convertFirstCharacterLower(className) + ".id\", resultType=Long.class, statementType= StatementType.STATEMENT, statement=\"SELECT LAST_INSERT_ID() AS id\"");
//        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    /**
     * 为类生成update注解语句
     */
    private static void generateUpdateAnnotation() {
        String className = clz.getSimpleName();
        String tableName = camelToUnderline(className);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Update(");
        stringBuilder.append("\"");
        stringBuilder.append("update ");
        stringBuilder.append(tableName);
        stringBuilder.append(" set ");
        Field[] fields = clz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String columnName = fields[i].getName();
            if (columnName.equals("id")) {
                continue;
            }
            stringBuilder.append(camelToUnderline(columnName));
            stringBuilder.append("=#{");
            stringBuilder.append(convertFirstCharacterLower(className));
            stringBuilder.append(".");
            stringBuilder.append(columnName);
            stringBuilder.append("}");
            if (i < fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" where id=#{");
        stringBuilder.append(convertFirstCharacterLower(className));
        stringBuilder.append(".id, jdbcType=BIGINT}");
        stringBuilder.append("\"");
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    /**
     * 为类生成insert注解语句
     */
    private static void generateInsertAnnotation() {
        String className = clz.getSimpleName();
        String tableName = camelToUnderline(className);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Insert(");
        stringBuilder.append("\"");
        stringBuilder.append("insert into ");
        stringBuilder.append(tableName);
        stringBuilder.append("(");

        Field[] fields = clz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String columnName = fields[i].getName();
            if (columnName.equals("id")) {
                continue;
            }
            stringBuilder.append(camelToUnderline(columnName));
            if (i < fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(")");
        stringBuilder.append(" values (");
        for (int i = 0; i < fields.length; i++) {

            String columnName = fields[i].getName();
            if (columnName.equals("id")) {
                continue;
            }
            stringBuilder.append("#{");
            stringBuilder.append(convertFirstCharacterLower(className));
            stringBuilder.append(".");
            stringBuilder.append(columnName);
            stringBuilder.append("}");
            if (i < fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(")");
        stringBuilder.append("\")");
        stringBuilder.append("\r\n");
        stringBuilder.append("@SelectKey(before=false, keyProperty=\"" + convertFirstCharacterLower(className) + ".id\", resultType=Long.class, statementType=StatementType.STATEMENT, statement=\"SELECT LAST_INSERT_ID() AS id\"");
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }

    /**
     * 将字符串转化成小写,并以"_"分割
     *
     * @param param
     * @return
     */
    @NotNull
    private static String camelToUnderline(String param) {
        char UNDERLINE = '_';
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    sb.append(UNDERLINE);
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串首字母转化成小写
     *
     * @param param
     * @return
     */
    @NotNull
    private static String convertFirstCharacterLower(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (i == 0) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 递归文件夹下文件
     *
     * @param path
     */
    private static void traverseFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());

                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    private static boolean isAcronym(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }
}
