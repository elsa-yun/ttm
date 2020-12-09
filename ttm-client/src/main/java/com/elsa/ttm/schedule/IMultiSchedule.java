package com.elsa.ttm.schedule;

import java.util.List;

/**
 * @author longhaisheng 多节点任务处理
 */
public interface IMultiSchedule {

	public boolean execute(List<Long> params) throws Exception;
}
