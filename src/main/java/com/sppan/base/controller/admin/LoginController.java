package com.sppan.base.controller.admin;

import com.sppan.base.common.JsonResult;
import com.sppan.base.common.utils.MD5Utils;
import com.sppan.base.controller.BaseController;
import com.sppan.base.controller.BaseController;

import com.sppan.base.entity.User;
import com.sppan.base.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
	public String login() {

		return "admin/login";
	}
	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,ModelMap model
			) {
		try {
			 Subject subject = SecurityUtils.getSubject();
			 UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			return redirect("/admin/index");
		} catch (AuthenticationException e) {
			model.put("message", e.getMessage());
		}
		return "admin/login";
	}
	
	@RequestMapping(value = { "/admin/logout" }, method = RequestMethod.GET)
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return redirect("admin/login");
	}

	@RequestMapping(value = { "/admin/repassword" }, method = RequestMethod.GET)
	public String repassword(){
		return "admin/repassword";
	}

	@ResponseBody
	@RequestMapping(value = "/admin/savePasswd", method = RequestMethod.POST)
	public JsonResult savePasswd(String password, String newPassword, String reNewPassword, ModelMap map) {
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			Assert.state(!password.equals(newPassword),"新密码不能和原密码一样");
			Assert.state(user.getPassword().equals(MD5Utils.md5(password)),"原密码不正确");
			Assert.state(newPassword.equals(reNewPassword),"您输入的密码与确认密码不一致");

			user.setPassword(MD5Utils.md5(newPassword));
			this.userService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
}
