package com.nextdoor.z.DAO;

import com.nextdoor.z.entity.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class UserDAO {

    //用户登录
    public User userLogin(User user_login) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        User user=new User();
        String sql;
        try {
            connection = DBUtils.getConnection();
            sql = "SELECT uid,uname FROM user WHERE uid = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_login.getId());
            preparedStatement.setString(2,user_login.getPassword());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String current_time=df.format(date);
                sql="UPDATE user set lasttimevisitwebsite=? WHERE uid = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, current_time);
                preparedStatement.setInt(2, user_login.getId());
                int RS = preparedStatement.executeUpdate();
                user=new User();
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(resultSet, preparedStatement, connection);
        }
        return user;
    }
    //用户注册
    public int userSignup(User user) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int generateKey=0;
        try{
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO user (uname,password,uEmail)values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql,preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getUEmail());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                generateKey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            generateKey=0;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return generateKey;
    }
    //添加好友 ——请求
    public int add_friend_request(FriendApp fa){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int generateKey=0;
        String sql;
        try{
            connection = DBUtils.getConnection();
            sql= "SELECT * FROM  friend where uid1= ? AND uid2= ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, fa.getUid1());
            preparedStatement.setInt(2, fa.getUid2());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return 0;
            }
            sql = "INSERT INTO friendapplication (uid1,uid2)values (?,?)";
            preparedStatement = connection.prepareStatement(sql,preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, fa.getUid1());
            preparedStatement.setInt(2, fa.getUid2());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                generateKey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            generateKey=0;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return generateKey;
    }
    //添加好友的结果
    public int ans_friend_request(FriendApp fa) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int res=0;
        String sql;
        try{
            connection = DBUtils.getConnection();
            sql = "UPDATE friendapplication SET is_approved=(?) where faid=(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(2, fa.getFaid());
            preparedStatement.setString(1, fa.getIs_approved());
            // preparedStatement.setString(3,user.getuEmail());
            res=preparedStatement.executeUpdate();
            //ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        } catch (SQLException e) {
            //generateKey=0;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return res;
    }
    //申请加入block
    public int apply_block(BlockApp ba) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int generateKey=0;
        try{
            String sql;
            connection = DBUtils.getConnection();
            sql = "SELECT bid FROM user where uid=(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ba.getUid());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                if(resultSet.getInt(1)==ba.getUid()){
                    System.out.println(resultSet.getInt(1));
                    return 0;
                }
            }
            sql = "INSERT INTO blockmemberapplication (uid,bid)values (?,?)";
            preparedStatement = connection.prepareStatement(sql,preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ba.getUid());
            preparedStatement.setInt(2, ba.getBid());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                generateKey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            generateKey=0;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return generateKey;
    }
    //加入block的结果
    public int ans_block_request(BlockAppRes bar) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int res=0;
        String sql;
        try{
            connection = DBUtils.getConnection();
            sql = "INSERT INTO blockmemberapplicationapprove (bmaid, uid, is_approved) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bar.getBmaid());
            preparedStatement.setInt(2, bar.getUid());
            preparedStatement.setString(3,bar.getIs_approved());
            res=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return res;
    }
    //修改密码
    public int updatePassword(User user, String new_password) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int res=0;
        String sql;
        try{
            connection = DBUtils.getConnection();
            sql = "SELECT uid,uname FROM user WHERE uid = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2,user.getPassword());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user.setPassword(new_password);
                sql="UPDATE user set password=? WHERE uid = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setInt(2, user.getId());
                res = preparedStatement.executeUpdate();
            }else{
                res=2;//2current密码错误,0写入失败，1是正常结果
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return res;
    }
    //profilePersonalInformation
    public User profilePersonalInformation(Integer id) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        User user=new User();
        int res=0;
        String sql;
        try{
            connection = DBUtils.getConnection();
            sql = "SELECT uid,uname,uEmail,birthday,uaddress,discription,occupation,birthplace,gender,status,pincline,website,religion FROM user WHERE uid = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user.setUsername(resultSet.getString(2));
                user.setUEmail(resultSet.getString(3));
                user.setBirthday(resultSet.getString(4));
                user.setUaddress(resultSet.getString(5));
                user.setDescription(resultSet.getString(6));
                user.setOccupation(resultSet.getString(7));
                user.setBirthplace(resultSet.getString(8));
                user.setGender(resultSet.getString(9));
                user.setStatus(resultSet.getString(10));
                user.setPincline(resultSet.getString(11));
                user.setWebsite(resultSet.getString(12));
                user.setReligion(resultSet.getString(13));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return user;
    }
    //updatePersonalinfo
    public int updatePersonalinfo(User user) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int res=0;
        String sql;
        try{
            connection = DBUtils.getConnection();
            sql="UPDATE user set uname=?,uEmail=?,birthday=?,uaddress=?,discription=?,occupation=?,birthplace=?,gender=?,status=?,pincline=?,website=?,religion=? WHERE uid = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getUEmail());
            preparedStatement.setString(3, user.getBirthday());
            preparedStatement.setString(4, user.getUaddress());
            preparedStatement.setString(5, user.getDescription());
            preparedStatement.setString(6, user.getOccupation());
            preparedStatement.setString(7, user.getBirthplace());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setString(9, user.getStatus());
            preparedStatement.setString(10, user.getPincline());
            preparedStatement.setString(11, user.getWebsite());
            preparedStatement.setString(12, user.getReligion());
            preparedStatement.setInt(13, user.getId());
            res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return res;
    }
    //FriendRequest List
    public List<FriendApp> InitializeFriendRequest(User u) {
        List<FriendApp> fa=new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String sql;
        ResultSet res=null;
        try{
            connection = DBUtils.getConnection();
            sql = "SELECT uname,faid,uid2 FROM user join friendapplication on uid=uid2 where is_approved='unknown' and uid1=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, u.getId());
            res=preparedStatement.executeQuery();
            while(res.next()){
                FriendApp f=new FriendApp();
                f.setFaid(res.getInt("faid"));
                f.setUsername(res.getString("uname"));
                f.setUid2(res.getInt("uid2"));
                fa.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return fa;
    }
    //BlockRequest List
    public List<BlockApp> InitializeBlockRequest(User u) {
        List<BlockApp> ba=new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String sql;
        ResultSet res=null;
        try{
            connection = DBUtils.getConnection();
            sql = "select bm1.bmaid as ubmaid,u1.uid as userid,u1.uname as uname\n" +
                    "from blockmemberapplication bm1 join user u1 on bm1.uid=u1.uid\n" +
                    "where bm1.bid=(select u2.bid from user u2 where u2.uid=?) and bm1.bmaid not in (select bm2.bmaid from blockmemberapplicationapprove bm2 where bm2.uid=?) and bm1.bmastate='unknown'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, u.getId());
            preparedStatement.setInt(2, u.getId());
            res=preparedStatement.executeQuery();
            while(res.next()){
                BlockApp b=new BlockApp();
                b.setBmaid(res.getInt("ubmaid"));
                b.setUsername(res.getString("uname"));
                b.setUid(res.getInt("userid"));
                ba.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return ba;
    }
    //Notification List
    public List<SysMessage> InitializeNotification(User u) {
        List<SysMessage> sm=new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String sql;
        ResultSet res=null;
        try{
            connection = DBUtils.getConnection();
            sql = "Select * from sys_mes where userid=? ORDER BY smtime DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, u.getId());
            res=preparedStatement.executeQuery();
            while(res.next()){
                SysMessage s=new SysMessage();
                //s.setUserid(res.getInt(3));
                s.setContent(res.getString(2));
                String t=res.getString(4);
                t=t.substring(0,16);
                s.setStime(t);
                sm.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
        return sm;
    }
}

