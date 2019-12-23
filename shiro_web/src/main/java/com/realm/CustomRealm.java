package com.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomRealm extends AuthorizingRealm {
    Map<String,String> userMap = new HashMap<>();
  {userMap.put("Mark","1e14e388e4042dc43defefb9f88695e1");
  }
  /**
   * Retrieves the AuthorizationInfo for the given principals from the underlying data store.  When
   * returning an instance from this method, you might want to consider using an instance of {@link
   * SimpleAuthorizationInfo SimpleAuthorizationInfo}, as it is suitable in most cases.
   *
   * @param principals the primary identifying principals of the AuthorizationInfo that should be
   *                   retrieved.
   * @return the AuthorizationInfo associated with this principals.
   * @see SimpleAuthorizationInfo
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String userName = (String) principals.getPrimaryPrincipal();
    Set<String> roles = getRolesyUserName(userName);
    Set<String> psrmissions = getPermissionByUserName(userName);
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setStringPermissions(psrmissions);
    authorizationInfo.setRoles(roles);
    return authorizationInfo;
  }

  private Set<String> getPermissionByUserName(String userName) {
    Set<String> sets = new HashSet<>();
    sets.add("user:delete");
    sets.add("user:update");
    return sets;
  }

  private Set<String> getRolesyUserName(String userName) {
    Set<String> sets = new HashSet<>();
    sets.add("admin");
    sets.add("user");
    return sets;
  }

  /**
   * Retrieves authentication data from an implementation-specific datasource (RDBMS, LDAP, etc) for
   * the given authentication token.
   * <p/>
   * For most datasources, this means just 'pulling' authentication data for an associated
   * subject/user and nothing more and letting Shiro do the rest.  But in some systems, this method
   * could actually perform EIS specific log-in logic in addition to just retrieving data - it is up
   * to the Realm implementation.
   * <p/>
   * A {@code null} return value means that no account could be associated with the specified
   * token.
   *
   * @param token the authentication token containing the user's principal and credentials.
   * @return an {@link AuthenticationInfo} object containing account data resulting from the
   * authentication ONLY if the lookup is successful (i.e. account exists and is valid, etc.)
   * @throws AuthenticationException if there is an error acquiring data or performing
   *                                 realm-specific authentication logic for the specified
   *                                 <tt>token</tt>
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    String userName = (String) token.getPrincipal();
    //到数据库中获取凭证
    String password = getPasswordByUserName(userName);
    if(password == null){
      return null;
    }
    SimpleAuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(userName,password,"111");
    authorizationInfo.setCredentialsSalt(ByteSource.Util.bytes("111"));
    return authorizationInfo;
  }

  private String getPasswordByUserName(String userName) {
    return userMap.get(userName);
  }

  public static void main(String[] args) {
    Md5Hash md5Hash = new Md5Hash("124","111");
    System.out.println(md5Hash.toString());
  }
}
