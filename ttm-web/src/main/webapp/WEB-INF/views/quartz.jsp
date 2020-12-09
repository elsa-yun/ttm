<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>My HTML View</title>
<link href="<c:url value="/resources/form.css" />" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="cacheDB">
	<%@ include file="top.jsp"%>
		<table>
			<tr>
				<td>jobName</td>
				<td>expression</td>
				<td>desc</td>
				<td>status</td>
				<td>update</td>
				<td>operation</td>
			</tr>
			<c:forEach items="${cacheList}" var="person">
				<form:form id="form" method="post" action="quartz/update/${person.jobName}">
					<tr>
						<td><input id="pid${person.jobName}" name="jobName" type="text" value='${person.jobName}' size="20" /></td>
						<td><input id="pna${person.cronExpression}" name="cronExpression" type="text" value='${person.cronExpression}' size="20" /></td>
						<td><input id="pna${person.jobDesc}" name="jobDesc" type="text" value='${person.jobDesc}' size="20" /></td>
						<td><input id="pna${person.triggerStatus}" name="triggerStatus" type="text" value='${person.triggerStatus}' size="20" /></td>
						<td><button type="submit" name="update" class="right-button04">更新时间表达式</button>&nbsp;&nbsp; </td>
						<td><a href="quartz/pause/${person.jobName}">暂停</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="quartz/resume/${person.jobName}">恢复</a>
						&nbsp;&nbsp;<a href="quartz/delete/${person.jobName}">移除</a>
						&nbsp;&nbsp;<a href="quartz/trigger/${person.jobName}">立即运行一次</a>
						</td>
					</tr>
				</form:form>
			</c:forEach>
			
			<c:forEach items="${delList}" var="person">
				<form:form id="form" method="post" action="quartz/addRemoveJob/${person.jobName}">
					<tr>
						<td><input id="pid${person.jobName}" name="jobName"	type="text" value='${person.jobName}' size="20" /></td>
						<td><input id="pna${person.cronExpression}"	name="cronExpression" type="text" value='${person.cronExpression}' size="20" /></td>
						<td><input id="pna${person.jobDesc}" name="jobDesc" type="text" value='${person.jobDesc}' size="20" /></td>
						<td><input id="pna${person.triggerStatus}" name="triggerStatus" type="text" value='${person.triggerStatus}' size="20" /></td>
						<td><button type="submit" name="store"	class="right-button04">store</button>&nbsp;&nbsp; </td>
						<td></td>
					</tr>
				</form:form>
			</c:forEach>
		</table>
		<form:form id="form" method="post" action="quartz/dynamicStore">
		<table>
			<tr>
				<td colspan="2" align="center">动态添加schedule</td>
			</tr>
			<tr>
				<td>目标对象targetObject：</td>
				<td><input id="targetObject" name="targetObject" type="text" value='' size="20" /></td>
			</tr>
			<tr>
				<td>目标方法targetMethod：</td>
				<td><input id="targetMethod" name="targetMethod" type="text" value='' size="20" /></td>
			</tr>
			<tr>
				<td>方法描述jobDesc：</td>
				<td><input id="jobDesc" name="jobDesc" type="text" value='' size="20" /></td>
			</tr>
			<tr>
				<td>任务类型scheduleType：</td>
				<td>
					<select id="triggerType" name="triggerType">
						<option value="simpleTrigger">simpleTrigger</option>
						<option value="cronTrigger">cronTrigger</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>延时startDelay：</td>
				<td><input id="startDelay" name="startDelay" type="text" value='' size="10" />毫秒后启动</td>
			</tr>
			<tr>
				<td>trigger任务表达式：</td>
				<td><input id="cronExpression" name="cronExpression" type="text" value='' size="20" /></td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit" name="store"	class="right-button04">store</button>&nbsp;&nbsp; </td>
			</tr>
		</table>
		</form:form>
	</div>
</body>
</html>