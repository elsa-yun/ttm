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
				<td colspan="2" align="center">
				<c:if test="${errorCode!=''}">${errorCode}&nbsp;&nbsp;
					<c:if test="${success=='0'}">
						<a href="${contextPath}/dynamic/check">重新check</a>
					</c:if>
				</c:if>
				<c:if test="${success=='1'}">
					动态添加jobName:${jobName}成功,<a href="${contextPath}/quartz">返回列表</a>
				</c:if>
				</td>
			</tr>
		</table>

	</div>
</body>
</html>