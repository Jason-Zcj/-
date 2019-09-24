package com.itheima.security;

import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果需要从数据库中查询用户信息，必须提供一个使用UserDetailsService接口的实现类
 *
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class SpringSecurity implements UserDetailsService {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
    }

    //模拟数据库的数据
    private static List<SysUser> userList = new ArrayList<>();
    static {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        SysUser user1 = new SysUser();
        user1.setUsername("zhangsan");
        user1.setPassword(passwordEncoder.encode("123"));

        //创建角色
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");

        Role role3 = new Role();
        role3.setName("add");

        user1.getRoles().add(role1);
        user1.getRoles().add(role3);


        SysUser user2 = new SysUser();
        user2.setUsername("lisi");
        user2.setPassword(passwordEncoder.encode("123"));

        Role role2 = new Role();
        role2.setName("ROLE_USER");

        Role role4 = new Role();
        role4.setName("delete");

        user2.getRoles().add(role2);
        user2.getRoles().add(role4);

        userList.add(user1);
        userList.add(user2);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 根据用户名从数据库查询用户信息
        // userDao.findByUsername();
        for (SysUser sysUser : userList) {
            if(username != null && username.equals(sysUser.getUsername())){
                //创建角色的集合对象
                List<GrantedAuthority> list = new ArrayList<>();
                for (Role role : sysUser.getRoles()) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
                    list.add(simpleGrantedAuthority);
                }

                //2. 返回User（安全框架定义）对象即可
                User user = new User(sysUser.getUsername(), sysUser.getPassword(), list);
                return  user;
            }
        }


        return null;
    }
}
