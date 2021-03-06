package com.controller;

import com.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

  @RequestMapping(value = "/subLogin",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
  public String subLogoin(User user){
    //
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
    try {
      subject.login(token);
    } catch (AuthenticationException e) {
      return "403";
    }
    return "index";
  }
  @RequestMapping(value = "/hello",method = {RequestMethod.POST,RequestMethod.GET},produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String Hello(){
    return "hello zjc";
  }
}
