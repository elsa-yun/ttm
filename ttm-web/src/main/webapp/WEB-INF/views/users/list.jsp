<%@ include file="../common.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<title>修改配置文件名</title>
<link href="<c:url value="/resources/css/common.css" />"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
</head>
<body>
	<div id="formsContent">
		<%@ include file="../top.jsp"%>
		<table border="0" cellpadding="4" cellspacing="1" class="edit_table">
			<thead>
				<tr style="background: #eee;">
					<th width="5%" align="center">ID</th>
					<th>用户名</th>
					<th>角色</th>
					<th>是否启用</th>
					<th>可操作任务组</th>
					<th>添加时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user">
					<tr>
						<td>${user.id}<input id="user_id" name="user_id"
							type="hidden" value="${user.id}"></td>
						<td>${user.user_name}</td>
						<td><c:if test="${user.role eq 'tl'}">TL</c:if> <c:if
								test="${user.role eq 'opm'}">运维</c:if> <c:if
								test="${user.role eq 'admin'}">管理员</c:if></td>
						<td><c:choose>
								<c:when test="${user.is_delete=='0'}">启用</c:when>
								<c:otherwise>禁用</c:otherwise>
							</c:choose></td>
						<td><c:if test="${user.role eq 'tl'}">${user.group_names}</c:if>
							<c:if test="${user.role eq 'opm'}">审核并修改所有申请</c:if> <c:if
								test="${user.role eq 'admin'}">所有</c:if></td>
						<td>${user.create_time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
