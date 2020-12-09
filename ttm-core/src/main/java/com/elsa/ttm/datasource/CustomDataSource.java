package com.elsa.ttm.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CustomDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return ContextHolder.getCustomerKey();
	}
	
}
