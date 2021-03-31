package com.example.demo.dgut.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.demo.dgut.config.SendSmsConfig;
import com.example.demo.dgut.dao.UserDao;
import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.User;
import com.example.demo.dgut.service.UserService;
import com.example.demo.dgut.util.CodeUtils;
import com.example.demo.dgut.util.EmailUtils;
import com.example.demo.dgut.util.JsonDataResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "用户登录接口")
@Slf4j
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

    @Autowired
    public UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private CodeUtils code;

    // 存储Redis中的key
    public String key;

    // 判断Rdis中是否存在当前key
    private boolean hasKey;

    // 判断当前操作结果
    private boolean operationResult;

    // 存储手机验证码
    private String verifyCode = "a123";

    // 文件日期
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @Autowired
    private EmailUtils send;

    // 用户登录
    @ApiOperation(value = "用户登录",notes = "根据用户名和密码登录账号， 返回值为user对象")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name = "userName",value = "用户名",required = true),
            @ApiImplicitParam(paramType = "path",name = "userPassword",value = "用户密码",required = true)
    })
    @PostMapping("/login")
    public User login(String userName, String userPassword){
        //生成Redis中的key
        key = code.getRandonString(4);
        try {
            User userDB = userService.login(userName, userPassword);
            // userId作为对应的value
            stringRedisTemplate.opsForValue().set(key, userDB.getUserid().toString(), 10, TimeUnit.MINUTES);
            return userDB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 用户手机号登录
    @PostMapping("/loginByPhone")
    public JsonDataResult loginByPhone(@RequestBody User user){
        log.info("用户信息：[{}]",user.toString());
        // 生成Redis中的key
        key = code.getRandonString(4);
        try {
            User userDB = userService.loginByPhone(user.getPhonenum(), user.getUserpassword());
            // userId作为对应的value
            stringRedisTemplate.opsForValue().set(key, userDB.getUserid().toString(), 10, TimeUnit.MINUTES);//设置Redis中该Key的过期时间
            userDB.setUserpassword("");
            return JsonDataResult.buildSuccess(userDB);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonDataResult.buildError("登录失败");
        }
    }

    // 用户注册
    @ApiOperation(value = "用户注册",notes = "注册成功，默认进行登录")
    @PostMapping("/register")
    public JsonDataResult register(@RequestBody User user, String gotCode){
        log.info("用户信息：[{}]",user.toString());
        if(userService.isExistUser(user.getPhonenum())) {
            return JsonDataResult.buildError("该手机号已存在");
        }else {
            try {
                System.out.println(gotCode);
                System.out.println(verifyCode);
                if(gotCode.length()!=0){
                    if(gotCode.equals(verifyCode)){
                        operationResult = userService.register(user);
                        if(operationResult){
                            User userDB = login(user.getUsername(),user.getUserpassword());
                            userDB.setUserpassword("");
                            return JsonDataResult.buildSuccess(userDB);
                        }else {
                            return JsonDataResult.buildError("注册失败");
                        }
                    }else {
                        return JsonDataResult.buildError("输入的验证码有误");
                    }
                }else {
                    return JsonDataResult.buildError("请输入验证码");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return JsonDataResult.buildError("注册失败");
            }
        }
    }

    // 获取当前用户的数据
    @ApiOperation(value = "获取当前用户的数据",notes = "无需传入参数，后端会记录登录状态")
    @GetMapping("/getUserInfo")
    public JsonDataResult getUserInfo(){
        //查询缓存中是否存在
        hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            int userId = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            User userDB = userService.checkUserInfo(userId);
            userDB.setUserpassword(null);
            return JsonDataResult.buildSuccess(userDB);
        }else {
            return JsonDataResult.buildError("用户不存在或登录过期");
        }
    }

    // 根据用户ID获取用户的数据
    @ApiOperation(value = "根据用户ID获取用户的数据",notes = "")
    @GetMapping("/getUserInfoByUserId")
    public JsonDataResult getUserInfoByUserId(int userid){
            User userDB = userDao.getUserInfoDao(userid);
            userDB.setUserpassword(null);
            return JsonDataResult.buildSuccess(userDB);
    }

    // 获取用户列表
    @ApiOperation(value = "获取用户列表",notes = "无需传入参数，后端会记录登录状态")
    @GetMapping("/getUserList")
    public JsonDataResult getUserList(){
        //查询缓存中是否存在
        hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            List<User> userList = userService.getUserList();
            return JsonDataResult.buildSuccess(userList);
        }else {
            return JsonDataResult.buildError("用户不存在或登录过期");
        }
    }

    // 分页获取用户列表
    @ApiOperation(value = "分页获取用户列表",notes = "分页查询数据")
    @GetMapping("/getUserListByPage")
    public JsonDataResult getUserListByPage(int pagenum, int pagesize, String query){
        log.info("发起请求的pagenum：[{}]",pagenum);
        log.info("发起请求的pagesize：[{}]",pagesize);
        log.info("发起请求的pagesize：[{}]",query);
        //查询缓存中是否存在
        hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            if(query.length()==0){
                PageHelper.startPage(pagenum,pagesize);
                List<User> userList = userService.getUserList();
                PageInfo<User> pageInfo = new PageInfo<User> (userList);
                return JsonDataResult.buildSuccess(pageInfo);
            }else {
                PageHelper.startPage(pagenum,pagesize);
                List<User> userList = userDao.getUserListByUserName(query);
                PageInfo<User> pageInfo = new PageInfo<User> (userList);
                return JsonDataResult.buildSuccess(pageInfo);
            }

        }else {
            return JsonDataResult.buildError("用户不存在或登录过期");
        }
    }

    // 注销登录
    @GetMapping("/logout")
    public JsonDataResult logout(){
        //查询缓存中是否存在
        hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            operationResult = stringRedisTemplate.expire(key, 1, TimeUnit.MICROSECONDS);//设置该Key立即过期
            return JsonDataResult.buildSuccess(operationResult);
        }else {
            return JsonDataResult.buildSuccess(true);
        }
    }

    // 登录状态，修改用户密码
    @ApiOperation(value = "登录状态，修改用户密码",notes = "登录状态时，可通过手机验证码方式重置登录密码")
    @PostMapping("/changePassword")
    public JsonDataResult changePassword(String newPassword, String gotCode){
        //查询缓存中是否存在
        hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey){
            int userId = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
            if(gotCode.equals(verifyCode)){
                operationResult = userService.changePassword(userId, newPassword);
                return JsonDataResult.buildSuccess(operationResult);
            }else {
                return JsonDataResult.buildError("输入的验证码有误");
            }
        }else {
            return JsonDataResult.buildError("用户不存在或登录过期");
        }
    }

    // 重置用户密码
    @ApiOperation(value = "重置用户密码",notes = "用户忘记密码时，可通过手机验证码方式重置登录密码")
    @PostMapping("/resetPassword")
    public JsonDataResult resetPassword(@RequestBody User user, String gotCode){
        log.info("用户信息：[{}]",user.toString());
        if(!userService.isExistUser(user.getPhonenum())){
            return JsonDataResult.buildError("该手机号不存在，请先注册！");
        }else {
            if(gotCode.equals(verifyCode)){
                operationResult = userService.resetPassword(user.getPhonenum(),user.getUserpassword());
                return JsonDataResult.buildSuccess(operationResult);
            }else {
                return JsonDataResult.buildError("输入的验证码有误");
            }
        }
    }

    // 发送手机验证码
    @ApiOperation(value = "发送手机验证码", notes = "根据填入的手机号发送短信验证码")
    @ApiImplicitParam(paramType = "query", name = "phoneNum", value = "手机号码", required = true)
    @GetMapping("/sendVerifyCode")
    public JsonDataResult sendVerifyCode(String phoneNum){
        // 生成验证码
        verifyCode = code.getRandonString(4);
        System.out.println(verifyCode);
        // 发送验证码
        try {
            SendSmsConfig sendSmsConfig = new SendSmsConfig();
            sendSmsConfig.sendSms(phoneNum,verifyCode);
            return JsonDataResult.buildSuccess(verifyCode);
        } catch (ClientException e) {
            e.printStackTrace();
            return JsonDataResult.buildError("发送失败");
        }
    }

    // 发送邮件
    @ApiOperation(value = "发送邮件", notes = "根据填入的邮箱发送电子邮件")
    @ApiImplicitParam(paramType = "query", name = "emailAddress", value = "邮箱地址", required = true)
    @GetMapping("/sendEmail")
    public JsonDataResult sendEmail(String email){
        try {
            send.sendMsg(email);
            return JsonDataResult.buildSuccess(true);
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonDataResult.buildError("发送失败");
        }
    }

    // 用户头像上传
    @ApiOperation(value = "用户头像上传", notes = "用户可以将小于或等于1M的图片文件上传，作为用户头像。先在运行Jar文件的同级目录下，新建一个文件名为public的文件夹，不然会默认存放在临时目录")
    @ApiImplicitParam(paramType = "query", name = "photoFile", value = "所上传的图片", required = true)
    @PostMapping("/uploadProfilePhoto")
    public JsonDataResult uploadProfilePhoto(@RequestParam(value = "file") MultipartFile uploadFile, HttpServletRequest req){
        //查询缓存中是否存在（token）
        hasKey = stringRedisTemplate.hasKey(key);
        if(hasKey) {
            String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
//        String realPath = "E:/csTrending/uploadFile/";
            System.out.println(realPath);
            String format = sdf.format(new Date());
            File folder = new File(realPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(folder, newName));
                String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/" + format + newName;
                int userId = Integer.parseInt(stringRedisTemplate.opsForValue().get(key));
                userService.upLoadUserImage(userId, filePath);
                return JsonDataResult.buildSuccess(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JsonDataResult.buildError("上传失败！");
        }else {
            return JsonDataResult.buildError("用户不存在或登录过期");
        }
    }
}
