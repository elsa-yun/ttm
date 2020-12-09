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
				<td><strong>type</strong></td>
				<td><strong>操作内容</strong></td>
				<td><strong>结果</strong></td>
				<td><strong>操作人</strong></td>
				<td><strong>创建时间</strong></td>
			</tr>
		</thead>
		<c:forEach items="${opereator_logs}" var="log">
			<tr>
				<td>${log.id}</td>
				<td>${log.job_name}</td>
				<td>${log.job_group_name}</td>
				<td>${log.type}</td>
				<td>${log.content}</td>
				<td><c:if test="${log.success=='1'}">成功</c:if> <c:if
						test="${log.success=='0'}">失败</c:if></td>
				<td>${log.operator}</td>
				<td>${log.create_time}</td>
			</tr>
		</c:forEach>

	</table>

</body>
</html>