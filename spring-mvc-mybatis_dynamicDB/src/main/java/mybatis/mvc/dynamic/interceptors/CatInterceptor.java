package mybatis.mvc.dynamic.interceptors;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.reflect.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Proxy;

/**
 * Created with by shuangyao on 2016/10/19.
 */
public class CatInterceptor implements HandlerInterceptor {

    private ThreadLocal<Transaction> tranLocal = new ThreadLocal<Transaction>();

    private ThreadLocal<Transaction> pageLocal = new ThreadLocal<Transaction>();

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        Transaction pt = pageLocal.get();
        pt.setStatus(Transaction.SUCCESS);
        pt.complete();

        Transaction t = tranLocal.get();
        t.setStatus(Transaction.SUCCESS);
        t.complete();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String uri = request.getRequestURI();
        Transaction t = Cat.newTransaction("URL", uri);
        Cat.logEvent("URL.Method",
                request.getMethod(), Message.SUCCESS, request.getRequestURL().toString());
        Cat.logEvent("URL.Host",
                request.getMethod(), Message.SUCCESS, request.getRemoteHost());
        tranLocal.set(t);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        String viewName = modelAndView != null ? modelAndView.getViewName() : "null";
        Transaction t = Cat.newTransaction("View", viewName);
        pageLocal.set(t);
    }
}
