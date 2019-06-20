package com.ssm.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.model.User;
import com.ssm.service.UserService;
import com.ssm.vo.RespVo;
import com.ssm.vo.UserVo;

@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired(required=true)
    private UserService userService;  

    /**
     *跳转到添加用户界面
     */
    
    /**
     * 查询所有用户
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.GET) 
    @CrossOrigin
    @ResponseBody
    public RespVo getAllUser(){
        List<User> user = userService.findAll();
        return RespVo.success(user);
        
    }
    
    public String toAddUser(){
        return "addUser";
    }

    /**
     * 添加用户并重定向  
     * @param model
     * @param user
     * @return
     */
//    @RequestMapping("addUser") 
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value="/add",method=RequestMethod.POST) 
    public  RespVo addUser( @RequestBody UserVo vo){
    	 if (StringUtils.isEmpty(vo.getUsername())) {
             return RespVo.error("10003", "参数错误");
         }
    	 User user = new User();
//       user.setMobile(vo.getMobile());
//       user.setName(vo.getName());
    	 BeanUtils.copyProperties(vo, user);
    	 //user.setIsDel(0);
    	 userService.saveUser(user);;
    	 return RespVo.success("success");
   }

    /**
     * 修改用户
     * @param model
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value="/update",method=RequestMethod.POST) 
    @CrossOrigin
    @ResponseBody
    public RespVo UpdateUser(@RequestBody UserVo vo){
    	 if (StringUtils.isEmpty(vo.getId())) {
             return RespVo.error("10003", "参数错误");
         }
    	 User user = new User();
    	 user.setUsername(vo.getUsername());
    	 user.setUsername(vo.getUsername());
    	 BeanUtils.copyProperties(vo, user);
    	 //user.setIsDel(0);
    	 userService.saveUser(user);;
    	 return RespVo.success("success"); 
    		
    		//             if(userService.updateUser(user)){  
    		//                    user = userService.findUserById(user.getId());  
    		//                    model.addAttribute("user", user);  
    		//                    return "redirect:/user/userInfo";  
    		//                } 
    		//         return "/error";
    }
    
  
    /**
     * 根据id删除用户
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping(value="/del",method=RequestMethod.POST) 
    @CrossOrigin
    @ResponseBody
    public RespVo deleteUser(Integer id){
    	return RespVo.success("success");
    }

    /**
     * 分页查询用户信息
     * @param pn 默认从第一页开始  请求参数
     * @param model
     * @return
     */
//    @RequestMapping("userInfo")
//    public String getUsers(@RequestParam(value="pn",defaultValue="1")Integer pn,Model model){
//        //从第一条开始 每页查询五条数据
//        PageHelper.startPage(pn, 5);
//        List<User> users = userService.findAll();
//        //将用户信息放入PageInfo对象里
//        PageInfo page = new PageInfo(users,5);
//        model.addAttribute("pageInfo", page);
//        return "allUser";
//    }
}

