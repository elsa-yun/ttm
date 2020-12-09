package com.elsa.ttm.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.elsa.ttm.constant.UsersConstant;
import com.elsa.ttm.core.manager.ServerManager;
import com.elsa.ttm.domain.UsersDO;
import com.elsa.ttm.manager.DBManager;

@Controller
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UsersController extends BaseController {

	@Autowired
	DBManager dbManager;

	@Autowired
	ServerManager serverManager;

	@RequestMapping(value = "/users/add", method = RequestMethod.GET)
	public String add(final ModelMap model, HttpSession session) {
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		if (!role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}
		model.addAttribute("job_group_name_list", serverManager.get_all_job_group_names());
		return "users/add";
	}

	@RequestMapping(value = "/users/edit_pwd", method = RequestMethod.GET)
	public String editPwd(final ModelMap model, HttpSession session) {
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String username = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		model.addAttribute("username", username);
		model.addAttribute("success", 0);
		return "users/edit_pwd";
	}

	@RequestMapping(value = "/users/update_pwd", method = RequestMethod.POST)
	public String updatePwd(final ModelMap model, HttpServletRequest request, HttpSession session) {
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String username = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		String old_password = request.getParameter("old_password");
		String valid_password = request.getParameter("valid_password");
		String password = request.getParameter("password");
		if (password.equals(valid_password)) {
			UsersDO user = new UsersDO();
			user.setUserName(username);
			String md5_pwd = Md5Util.md5pwd(old_password);
			user.setPassWord(md5_pwd);
			Map<String, String> map = dbManager.get_one_user(username, md5_pwd);
			if (null != map && !map.isEmpty() && map.size() > 0) {
				UsersDO updateUser = new UsersDO();
				updateUser.setUserName(username);
				String md5pwd = Md5Util.md5pwd(valid_password);
				updateUser.setPassWord(md5pwd);
				updateUser.setId(Integer.valueOf(map.get("id")));
				dbManager.update_user(updateUser);
				model.addAttribute("success", 1);
			} else {
				model.addAttribute("success", 2);
			}
		}
		return "users/edit_pwd";
	}

	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public String save(final ModelMap model, WebRequest request, HttpSession session) {
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String sessionRole = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		if (!sessionRole.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String valid_password = request.getParameter("valid_password");
		String role = request.getParameter("role");
		Map<String, String[]> appIds = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String[]> entry : appIds.entrySet()) {
			if (entry.getKey().startsWith("group_names_")) {
				String app_id = request.getParameter(entry.getKey());
				if (StringUtils.isNotBlank(app_id)) {
					sb.append(app_id);
					sb.append(",");
				}
			}

		}
		String groupNames = sb.toString();
		if (StringUtils.isNotBlank(groupNames)) {
			groupNames = groupNames.substring(0, groupNames.length() - 1);
		}

		String md5_pwd = Md5Util.md5pwd(password);
		Map<String, String> map = dbManager.get_one_user(username, md5_pwd);
		if (null == map) {
			if (password.equals(valid_password) && StringUtils.isNotBlank(username.trim())) {
				UsersDO users = new UsersDO();
				users.setPassWord(md5_pwd);
				users.setUserName(username);
				users.setGroupNames(groupNames);
				users.setRole(role);
				users.setCreateTime(new Date());
				users.setModifyTime(new Date());
				dbManager.save_user(users);
			}
		}

		return "redirect:/users/list";
	}

	@RequestMapping(value = "/users/list")
	public String list(final ModelMap model, WebRequest request, HttpSession session) {
		String user_id = (String) session.getAttribute(UsersConstant.SESSION_USER_ID);
		String role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		if (StringUtils.isBlank(user_id)) {
			return "redirect:/login";
		}
		if (!role.equals(UsersConstant.ROLE_ADMIN)) {
			return "redirect:/quartz";
		}
		List<Map<String, String>> userList = dbManager.query_all_users();
		if (!CollectionUtils.isEmpty(userList)) {
		}
		model.addAttribute("userList", userList);
		return "users/list";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(final ModelMap model, WebRequest request) {
		return "login";
	}

	@RequestMapping(value = "/login_in", method = RequestMethod.POST)
	public String login_in(final ModelMap model, WebRequest request, HttpSession session) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String passwordToMD5OrSHA = Md5Util.md5pwd(password);
		UsersDO usersDO = new UsersDO();
		usersDO.setUserName(username);
		usersDO.setPassWord(passwordToMD5OrSHA);

		Map<String, String> user = dbManager.get_one_user(username, passwordToMD5OrSHA);
		if (null != user && !CollectionUtils.isEmpty(user)) {
			session.setAttribute(UsersConstant.SESSION_USER_ID, user.get("id"));
			session.setAttribute(UsersConstant.SESSION_USER_NAME, user.get("user_name"));
			session.setAttribute(UsersConstant.SESSION_ROLE, user.get("role"));
			session.setAttribute(UsersConstant.SESSION_GROUP_NAMES, user.get("group_names"));
			return "redirect:/quartz";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/login_out", method = RequestMethod.GET)
	public String login_out(final ModelMap model, WebRequest request, HttpSession session) {

		session.removeAttribute(UsersConstant.SESSION_USER_ID);
		session.removeAttribute(UsersConstant.SESSION_USER_NAME);
		session.removeAttribute(UsersConstant.SESSION_ROLE);
		session.removeAttribute(UsersConstant.SESSION_GROUP_NAMES);

		return "redirect:/login";
	}

}
