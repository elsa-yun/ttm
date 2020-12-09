package com.elsa.ttm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.elsa.ttm.constant.UsersConstant;

public class BaseController {

	@ModelAttribute
	public void ajaxAttribute(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String session_user_name = (String) session.getAttribute(UsersConstant.SESSION_USER_NAME);
		String session_role = (String) session.getAttribute(UsersConstant.SESSION_ROLE);
		model.addAttribute("session_user_name", session_user_name);
		model.addAttribute("session_role", session_role);
		String contextPath = request.getContextPath();
		model.addAttribute("contextPath", contextPath);
	}
}
