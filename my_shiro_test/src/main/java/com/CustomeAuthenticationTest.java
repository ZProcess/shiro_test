package com;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomeAuthenticationTest {


    @Test
    public void testAuthenticationTest(){
      CustomRealm customRealm = new CustomRealm();
      //构建环境
      DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
      defaultSecurityManager.setRealm(customRealm);

      HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
      matcher.setHashAlgorithmName("md5");
      matcher.setHashIterations(1);
      customRealm.setCredentialsMatcher(matcher);
      SecurityUtils.setSecurityManager(defaultSecurityManager);
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken token = new UsernamePasswordToken("Mark","124");

      subject.login(token);
      System.out.println(subject.isAuthenticated());
      subject.checkRole("admin");
      subject.checkPermission("user:delete");
    }
}
