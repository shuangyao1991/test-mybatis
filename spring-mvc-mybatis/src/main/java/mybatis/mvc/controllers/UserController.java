package mybatis.mvc.controllers;

import mybatis.mvc.service.UserService;
import mybatis.mvc.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

}
