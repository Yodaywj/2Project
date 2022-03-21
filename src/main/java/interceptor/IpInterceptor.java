package interceptor;


import domain.Tb_User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;



public class IpInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
        Tb_User user = (Tb_User) req.getSession().getAttribute("user");
        if (user == null) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('你未登陆，请先登陆');");
            out.println("window.location.href='/user/toLogin'");
            out.println("</script>");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Enumeration<String> headerNames = req.getHeaderNames();
        Map<String,Object> map = new HashMap<>(20);
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            map.put(headName, req.getHeader(headName));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}

