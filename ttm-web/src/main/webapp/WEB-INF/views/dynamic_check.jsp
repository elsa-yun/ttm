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
		<table
			style="border-style: dashed; border-color: windowframe; border-width: 1;">
			<tr>
				<td colspan="2" align="center"><c:if
						test="${result.success=='true'}">动态添加jobName:${jobName}成功!&nbsp;&nbsp;</c:if>
					<c:if test="${result.success=='false'}">
						check失败，<a href="${contextPath}/dynamic/check">再 check一次</a>
					</c:if></td>
			</tr>
		</table>

	</div>
</body>
</html>