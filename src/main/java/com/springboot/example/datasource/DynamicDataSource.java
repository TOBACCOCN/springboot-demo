package com.springboot.example.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @author TOBACCO
 * @date 2022.12.22
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DynamicDataSourceNameEnum> CONTEXT_HOLDER = new ThreadLocal<>();

    public DynamicDataSource(Map<Object, Object> targetDataSources, Object defaultTargetDataSource) {
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void setDataSource(DynamicDataSourceNameEnum nameEnum) {
        CONTEXT_HOLDER.set(nameEnum);
    }

    public static DynamicDataSourceNameEnum getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

}
