<%@ page language="java" pageEncoding="UTF-8"%>
		<table border="0" cellpadding="4" cellspacing="0" width="80%" align="center">
			<tr>
				<td colspan="7">&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td><strong><a
						href="${contextPath}/quartz">查看所有任务</a></strong></td>
				<td><%-- <c:if test="${session_role=='admin'}"><strong><a
						href="${contextPath}/dynamic">动态添加任务</a></strong></c:if> --%></td>
				<td><strong><a
						href="${contextPath}/users/edit_pwd">修改密码</a></strong></td>
				<td><strong>当前用户：${session_user_name},<a
						href="${contextPath}/login_out">退出</a></strong></td>
				<td>
				<c:if test="${session_role=='admin'}">
				<strong><a
						href="${contextPath}/users/add">添加用户</a></strong>
						
				</c:if>		
						</td>
				<td><c:if test="${session_role=='admin'}"><strong><a
						href="${contextPath}/users/list">查看用户</a></strong></c:if>	</td>
				<td><c:if test="${session_role=='admin'}"><strong><a
						href="${contextPath}/dynamic/clear/result">手式清除动态添加结果</a></strong></c:if>	</td>
			</tr>
			<tr>
				<td colspan="7">&nbsp;&nbsp;</td>
			</tr>
		</table>