package com.qiu221b.framework;

import com.qiu221b.framework.bean.Data;
import com.qiu221b.framework.bean.Handler;
import com.qiu221b.framework.bean.Param;
import com.qiu221b.framework.bean.View;
import com.qiu221b.framework.helper.BeanHelper;
import com.qiu221b.framework.helper.ConfigHelper;
import com.qiu221b.framework.helper.ControllerHelper;
import com.qiu221b.framework.util.CodecUtil;
import com.qiu221b.framework.util.JsonUtil;
import com.qiu221b.framework.util.ReflectionUtil;
import com.qiu221b.framework.util.StreamUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    public void init(ServletConfig servletConfig) {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
    }

    public void service(HttpServletRequest request, HttpServletResponse response){
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler != null){
            Class<?> controllerClass = handler.getControlClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            try {
                String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
                String[] params = StringUtils.split(body, "&");
                for (String param : params){
                    String[] array = StringUtils.split(param, "=");
                    String paramName = array[0];
                    String paramValue = array[1];
                    paramMap.put(paramName, paramValue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            if(result instanceof View){
                View view = (View) result;
                String path = view.getPath();
                if(path.startsWith("/")){
                    try {
                        response.sendRedirect(request.getContextPath() + path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Map<String, Object> model = view.getModel();
                    for(Map.Entry<String, Object> entry : model.entrySet()){
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                    try {
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else if (result instanceof Data){
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null){
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    try {
                        PrintWriter writer = response.getWriter();
                        String json = JsonUtil.toJson(model);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
