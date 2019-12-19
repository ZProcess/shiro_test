package com;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class SimpleAuthenticationTest {
  SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
  @Before
  public void  addUser(){
    simpleAccountRealm.addAccount("mark","1234567","admin","user");
  }
  @Test
  public void testAuthenticationTest(){

    //构建环境
    DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
    defaultSecurityManager.setRealm(simpleAccountRealm);

    SecurityUtils.setSecurityManager(defaultSecurityManager);
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("mark","1234567");

    subject.login(token);
    System.out.println(subject.isAuthenticated());
    subject.checkRole("admin");
  }
}
