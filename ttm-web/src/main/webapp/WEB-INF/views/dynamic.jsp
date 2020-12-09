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
		<form:form id="form" method="post"
			action="${contextPath}/dynamic/store">
			<table border="0" cellpadding="4" cellspacing="1" class="edit_table">
				<thead>
					<tr>
						<td colspan="2" align="center"><strong>动态添加schedule</strong></td>
					</tr>
				</thead>
				<tr>
					<td class="addtable_left">JOB名称：</td>
					<td class="leftTD"><input id="job_name" name="job_name"
						type="text" value='' size="20" /></td>
				</tr>
				<tr>
					<td class="addtable_left">job所属组名：</td>
					<td class="leftTD"><select id="job_group_name"
						name="job_group_name" onchange="return change_ips();">
								<option value="">请选择任务所属组名</option>
							<c:forEach items="${job_group_name_list}" var="group">
								<option value="${group.key}">${group.key}</option>
							</c:forEach>
					</select> <c:forEach items="${job_group_name_list}" var="group">
							<input type="hidden" id="ips_${group.key}"
								name="ips_${group.key}" value="${group.value}"
								class="group_name_ips">
						</c:forEach></td>
				</tr>
				<tr>
					<td class="addtable_left">任务运行类型：</td>
					<td class="leftTD"><select id="job_environment_type"
						name="job_environment_type" onchange="return change_agrments();">
							<option value="">请选择任务节点类型</option>
							<option value="single">单节点任务(同一任务同一时刻只在集群中一台机器运行)</option>
							<option value="distribute">多节点任务(同一任务同一时记得在指定ip列表上运行)</option>
					</select></td>
				</tr>
				<tr>
					<td class="addtable_left">目标对象targetObject：</td>
					<td class="leftTD"><input id="targetObject"
						name="targetObject" type="text" value='' size="20" /></td>
				</tr>
				<tr>
					<td class="addtable_left">目标方法targetMethod：</td>
					<td class="leftTD"><input id="targetMethod"
						name="targetMethod" type="text" value='' size="20" /></td>
				</tr>

				<tr>
					<td class="class_ips addtable_left">运行机器IP列表：</td>
					<td class="class_ips leftTD"><input id="ips" name="ips"
						type="text" value='' size="120" readonly="readonly" /></td>
				</tr>
				<tr>
					<td class="agrments addtable_left">方法参数agrments：</td>
					<td class="agrments leftTD"><input id="params" name="params"
						type="text" value='' size="120" /></td>
				</tr>
				<tr>
					<td class="addtable_left">方法描述jobDesc：</td>
					<td class="leftTD"><input id="jobDesc" name="jobDesc"
						type="text" value='' size="20" /></td>
				</tr>
				<tr>
					<td class="addtable_left">任务类型scheduleType：</td>
					<td class="leftTD"><select id="triggerType" name="triggerType"
						onchange="return change_trigger();">
							<option value="">请选择时间触发器类型</option>
							<option value="simpleTrigger">simpleTrigger</option>
							<option value="cronTrigger">cronTrigger</option>
					</select></td>
				</tr>
				<tr>
					<td class="class_start_delay addtable_left">延时startDelay：</td>
					<td class="class_start_delay leftTD"><input id="startDelay"
						name="startDelay" type="text" value='' size="10" />毫秒后启动</td>
				</tr>
				<tr>
					<td class="class_trigger addtable_left">trigger时间表达式：</td>
					<td class="class_trigger leftTD"><input id="cronExpression"
						name="cronExpression" type="text" value='' size="20" /></td>
				</tr>
				<tr>
					<td class="addtable_left">运行次数：</td>
					<td class="leftTD"><select id="runTimes" name="runTimes">
							<option value="">请选择运行次数</option>
							<option value="once">只运行一次</option>
							<option value="expression">按表达式运行</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><button type="submit"
							name="store" class="dynamic_add_job"
							onclick="return validatorForm();">保存JOB</button>(提交保存后请等待六秒钟，出现操作结果界面再操作)&nbsp;&nbsp;</td>
					<!--  -->
				</tr>
			</table>
		</form:form>
		<br> <br>
		<c:if test="${session_role=='admin'}">
			<form:form id="form" method="post"
				action="${contextPath}/dynamic/delete/ip">
				<table border="0" cellpadding="4" cellspacing="1" class="edit_table">
					<tr>
						<td colspan="2" align="center"><strong>删除某个组下不存在的IP</strong></td>
					</tr>
					<tr>
						<td class="addtable_left">job所属组名：</td>
						<td class="leftTD"><select id="job_group_name"
							name="job_group_name" class="delete_job_group_name">
									<option value="">请选择任务所属组名</option>
								<c:forEach items="${job_group_name_list}" var="group">
									<option value="${group.key}">${group.key}=>${group.value}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td class="addtable_left">要删除的IP：</td>
						<td class="leftTD"><input id="delete_ip" name="delete_ip"
							type="text" value='' size="20" /></td>
					</tr>
					<tr>
						<td colspan="2" align="center"><button type="submit"
								name="store" class="right-button04"
								onclick="return validator_form_delete_ip();">删除IP</button>&nbsp;&nbsp;</td>
					</tr>
				</table>
			</form:form>

			<br>
			<br>
			<form:form id="form" method="post"
				action="${contextPath}/dynamic/replace/ip">
				<table border="0" cellpadding="4" cellspacing="1" class="edit_table">
					<tr>
						<td colspan="2" align="center"><strong>替换任务所属IP地址</strong></td>
					</tr>
					<tr>
						<td class="addtable_left">job所属组名：</td>
						<td class="leftTD"><select id="job_group_name"
							name="job_group_name" class="replace_job_group_name">
									<option value="">请选择任务所属组名</option>
								<c:forEach items="${job_group_name_list}" var="group">
									<option value="${group.key}">${group.key}=>${group.value}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td class="addtable_left">job名称：</td>
						<td class="leftTD"><input id="job_name" name="job_name"  class="replace_job_name"
							type="text" value='' size="20" /></td>
					</tr>
					<tr>
						<td class="addtable_left">替换IP：</td>
						<td class="leftTD"><input id="source_ip" name="source_ip"
							type="text" value='' size="20" />&nbsp;&nbsp;替换为&nbsp;<input id="replace_ip" name="replace_ip"
							type="text" value='' size="20" />&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><button type="submit"
								name="store" class="right-button04"
								onclick="return validator_form_replace_ip();">替换IP</button>&nbsp;&nbsp;</td>
					</tr>
				</table>
			</form:form>
		</c:if>
	</div>

	<script type="text/javascript">
		function validator_form_replace_ip() {
			var job_group_name = jQuery(".replace_job_group_name").val();
			var source_ip = jQuery("#source_ip").val();
			var job_name = jQuery(".replace_job_name").val();
			var replace_ip = jQuery("#replace_ip").val();
			if (job_group_name == "") {
				alert("请选择JOB所属组名！")
				return false;
			}
			if (job_name == "") {
				alert("请填写JOB名！")
				return false;
			}
			if (source_ip == "") {
				alert("请填写要源IP！")
				return false;
			}
			if (replace_ip == "") {
				alert("请填写替换结果IP！")
				return false;
			}
		}
		function validator_form_delete_ip() {
			var job_group_name = jQuery(".delete_job_group_name").val();
			var delete_ip = jQuery("#delete_ip").val();
			if (job_group_name == "") {
				alert("请选择JOB所属组名！")
				return false;
			}
			if (delete_ip == "") {
				alert("请填写要删除的IP！")
				return false;
			}
		}

		$(".update_cronexpression").click(function() {
			var job_name = $(this).attr('job_name');
			var job_group_name = $(this).attr('job_group_name');
			var expression = jQuery(".class_cron_" + job_name).val();
			var url = "${contextPath}/quartz/update/cron";
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
				} else {
					alert("操作失败,失败IP为：" + json.failIps + ",请稍后重试！");
				}
				location.reload();
			});
		});

		$(".ajax_opreator")
				.click(
						function() {
							var url = "${contextPath}/" + $(this).attr('url');
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