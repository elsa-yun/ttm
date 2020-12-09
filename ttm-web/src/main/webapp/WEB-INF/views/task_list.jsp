<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>My HTML View</title>
<link href="<c:url value="/resources/css/common.css" />"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
</head>
<body>
	<div class="cacheDB">
<%@ include file="top.jsp"%>
		<table border="0" cellpadding="4" cellspacing="1" class="edit_table">
			<tr>
				<td><strong>ID</strong></td>
				<td><strong>任务名称</strong></td>
				<td><strong>组名</strong></td>
				<td><strong>时间表达式</strong></td>
				<td><strong>job备注</strong></td>
				<td><strong>环境</strong></td>
				<td><strong>状态</strong></td>
				<td><strong>只运行一次</strong></td>
				<td><strong>更新时间表达式</strong></td>
				<td align="center"><strong>操作</strong></td>
			</tr>
			<c:forEach items="${task_list}" var="task">
				<form:form id="form" method="get">
					<%-- action="quartz/update/${task.job_name}" --%>
					<tr><td>${task.id}</td>
						<td><input id="pid${task.job_name}" name="job_name"
							type="text" value='${task.job_name}' size="20"
							readonly="readonly" /></td>
						<td><input id="pna${task.job_group_name}"
							name="job_group_name" type="text" value='${task.job_group_name}'
							size="25" readonly="readonly" /></td>
						<td><input id="pna${task.cron_expression}"
							name="cron_expression" type="text"
							value='${task.cron_expression}' size="20"
							class="class_cron_${task.job_name}" /> <input
							id="old_cron_${task.job_name}" name="old_cron_expression"
							type="hidden" value='${task.cron_expression}' size="20">
						</td>
						<td><input id="pna${task.job_memo}" name="job_memo"
							type="text" value='${task.job_memo}' size="22"
							readonly="readonly" /></td>
						<td><input id="pna${task.job_environment_type}"
							name="job_environment_type" type="text"
							value='${task.environment_desc}' size="20" readonly="readonly" /></td>
						<td><input id="pna${task.status_desc}" name="status_desc"
							type="text" value='${task.status_desc}' size="5"
							readonly="readonly" /></td>
						<td><input id="pna${task.only_run_once}" name="only_run_once"
							type="text" value='${task.only_run_once_desc}' size="2"
							readonly="readonly" /></td>
						<td><c:if
								test="${task.run_status=='normal' && task.only_run_once!='1' && task.has_operator=='yes'}">
								<button type="button" name="update"
									class="update_cronexpression row_job_${task.job_name}"
									job_name="${task.job_name}"
									job_group_name="${task.job_group_name}">更新时间表达式</button>
							</c:if>&nbsp;</td>
						<td><c:if test="${task.has_operator=='yes'}">
							  <c:if test="${task.only_run_once!='1'}">
								<c:if test="${task.run_status=='normal'}">
									<button type="button"
										url="quartz/pause/${task.job_name}/${task.job_group_name}"
										class="ajax_opreator row_job_${task.job_name}"
										job_name="${task.job_name}"
										job_group_name="${task.job_group_name}">暂停任务</button>&nbsp;
									<button type="button"
										url="quartz/remove/${task.job_name}/${task.job_group_name}"
										class="ajax_opreator row_job_${task.job_name}"
										job_name="${task.job_name}"
										job_group_name="${task.job_group_name}">移除任务</button>&nbsp;
									<button type="button"
										url="quartz/triggerOnce/${task.job_name}/${task.job_group_name}"
										class="ajax_opreator row_job_${task.job_name}"
										job_name="${task.job_name}"
										job_group_name="${task.job_group_name}">立即运行一次</button>&nbsp;
								</c:if>
								<c:if test="${task.run_status=='pause'}">
									<button type="button"
										url="quartz/resume/${task.job_name}/${task.job_group_name}"
										class="ajax_opreator row_job_${task.job_name}"
										job_name="${task.job_name}"
										job_group_name="${task.job_group_name}">恢复</button>&nbsp;&nbsp;</c:if>
								<c:if test="${task.run_status=='remove'}">
									<button type="button"
										url="quartz/addRemoveJob/${task.job_name}/${task.job_group_name}"
										class="ajax_opreator row_job_${task.job_name}"
										job_name="${task.job_name}"
										job_group_name="${task.job_group_name}">从移除中添加</button>&nbsp;&nbsp;</c:if>
							  </c:if>
							</c:if> <a
							href="${contextPath}/trigger/logs/${task.job_name}/${task.job_group_name}"
							target="_blank"><strong>查看运行记录</strong></a>&nbsp;&nbsp; <a
							href="${contextPath}/opereator/logs/${task.job_name}/${task.job_group_name}"
							target="_blank"><strong>查看操作记录</strong></a>&nbsp;&nbsp;<c:if test="${session_role=='admin'}"> <a
							href="${contextPath}/quartz/zk/${task.job_name}/${task.job_group_name}"
							target="_blank"><strong>查看node</strong></a></c:if></td>
					</tr>
				</form:form>
			</c:forEach>

			<c:forEach items="${delList}" var="person">
				<form:form id="form" method="post"
					action="quartz/addRemoveJob/${person.jobName}">
					<tr>
						<td><input id="pid${person.jobName}" name="jobName"
							type="text" value='${person.jobName}' size="20" /></td>
						<td><input id="pna${person.cronExpression}"
							name="cronExpression" type="text"
							value='${person.cronExpression}' size="20" /></td>
						<td><input id="pna${person.jobDesc}" name="jobDesc"
							type="text" value='${person.jobDesc}' size="20" /></td>
						<td><input id="pna${person.triggerStatus}"
							name="triggerStatus" type="text" value='${person.triggerStatus}'
							size="20" /></td>
						<td><button type="submit" name="store" class="right-button04">store</button>&nbsp;&nbsp;
						</td>
						<td></td>
					</tr>
				</form:form>
			</c:forEach>
		</table>
		<%-- 	<form:form id="form" method="post" action="quartz/dynamicStore">
			<table
				style="border-style: dashed; border-color: windowframe; border-width: 1;">
				<tr>
					<td colspan="2" align="center">动态添加schedule</td>
				</tr>
				<tr>
					<td>JOB名称：</td>
					<td><input id="job_name" name="job_name" type="text" value=''
						size="20" /></td>
				</tr>
				<tr>
					<td>job所属组名：</td>
					<td><select id="job_group_name" name="job_group_name"
						onchange="return change_ips();">
							<c:forEach items="${job_group_name_list}" var="group">
								<option value="">请选择任务所属组名</option>
								<option value="${group.key}">${group.key}</option>
							</c:forEach>
					</select> <c:forEach items="${job_group_name_list}" var="group">
							<input type="hidden" id="ips_${group.key}"
								name="ips_${group.key}" value="${group.value}"
								class="group_name_ips">
						</c:forEach></td>
				</tr>
				<tr>
					<td>任务运行类型：</td>
					<td><select id="job_environment_type"
						name="job_environment_type" onchange="return change_agrments();">
							<option value="">请选择任务节点类型</option>
							<option value="single">单节点任务(同一任务同一时刻只在集群中一台机器运行)</option>
							<option value="distribute">多节点任务(同一任务同一时记得在指定ip列表上运行)</option>
					</select></td>
				</tr>
				<tr>
					<td>目标对象targetObject：</td>
					<td><input id="targetObject" name="targetObject" type="text"
						value='' size="20" /></td>
				</tr>
				<tr>
					<td>目标方法targetMethod：</td>
					<td><input id="targetMethod" name="targetMethod" type="text"
						value='' size="20" /></td>
				</tr>

				<tr>
					<td class="class_ips">运行机器IP列表：</td>
					<td class="class_ips"><input id="ips" name="ips" type="text"
						value='' size="80" readonly="readonly" /></td>
				</tr>
				<tr>
					<td class="agrments">方法参数agrments：</td>
					<td class="agrments"><input id="params" name="params"
						type="text" value='' size="80" /></td>
				</tr>
				<tr>
					<td>方法描述jobDesc：</td>
					<td><input id="jobDesc" name="jobDesc" type="text" value=''
						size="20" /></td>
				</tr>
				<tr>
					<td>任务类型scheduleType：</td>
					<td><select id="triggerType" name="triggerType"
						onchange="return change_trigger();">
							<option value="">请选择时间触发器类型</option>
							<option value="simpleTrigger">simpleTrigger</option>
							<option value="cronTrigger">cronTrigger</option>
					</select></td>
				</tr>
				<tr>
					<td class="class_start_delay">延时startDelay：</td>
					<td class="class_start_delay"><input id="startDelay"
						name="startDelay" type="text" value='' size="10" />毫秒后启动</td>
				</tr>
				<tr>
					<td class="class_trigger">trigger任务表达式：</td>
					<td class="class_trigger"><input id="cronExpression"
						name="cronExpression" type="text" value='' size="20" /></td>
				</tr>
				<tr>
					<td>运行次数：</td>
					<td><select id="runTimes" name="runTimes">
							<option value="">请选择运行次数</option>
							<option value="once">只运行一次</option>
							<option value="expression">按表达式运行</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><button type="submit"
							name="store" class="right-button04"
							onclick="return validatorForm();">保存JOB</button>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form:form> --%>

		<%-- 		<form:form id="form" method="post"
			action="${contextPath}/quartz/delete/ip">
			<table
				style="border-style: dashed; border-color: windowframe; border-width: 1;">
				<tr>
					<td colspan="2" align="center">删除某个组下不存在的IP</td>
				</tr>
				<tr>
					<td>job所属组名：</td>
					<td><select id="job_group_name" name="job_group_name">
							<c:forEach items="${job_group_name_list}" var="group">
								<option value="">请选择任务所属组名</option>
								<option value="${group.key}">${group.key}=>${group.value}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>要删除的IP：</td>
					<td><input id="delete_ip" name="delete_ip" type="text"
						value='' size="20" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><button type="submit"
							name="store" class="right-button04">删除不存在的IP</button>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form:form> --%>

	</div>

	<script type="text/javascript">
		$(".update_cronexpression").click(function() {
			var job_name = $(this).attr('job_name');
			var job_group_name = $(this).attr('job_group_name');
			var expression = jQuery(".class_cron_" + job_name).val();
			var url = "${contextPath}/quartz/update/cron";

			var old_cron_expression = jQuery("#old_cron_" + job_name).val();
			if (expression == old_cron_expression) {
				alert("时间表达式值新值与旧值相同，请重新修改后再提交！");
				return false;
			}

			jQuery(".row_job_" + job_name).attr({
				"disabled" : "disabled"
			});
			$.get(url, {
				cron_expression : expression,
				job_group_name : job_group_name,
				job_name : job_name
			}, function(data) {
				var json = jQuery.parseJSON(data);
				var suc = json.success;
				if (suc) {
					alert("操作成功");
				}  else {
					if (json.errorCode == 'exists_other_opreator') {
						alert("操作失败,存在其他的操作,请稍后重试！");
					} else if (json.errorCode == 'has_no_auth') {
						alert("操作失败,您没有此组的操作权限");
					} else if (json.errorCode == 'has_no_result') {
						alert("操作失败,没有任何节点IP操作成功,失败IP为："
								+ json.failIps
								+ ",请稍后重试！");
					} else if (json.errorCode == 'opreator_fail') {
						alert("操作失败,失败IP为："
								+ json.failIps
								+ ",请稍后重试！");
					} else {
						alert("操作失败,失败IP为："
								+ json.failIps
								+ ",请稍后重试！");
					}
				}
				location.reload();
			});
		});

		$(".ajax_opreator")
				.click(
						function() {
							var url = "${contextPath}/"
									+ $(this).attr('url');
							var job_name = $(this).attr('job_name');
							var job_group_name = $(this).attr('job_group_name');
							var expression = jQuery(".class_" + job_name).val();
							jQuery(".row_job_" + job_name).attr({
								"disabled" : "disabled"
							});
							$
									.get(
											url,
											{
												cron_expression : expression,
												job_group_name : job_group_name,
												job_name : job_name
											},
											function(data) {
												var json = jQuery
														.parseJSON(data);
												var suc = json.success;
												if (suc) {
													alert("操作成功");
												} else {
													if (json.errorCode == 'exists_other_opreator') {
														alert("操作失败,存在其他的操作,请稍后重试！");
													} else if (json.errorCode == 'has_no_auth') {
														alert("操作失败,您没有此组的操作权限");
													} else if (json.errorCode == 'has_no_result') {
														alert("操作失败,没有任何节点IP操作成功,失败IP为："
																+ json.failIps
																+ ",请稍后重试！");
													} else if (json.errorCode == 'opreator_fail') {
														alert("操作失败,失败IP为："
																+ json.failIps
																+ ",请稍后重试！");
													} else {
														alert("操作失败,失败IP为："
																+ json.failIps
																+ ",请稍后重试！");
													}
												}
												location.reload();
											});
						});

		jQuery(".agrments").hide();
		jQuery(".class_trigger").hide();
		jQuery(".class_start_delay").hide();

		function change_ips() {
			var job_group_name = jQuery("#job_group_name").val();
			var ips = jQuery("#ips_" + job_group_name).val();
			if (job_group_name == "") {
				jQuery("#ips").val("");
			} else {
				jQuery("#ips").val(ips);
			}
		}

		function change_agrments() {
			var job_environment_type = jQuery("#job_environment_type").val();
			if (job_environment_type == "distribute") {
				jQuery(".agrments").show();
			}
			if (job_environment_type == "single") {
				jQuery(".agrments").hide();
			}
		}

		function change_trigger() {
			var triggerType = jQuery("#triggerType").val();
			if (triggerType == "simpleTrigger") {
				jQuery(".class_start_delay").show();
				jQuery(".class_trigger").hide();
			}
			if (triggerType == "cronTrigger") {
				jQuery(".class_start_delay").hide();
				jQuery(".class_trigger").show();
			}
		}

		function validatorForm() {
			var job_environment_type = jQuery("#job_environment_type").val();
			var ips = jQuery("#ips").val();

			var targetObject = jQuery("#targetObject").val();
			var targetMethod = jQuery("#targetMethod").val();

			var jobDesc = jQuery("#jobDesc").val();
			var params = jQuery("#params").val();
			var triggerType = jQuery("#triggerType").val();

			var startDelay = jQuery("#startDelay").val();
			var cronExpression = jQuery("#cronExpression").val();

			var runTimes = jQuery("#runTimes").val();
			var job_name = jQuery("#job_name").val();
			var job_group_name = jQuery("#job_group_name").val();

			if (job_name.trim().length <= 0) {
				alert("请输入job名称！");
				return false;
			}
			if (job_group_name.trim().length <= 0) {
				alert("请选择job所在的组！");
				return false;
			}
			if (job_environment_type.trim().length <= 0) {
				alert("请选择任务节点类型！");
				return false;
			}
			if (targetObject.trim().length <= 0) {
				alert("请填写目标对象！");
				return false;
			}
			if (targetMethod.trim().length <= 0) {
				alert("请填写目标方法！");
				return false;
			}
			if (ips.trim().length <= 0) {
				alert("请填写方法运行IP列表");
				return false;
			}
			if (job_environment_type == "distribute") {
				if (params.trim().length <= 0) {
					alert("请填写方法运行参数值");
					return false;
				}
			}
			if (jobDesc.trim().length <= 0) {
				alert("请填写方法描述！");
				return false;
			}
			if (triggerType.trim().length <= 0) {
				alert("请选择时间触发器类型！");
				return false;
			}
			if (triggerType == "simpleTrigger") {
				if (startDelay.trim().length <= 0) {
					alert("请填写延时startDelay毫秒数");
					return false;
				}
			}
			if (triggerType == "cronTrigger") {
				if (cronExpression.trim().length <= 0) {
					alert("请填写trigger任务表达式");
					return false;
				}
			}
			if (runTimes.trim().length <= 0) {
				alert("请选择运行次数！");
				return false;
			}
			if (triggerType == "simpleTrigger") {
				if (runTimes.trim() != "once") {
					alert("simpleTrigger的运行次数必须选择为：只运行一次！");
					return false;
				}
			}
			if (triggerType == "cronTrigger") {
				if (runTimes.trim() != "expression") {
					alert("cronTrigger的运行次数必须选择为：按表达式运行！");
					return false;
				}
			}
		}
	</script>
</body>
</html>