 package com.my.ibatis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tufei on 2017/12/29.
 */
public class SqlMapConfig {

    private Map<String,SqlMapInfo> mappedSql = new HashMap<String,SqlMapInfo>();//根据sql id能够快速找到sql

    public Map<String, SqlMapInfo> getMappedSql() {
        return mappedSql;
    }

    public void setMappedSql(Map<String, SqlMapInfo> mappedSql) {
        this.mappedSql = mappedSql;
    }

    static class Sql{
        private String sqlString;//sql语句
        private String sqlId;
        private String resultMapId;//
        private ResultMap resultMap;//结果集属于哪个resultMap
        private Map<String,Integer> parameterIndexMap;//参数index映射
        private Class parameterClass;

        public Class getParameterClass() {
            return parameterClass;
        }

        public void setParameterClass(Class parameterClass) {
            this.parameterClass = parameterClass;
        }

        public String getResultMapId() {
            return resultMapId;
        }

        public void setResultMapId(String resultMapId) {
            this.resultMapId = resultMapId;
        }

        public void setSqlId(String sqlId) {
            this.sqlId = sqlId;
        }

        public String getSqlId() {
            return sqlId;
        }

        public Map<String,Integer> getParameterIndexMap() {
            return parameterIndexMap;
        }

        public ResultMap getResultMap() {
            return resultMap;
        }

        public String getSqlString() {
            return sqlString;
        }

        public void setParameterIndexMap(Map<String,Integer> parameterIndexMap) {
            this.parameterIndexMap = parameterIndexMap;
        }

        public void setResultMap(ResultMap resultMap) {
            this.resultMap = resultMap;
        }

        public void setSqlString(String sqlString) {
            this.sqlString = sqlString;
        }
    }

    static class SqlMapInfo {
        private String resource;//sql id属于哪个resource
        private Sql sql;

        public Sql getSql() {
            return sql;
        }

        public void setSql(Sql sql) {
            this.sql = sql;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }
    }
    static class ResultMap{
        private String id;
        private String classz;
        private Map<String, String> propertyMap;

        public Map<String, String> getPropertyMap() {
			return propertyMap;
		}

		public void setPropertyMap(Map<String, String> propertyMap) {
			this.propertyMap = propertyMap;
		}

		public String getClassz() {
            return classz;
        }

        public String getId() {
            return id;
        }

        public void setClassz(String classz) {
            this.classz = classz;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    static class Result{
        private String column;
        private String property;
        private String jdbcType;

        public String getColumn() {
            return column;
        }

        public String getJdbcType() {
            return jdbcType;
        }

        public String getProperty() {
            return property;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public void setJdbcType(String jdbcType) {
            this.jdbcType = jdbcType;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }
}
