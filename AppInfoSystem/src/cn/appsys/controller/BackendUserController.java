package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backenduser.BackendUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/manager")
public class BackendUserController 
{
	private Logger logger = Logger.getLogger(BackendUserController.class);
	@Resource
	private BackendUserService backendUserService;
	
	@RequestMapping("/login")
	public String login()
	{
		return "backendlogin";
	}
	
	@RequestMapping("/dologin")
	public String doLogin(HttpSession session, @RequestParam(value = "userCode")String userCode,
			@RequestParam(value = "userPassword")String userPassword, HttpServletRequest request)
	{
		logger.info("userCode ==================>" + userCode);
		logger.info("userPassword ==================>" + userPassword);
		BackendUser backendUser = backendUserService.backendUserLogin(userCode, null);
		if (backendUser != null)
		{
			if (backendUser.getUserPassword().equals(userPassword))
			{
				session.setAttribute(Constants.BACKEND_USER_SESSION, backendUser);
				return "redirect:/manager/main.html";
			}
			else
			{
				request.setAttribute("error", "密码输入错误！");
			}
		}
		else
		{
			request.setAttribute("error", "用户名不存在！");
		}
		return "backendlogin";
	}
	
	@RequestMapping(value = "/main.html")
	public String main(HttpSession session)
	{
		if (((BackendUser)session.getAttribute(Constants.BACKEND_USER_SESSION)) == null)
		{
			return "redirect:/manager/login";
		}
		
		return "backend/main";
	}
	
	@RequestMapping(value = "/logout")
	public String loginOut(HttpSession session)
	{
		session.invalidate();
		return "redirect:/manager/login";
	}
}
