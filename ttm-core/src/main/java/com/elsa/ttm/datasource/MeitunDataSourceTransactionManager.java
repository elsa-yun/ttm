package com.elsa.ttm.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class MeitunDataSourceTransactionManager extends DataSourceTransactionManager {

	private static final long serialVersionUID = -7775738079919982096L;

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		ContextHolder.clearCustomerKey();
	}

}
