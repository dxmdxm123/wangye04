package com.du.fitler;

import com.du.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//过滤器  过滤/* 过滤所有的响应信息
@WebFilter(filterName = "LoginSessionFilter",urlPatterns = "/*")
public class LoginSessionFilter implements Filter {
    public void destroy() {
        System.out.println("过滤器死亡了");
    }
    /*
    * @parm req 请求
    * @parm resp 响应
    * @parm chain 过滤链   相当于保安，他有权  他让进/不让进
    * @parm ServletException
    * @parm IOException
     * */

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //根据session来判断  有则进  没有则退出登录页面
        //服务器有session了  就不是null  没有登录   就是null
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("user session " + user);
        String requestURI = request.getRequestURI();
        //
        System.out.println("requestURI = " + requestURI);

        if (requestURI.equals("/login.html") || requestURI.equals("/reg.html") || requestURI.equals("/CodeServlet")  || requestURI.equals("/LoginServlet")
                || requestURI.equals("/layui-v2.5.6/layui/css/layui.css")|| requestURI.equals("/layui-v2.5.6/layui/layui.js")||requestURI.equals("/favicon.ico")
                || requestURI.equals("/layui-v2.5.6/layui/lay/modules/form.js")
                || requestURI.equals("/layui-v2.5.6/layui/lay/modules/layer.js")
                || requestURI.equals("/layui-v2.5.6/layui/css/modules/layer/default/layer.css")
                || requestURI.equals("/layui-v2.5.6/layui/lay/modules/jquery.js")

        ) {
            chain.doFilter(req, resp);
        } else {
            if (user == null) {
                chain.doFilter(req, resp);//旅行的意思 ，否则不让进  问题：登陆页 验证码  注册页面  是不需要判断session的
            }else {
                //跳转到 登录页面
                response.sendRedirect("/login.html");
            }
        }
    }


        //if (user == null) {
        //  chain.doFilter(req,resp);//旅行的意思 ，否则不让进
                                 // 问题：登陆页 验证码  注册页面  是不需要判断session的
                                    //.js .css .png 等地的，都不需要  过滤
       // }else {
        //    response.sendRedirect("/login.html");
        //}



    public void init(FilterConfig config) throws ServletException {

    }

}
