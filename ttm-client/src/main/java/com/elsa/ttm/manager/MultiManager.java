package com.elsa.ttm.manager;

import java.util.List;

import com.elsa.ttm.schedule.IMultiSchedule;

/**
 * @author longhaisheng
 *
 */
public class MultiManager implements IMultiSchedule {

	@Override
	public boolean execute(List<Long> params) throws Exception {
		return true;
	}

}
