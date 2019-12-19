package com;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class IniAuthenticationTest {


    @Test
    public void testAuthenticationTest(){
      IniRealm iniRealm = new IniRealm("classpath:user.ini");
      //构建环境
      DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
      defaultSecurityManager.setRealm(iniRealm);

      SecurityUtils.setSecurityManager(defaultSecurityManager);
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken token = new UsernamePasswordToken("Tom","1234");

      subject.login(token);
      System.out.println(subject.isAuthenticated());
      subject.checkRole("admin");
      subject.checkPermission("upate");
    }
}
