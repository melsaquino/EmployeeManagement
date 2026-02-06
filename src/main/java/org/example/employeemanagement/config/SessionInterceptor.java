package org.example.employeemanagement.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        HttpSession session = request.getSession(false);
        boolean loggedin = false;
        if (session != null) {
            Object loggedInAttr = session.getAttribute("loggedIn");
            if (loggedInAttr instanceof Boolean && (Boolean) loggedInAttr) {
                Object emailAttr = session.getAttribute("userId");
                if (emailAttr != null && !emailAttr.toString().isEmpty()) {
                    loggedin = true;
                }
            }
        }
        String path = request.getRequestURI();
        if(!loggedin && isProtected(path)){
            response.sendRedirect("/login");
            return false;

        }else if(loggedin && !isProtected(path)){
            response.sendRedirect("/employees");
            return false;
        }

        return true;
    }
    private boolean isProtected(String path) {
        return !path.equals("/login") && !path.equals("/registration");
    }

}
