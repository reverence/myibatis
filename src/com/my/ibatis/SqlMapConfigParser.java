package com.my.ibatis;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.tools.corba.se.idl.StringGen;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tufei on 2018/1/1.
 */
public class SqlMapConfigParser {

    /**
     * 解析sqlMapConfig
     * @param configFile
     * @return
     */
    public static SqlMapConfig parseSqlMap(String configFile) throws Exception{
        /**
         * resource
         */
        List<String> resources = null;
        InputStream is = SqlMapConfigParser.class.getResourceAsStream(configFile);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(is);
        Element element = document.getRootElement();
        switch (element.getName()){
            case "sqlmap":
                resources = parseSqlMapResource(element);
                break;
        }

        /**
         * get sqlmapconfig
         */
        return parseSqlMapConfig(resources);
    }

    private static SqlMapConfig parseSqlMapConfig(List<String> resources) throws Exception {
        SqlMapConfig config = new SqlMapConfig();
        for(String resource : resources){
            InputStream is = SqlMapConfigParser.class.getResourceAsStream(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(is);
            Element element = document.getRootElement();
            if("sqlMap".equalsIgnoreCase(element.getName())){
                String namespace = element.attributeValue("namespace");

                Map<String,SqlMapConfig.ResultMap> rMap = new HashMap<String,SqlMapConfig.ResultMap>();

                List<Element> elements = element.elements();
                for(Element element1 : elements){
                    SqlMapConfig.SqlMapInfo sqlMapInfo = new SqlMapConfig.SqlMapInfo();
                    sqlMapInfo.setResource(resource);
                    String name = element1.getName();
                    switch (name) {
                        case "resultMap":
                            SqlMapConfig.ResultMap resultMap = parseResultMap(element1);
                            resultMap.setId(namespace+"."+resultMap.getId());
                            if (rMap.containsKey(resultMap.getId())) {
                                throw new RuntimeException("repeat definition for " + element1.getName());
                            }
                            rMap.put(resultMap.getId(), resultMap);
                            break;
                        case "select":
                        case "update":
                        case "delete":
                            SqlMapConfig.Sql sql = parseSql(element1);
                            sql.setSqlId(namespace+"."+sql.getSqlId());
                            if (config.getMappedSql().containsKey(sql.getSqlId())) {
                                throw new RuntimeException("repeat definition for " + element1.getName());
                            }
                            SqlMapConfig.ResultMap resultMap1 = rMap.get(namespace+"."+sql.getResultMapId());
                            sql.setResultMap(resultMap1);
                            sqlMapInfo.setSql(sql);
                            if (null == config.getMappedSql()) {
                                config.setMappedSql(new HashMap<String, SqlMapConfig.SqlMapInfo>());
                            }
                            config.getMappedSql().put(sql.getSqlId(), sqlMapInfo);
                            break;
                    }
                }

            }else{
                throw  new RuntimeException("wrong tag for sqlmap file");
            }
        }
        return config;
    }

    private static SqlMapConfig.Sql parseSql(Element element1) throws Exception {
        SqlMapConfig.Sql sql = new SqlMapConfig.Sql();
        sql.setResultMapId(element1.attributeValue("resultMap"));
        sql.setSqlId(element1.attributeValue("id"));
        String parameterclass = element1.attributeValue("parameterClass");
        sql.setParameterClass(TypeHandlerFactory.getTypeHandler(parameterclass).getParameterClass(parameterclass));
        String sqlString = element1.getStringValue();
        List<String>variables = new ArrayList<String>();
        int first = sqlString.indexOf("#");
        int next;
        while(-1 != (next=sqlString.indexOf("#",first+1))){
            variables.add(sqlString.substring(first+1,next));
            first = next;
        }
        Map<String,Integer> parameterIndex = new HashMap<>();
        for(int i=0;i<variables.size();i++){
            parameterIndex.put(variables.get(i),i);
            sqlString = sqlString.replace("#"+variables.get(i)+"#","?");
        }
        sql.setParameterIndexMap(parameterIndex);
        sql.setSqlString(sqlString);
        return sql;
    }

    private static SqlMapConfig.ResultMap parseResultMap(Element element1) {
        SqlMapConfig.ResultMap resultMap = new SqlMapConfig.ResultMap();
        String id = element1.attributeValue("id");
        String classz = element1.attributeValue("class");
        resultMap.setId(id);
        resultMap.setClassz(classz);

        List<Element> elementList = element1.elements("result");
        for(Element element : elementList){
            SqlMapConfig.Result result = new SqlMapConfig.Result();
            String column = element.attributeValue("column");
            String property = element.attributeValue("property");
            String jdbcType = element.attributeValue("jdbcType");
            result.setColumn(column);
            result.setJdbcType(jdbcType);
            result.setProperty(property);
            if(null == resultMap.getResultList()){
                resultMap.setResultList(new ArrayList<SqlMapConfig.Result>());
            }
            resultMap.getResultList().add(result);
        }
        return resultMap;
    }

    private static List<String> parseSqlMapResource(Element element) {
        List<String> resources = new ArrayList<String>();
        String res =  element.attributeValue("resource");
        resources.add(res);
        return resources;
    }
}
