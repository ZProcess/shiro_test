package com;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcAuthenticationTest {

    DruidDataSource druidDataSource = new DruidDataSource();
    {
      druidDataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
      druidDataSource.setUsername("root");
      druidDataSource.setPassword("root");

    }
    @Test
    public void testAuthenticationTest(){
      JdbcRealm jdbcRealm = new JdbcRealm();
      jdbcRealm.setDataSource(druidDataSource);
      jdbcRealm.setAuthenticationQuery("select password from users where user_name = ? ");
      jdbcRealm.setUserRolesQuery("select role_name from user_roles where user_name = ?");
      jdbcRealm.setPermissionsQuery("select permission_name from roles_permissions where role_name = ?");
      //构建环境
      DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
      defaultSecurityManager.setRealm(jdbcRealm);

      SecurityUtils.setSecurityManager(defaultSecurityManager);
      Subject subject = SecurityUtils.getSubject();
      UsernamePasswordToken token = new UsernamePasswordToken("Rose","123");

      subject.login(token);
      System.out.println(subject.isAuthenticated());
      subject.checkRole("user");
      subject.checkPermission("add,select");
    }
}
