package mybatis.mvc.dynamic.controllers;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import mybatis.mvc.dynamic.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import mybatis.mvc.dynamic.service.UserService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created with by shuangyao on 2016/8/17.
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public void getById(@RequestParam(value = "id", required = true) int id,
                        OutputStream outputStream,
                        HttpServletRequest request,
                        HttpServletResponse response){
        try {
            User user = userService.getById(id);
            String result = request.getRequestURI() + " -> ";
            if (user == null){
                result += "empty";
            }else {
                result += user.toString();
            }
            outputStream.write(result.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    @RequestMapping("cat")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        Transaction t = Cat.getProducer().newTransaction("your transaction type", "your transaction name");
        try {
            System.out.println("debug...");
            Cat.getProducer().logEvent("your event type", "your event name", Event.SUCCESS, "keyValuePairs");
            t.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            Cat.getProducer().logError(e);//用log4j记录系统异常，以便在Logview中看到此信息
            t.setStatus(e);
            // throw e;
    	            	/*  (CAT所有的API都可以单独使用，也可以组合使用，比如Transaction中嵌套Event或者Metric。)
    	                  (注意如果这里希望异常继续向上抛，需要继续向上抛出，往往需要抛出异常，让上层应用知道。)
    	                  (如果认为这个异常在这边可以被吃掉，则不需要在抛出异常。)*/
        } finally {
            t.complete();
        }

        return new ModelAndView("index");
    }

}
