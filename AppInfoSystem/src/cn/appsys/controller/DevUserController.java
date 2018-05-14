package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/dev")
public class DevUserController 
{
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value = "/login")
	public String devLogin()
	{
		return "devlogin";
	}
	
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(@RequestParam String devCode, @RequestParam String devPassword, HttpServletRequest request, HttpSession session)
	{
		DevUser devUser = devUserService.getLoginUserByDevCode(devCode, devPassword);
		if (null != devUser)
		{
			session.setAttribute(Constants.DEV_USER_SESSION, devUser);
			return "redirect:/dev/main.html";
		}
		request.setAttribute("error", "用户名或密码不正确");
		return "devlogin";
	}
	
	@RequestMapping(value = "/main.html")
	public String main(HttpSession session)
	{
		if (((DevUser)session.getAttribute(Constants.DEV_USER_SESSION)) == null)
		{
			return "redirect:/dev/login";
		}
		return "developer/main";
	}
	
	@RequestMapping(value = "logout")
	public String loginout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/dev/login";
	}
}
