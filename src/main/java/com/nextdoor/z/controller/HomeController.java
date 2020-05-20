package com.nextdoor.z.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nextdoor.z.DAO.MessageDAO;
import com.nextdoor.z.DAO.UserDAO;
import com.nextdoor.z.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
@SessionAttributes(value="currentUser")

public class HomeController {

    UserDAO userDAO = new UserDAO();

    @RequestMapping(value = {"/loginpage"}, method = RequestMethod.GET)
    public ModelAndView loginPage(){
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    //登录
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(User user,HttpSession httpSession){
        System.out.print("login");
        User res=userDAO.userLogin(user);
        if(res.getId()!=null){
            httpSession.setAttribute("currentUser",res);
            return "success";
        }else{
            return "failed";
        }
    }

    //注册
    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    @ResponseBody
    public String userRegister(User u){
        System.out.println("UserSignUp");
        String s="";
        User user=new User();
        user.setPassword(u.getPassword());
        user.setUsername(u.getUsername());
        user.setUEmail(u.getUEmail());
        int res=userDAO.userSignup(user);
        if(res!=0){
            s=res+"";
        }else{
            s="failed";
        }
        return s;
    }

    @RequestMapping(value = {"/AllFriendsMessage"}, method = RequestMethod.GET)
    public ModelAndView allFriendsMessage(Model model, HttpSession session){
        System.out.println("AllFriendsMessage");
        User user = (User)session.getAttribute("currentUser");
        System.out.println(user.getId());
        MessageDAO messageDAO = new MessageDAO();
        ArrayList<Message> messages = messageDAO.getFriendMessges(user.getId());
//        System.out.println(messages);
        ModelAndView mav = new ModelAndView("messages");
        //commentsArray
        String s = JSONArray.toJSON(messages).toString();
        System.out.println(s);
        mav.addObject("userId",user.getId());
        mav.addObject("commentsArray",s);
        return mav;
    }

    @RequestMapping(value = {"/AllBlockMembersMessages"}, method = RequestMethod.GET)
    public ModelAndView allBlockMembersMessages(Model model, HttpSession session){
        System.out.println("AllBlockMembersMessages");
        User user = (User)session.getAttribute("currentUser");
        System.out.println(user.getId());
        MessageDAO messageDAO = new MessageDAO();
        ArrayList<Message> messages = messageDAO.getBlockMessges(user.getId());
        System.out.println(messages);
        ModelAndView mav = new ModelAndView("messages");
        //commentsArray
        String s = JSONArray.toJSON(messages).toString();
        System.out.println(s);
        mav.addObject("userId",user.getId());
        mav.addObject("commentsArray",s);
        return mav;
    }

    @RequestMapping(value = {"/AllHoodMembersMessages"}, method = RequestMethod.GET)
    public ModelAndView allNeighborsMessages(Model model, HttpSession session){
        System.out.println("AllNeighborsMessages");
        User user = (User)session.getAttribute("currentUser");
        System.out.println(user.getId());
        MessageDAO messageDAO = new MessageDAO();
        ArrayList<Message> messages = messageDAO.getHoodMembersMessages(user.getId());
        System.out.println(messages);
        ModelAndView mav = new ModelAndView("messages");
        //commentsArray
        String s = JSONArray.toJSON(messages).toString();
        System.out.println(s);
        mav.addObject("userId",user.getId());
        mav.addObject("commentsArray",s);
        return mav;
    }

    @RequestMapping(value = {"/PersonalMessages"}, method = RequestMethod.GET)
    public ModelAndView personalMessages(Model model, HttpSession session){
        System.out.println("PersonalMessages");
        User user = (User)session.getAttribute("currentUser");
        System.out.println(user.getId());
        MessageDAO messageDAO = new MessageDAO();
        ArrayList<Message> messages = messageDAO.getPersonalMessges(user.getId());
        System.out.println(messages);
        ModelAndView mav = new ModelAndView("messages");
        //commentsArray
        String s = JSONArray.toJSON(messages).toString();
        System.out.println(s);
        mav.addObject("userId",user.getId());
        mav.addObject("commentsArray",s);
        return mav;
    }

    @RequestMapping(value = {"/addMessagePage"}, method = RequestMethod.GET)
    public ModelAndView addMessagePage(Model model,HttpSession session){
        System.out.println("PersonalMessages");
        User user = (User)session.getAttribute("currentUser");
        System.out.println(user.getId());
        MessageDAO messageDAO = new MessageDAO();
        ArrayList<Message> messages = messageDAO.getMyMessges(user.getId());
        System.out.println(messages);
        ModelAndView mav = new ModelAndView("messages-post");
        //commentsArray
        String s = JSONArray.toJSON(messages).toString();
        System.out.println(s);
        mav.addObject("userId",user.getId());
        mav.addObject("commentsArray",s);
        return mav;
    }

    @RequestMapping(value = {"/addMessage"}, method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestBody String message,HttpSession httpSession){
        JSONObject jsonObject = JSONObject.parseObject(message);
//        System.out.println(jsonObject.getInteger("parent"));
        if(null == jsonObject.get("parent")){
            System.out.println("addMessage");
            Message m = new Message();
            MessageDAO messageDAO = new MessageDAO();
            String feed = jsonObject.getString("feed");

            System.out.println("ooooooo"+feed+"ooooo");
            m.setFeed(feed);
            if (feed.equals("person")) {
                System.out.println("hhhh");
                m.setTo_uid(jsonObject.getInteger("to_uid"));
                System.out.println(m.getTo_uid());
            }
            m.setCreated(jsonObject.getString("created"));
            User user = (User)httpSession.getAttribute("currentUser");
            m.setCreator(user.getId());
            m.setContent(jsonObject.getString("content"));
            messageDAO.addMessage(m);
        }else{
            System.out.println("addComment");
            System.out.println(message);

            Message m = new Message();
            MessageDAO messageDAO = new MessageDAO();

            m.setCreated(jsonObject.getString("created"));
            User user = (User)httpSession.getAttribute("currentUser");
            m.setCreator(user.getId());
            m.setContent(jsonObject.getString("content"));
            int parent = jsonObject.getInteger("parent");
            m.setParent(parent);
            System.out.println(parent);
            System.out.println("mid");

            int mid = jsonObject.getInteger("mid");
            System.out.println(mid);

            if(parent != mid){
                m.setMid(mid);
            }
            System.out.println(m);
            messageDAO.addComment(m);
        }
        System.out.println(message);
        return "success";
    }

    //加好友的请求
    @RequestMapping(value = {"/add_friend_request"}, method = RequestMethod.GET)
    public String addFriendRequest(Integer uid2,HttpSession session){
        System.out.println("Add Friend Request");
        String s="";
        FriendApp fa=new FriendApp();
        User u = (User)session.getAttribute("currentUser");
        fa.setUid1(u.getId());
        fa.setUid2(uid2);
        int res=userDAO.add_friend_request(fa);
        if(res!=0){
            System.out.println("successed");
        }else{
            System.out.println("failed");
        }
        return s;
    }
    //通过好友消息验证
    @RequestMapping(value = {"/ans_friend_request"}, method = RequestMethod.POST)
    @ResponseBody
    public String ans_friend_request(Integer faid,String is_approved){
        System.out.println("ans friend request");
        String s="";
        FriendApp fa=new FriendApp();
        fa.setFaid(faid);
        fa.setIs_approved(is_approved);
        int res=userDAO.ans_friend_request(fa);
        if(res==1){
            s="success";
            //System.out.println("failed");
        }else{
            s="wrong";
            //System.out.println("successed");
        }
        return s;
    }
    //申请加入block
    @RequestMapping(value = {"/apply_block"}, method = RequestMethod.GET)
    public String apply_block(Integer bid,HttpSession session){
        System.out.println("apply block");
        String s="";
        User u = (User)session.getAttribute("currentUser");
        BlockApp ba=new BlockApp();
        ba.setUid(u.getId());
        ba.setBid(bid);
        ba.setBmastate("unknown");
        int res=userDAO.apply_block(ba);
        if(res==0){
            System.out.println("failed");
        }else{
            System.out.println("successed!");
        }
        return s;
    }
    //同意加入block
    @RequestMapping(value = {"ans_block_request"}, method = RequestMethod.POST)
    @ResponseBody
    public String ans_block_request(String is_approved, Integer bmaid, HttpSession session){
        System.out.println("ans block member application");
        String s="";
        BlockAppRes bar = new BlockAppRes();
        User u = (User)session.getAttribute("currentUser");
        bar.setBmaid(bmaid);
        bar.setIs_approved(is_approved);
        bar.setUid(u.getId());
        int res=userDAO.ans_block_request(bar);
        if(res==0){
            s="wrong";
        }else{
            s="success";
        }
        return s;
    }
    //修改密码
    @RequestMapping(value = {"/updatePassword"}, method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(String new_password, String old_password, HttpSession session){
        System.out.println("updatePassword");
        String s="";
        User u = (User)session.getAttribute("currentUser");
        User user=new User();
        user.setId(u.getId());
        user.setPassword(old_password);
        int res=userDAO.updatePassword(user,new_password);
        if(res==1){
            s= "success";
        }else if(res==2){
            s="passWrong";
        }else{
            s="updarefailed";
        }
        return s;
    }

    //用户信息
    @RequestMapping(value = {"/profile-personalinfo"}, method = RequestMethod.GET)
    public ModelAndView profilePersonalInformation(Model model,HttpSession session){
        System.out.println("get personal information");
        User u = (User)session.getAttribute("currentUser");
        User user=userDAO.profilePersonalInformation(u.getId());
        System.out.println(user.getUEmail());
        ModelAndView mav = new ModelAndView("profile-personalinfo");
        mav.addObject("user",user);
        return mav;
    }
    //修改个人信息
    @RequestMapping(value = "updatePersonalinfo", method =  RequestMethod.POST)
    @ResponseBody
    public String  updatePersonalinfo(HttpSession session,User user){
        System.out.println("update personal information");
        String s="";
        User u = (User)session.getAttribute("currentUser");
        user.setId(u.getId());
        int res=userDAO.updatePersonalinfo(user);

        if(res==1){
            s="successttt";
        }else{
            s="failed";
        }
        return s;
    }


    @RequestMapping(value = {"/blockmemberapplication"}, method = RequestMethod.GET)
    public ModelAndView blockMemberApplication(Model model){
        ModelAndView mav = new ModelAndView("blockmemberapplication");
        return mav;
    }

    //初始化block Request
    @RequestMapping(value ="InitializeBlockRequest")
    @ResponseBody
    public String InitializeBlockRequest(HttpSession session){
        System.out.println("Initialize Block Request");
        User u=(User)session.getAttribute("currentUser");
        String s="";
        try{
            List<BlockApp> fa =userDAO.InitializeBlockRequest(u);
            s = JSONArray.toJSON(fa).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping(value = {"/profile-hobbiesandinterests"}, method = RequestMethod.GET)
    public ModelAndView profileHobbiesAndInterests(Model model){
        ModelAndView mav = new ModelAndView("profile-hobbiesandinterests");
        return mav;
    }

    @RequestMapping(value = {"/profile-changepassword"}, method = RequestMethod.GET)
    public ModelAndView profileChangePassword(Model model){
        ModelAndView mav = new ModelAndView("profile-changepassword");
        return mav;
    }

    @RequestMapping(value = {"/neighboors"}, method = RequestMethod.GET)
    public ModelAndView neighboors(Model model){
        ModelAndView mav = new ModelAndView("neighboors");
        return mav;
    }

    @RequestMapping(value = {"/homepage"}, method = RequestMethod.GET)
    public ModelAndView homepage(Model model){
        ModelAndView mav = new ModelAndView("homepage");
        return mav;
    }

    @RequestMapping(value = {"/notifications"}, method = RequestMethod.GET)
    public ModelAndView notification(){
        ModelAndView mav = new ModelAndView("notifications");
        return mav;
    }

    //初始化notification
    @RequestMapping(value = "InitializeNotification")
    @ResponseBody
    public String InitializeNotification(HttpSession session){
        System.out.println("Initialize Notification");
        User u=(User)session.getAttribute("currentUser");
        String s="";
        try{
            List<SysMessage> sm =userDAO.InitializeNotification(u);
            s = JSONArray.toJSON(sm).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping(value = {"/friendrequests"}, method = RequestMethod.GET)
    public ModelAndView friendRequest(Model model){
        ModelAndView mav = new ModelAndView("friendrequests");
        return mav;
    }

    //初始化FriendRequest
    @RequestMapping(value = "InitializeFriendRequest")
    @ResponseBody
    public String InitializeFriendRequest(HttpSession session){
        System.out.println("Initialize Friend Request List");
        User u=(User)session.getAttribute("currentUser");
        String s="";
        try{
            List<FriendApp> fa =userDAO.InitializeFriendRequest(u);
            s = JSONArray.toJSON(fa).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

}
