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
	<table border="0" cellpadding="4" cellspacing="1" class="edit_table">
		<thead>
			<tr>
				<td><strong>ID</strong></td>
				<td><strong>任务名称</strong></td>
				<td><strong>组名</strong></td>
				<td><strong>时间表达式</strong></td>
				<td><strong>运行时信息</strong></td>
				<td><strong>方法耗时(毫秒)</strong></td>
				<td><strong>运行节点IP</strong></td>
				<td><strong>创建时间</strong></td>
			</tr>
		</thead>
		<c:forEach items="${trigger_logs}" var="log">
			<tr>
				<td>${log.id}</td>
				<td>${log.job_name}</td>
				<td>${log.job_group_name}</td>
				<td>${log.cron_expression}</td>
				<td>${log.run_memo}</td>
				<td>${log.consum_time}</td>
				<td>${log.ip}</td>
				<td>${log.create_time}222</td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>