package com.zlandzbt.tools.jv.database;

import android.content.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SqlHelp {
    public static void createTable(Context context,Class clazz){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exist ");
        String tableName = clazz.getName();
        sqlBuilder.append(tableName);
        Field[] field = clazz.getFields();
        for(Field f:field){
            String column = f.getName();
            String typeName = f.getType().getTypeName().toLowerCase();
            switch (typeName){
                case "int":
                case "integer":
                    break;
                case "string":
                    break;
                case "short":
                    break;
                case "char":
                    break;
                case "byte":
                    break;
                case "float":
                case "double":
                    break;
                case "boolean":
                    break;
                case "long":
                    break;
                case "blob":
                    break;
                case "null":
                    break;
                default:
            }
        }
    }
}
