package com.nextdoor.z.DAO;

import com.nextdoor.z.entity.Message;
import com.nextdoor.z.entity.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDAO {

    public void addMessage(Message message) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        System.out.println("dao");
        try{
            connection = DBUtils.getConnection();
            String sql = "INSERT INTO message (uid,content,feed,posttime,to_uid) values (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getCreator());
            preparedStatement.setString(2,message.getContent());
            preparedStatement.setString(3,message.getFeed());
            preparedStatement.setString(4,message.getCreated());
            preparedStatement.setInt(5,message.getTo_uid());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
    }

    public void addComment(Message message) {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try{
            connection = DBUtils.getConnection();
            if (message.getMid()!= null){
                String sql = "INSERT INTO comment (mid,uid,content,rcid,createtime) values (?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, message.getMid());
                preparedStatement.setInt(4,message.getParent());
                preparedStatement.setInt(2,message.getCreator());
                preparedStatement.setString(3,message.getContent());
                preparedStatement.setString(5,message.getCreated());
                preparedStatement.execute();
            }else{
                String sql = "INSERT INTO comment (mid,uid,content,createtime) values (?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, message.getParent());
                preparedStatement.setInt(2,message.getCreator());
                preparedStatement.setString(3,message.getContent());
                preparedStatement.setString(4,message.getCreated());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        };
    }

    public ArrayList<Message> getFriendMessges(int userId) {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM message WHERE uid in (select uid2 from friend where uid1 = ?)  AND feed = 'friend' ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setCreator(resultSet.getInt(2));
                message.setContent(resultSet.getString(3));
                message.setCreated(resultSet.getString(4).substring(0,19));
                message.setFeed(resultSet.getString(5));
                message.setTo_uid(resultSet.getInt(6));
                message.setLatitude(resultSet.getString(7));
                message.setLongtitude(resultSet.getString(8));
                message.setParent(null);

                if(message.getCreator() == userId){
                    message.setFullname("You");
                }else {
                    String sql1 = "SELECT uname FROM user WHERE uid = ? ";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                    preparedStatement1.setInt(1, message.getCreator());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    if (resultSet1.next()) {
                        message.setFullname(resultSet1.getString(1));
                    }
                }
                messages.add(message);
            }
            //System.out.println(messages);
            ArrayList<Message> messages1 = new ArrayList<>();
            for(Message message:messages){
                //System.out.println("loop");
                int mid = message.getId();
                //System.out.println(mid);
                String sql2 = "SELECT * FROM comment WHERE mid = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, mid);
                ResultSet cResultSet = preparedStatement2.executeQuery();
                while(cResultSet.next()){
                    Message m = new Message();
                    m.setId(cResultSet.getInt(1));
                    m.setParent(cResultSet.getInt(2));
                    m.setCreator(cResultSet.getInt(3));
                    m.setContent(cResultSet.getString(4));
                    int rcid = cResultSet.getInt(5);
                    //m.setCreated(cResultSet.getString(6).substring(0,19));
                    if(rcid!=0){
                        m.setParent(rcid);
                    }
                    if(m.getCreator() == userId){
                        m.setFullname("You");
                    }else {
                        String sql3 = "SELECT uname FROM user WHERE uid = ? ";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, m.getCreator());
                        ResultSet resultSet3 = preparedStatement3.executeQuery();
                        if (resultSet3.next()) {
                            m.setFullname(resultSet3.getString(1));
                        }
                    }
                    messages1.add(m);
                }
            }
            for(Message message:messages1){
                messages.add(message);
            }

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(resultSet, preparedStatement, connection);
        }
        return messages;
    }

    public ArrayList<Message> getBlockMessges(int userId) {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM message WHERE uid in (select uid from user where bid = (select bid from user where uid = ?))  AND feed = 'block' ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setCreator(resultSet.getInt(2));
                message.setContent(resultSet.getString(3));
                message.setCreated(resultSet.getString(4).substring(0,19));
                message.setFeed(resultSet.getString(5));
                message.setTo_uid(resultSet.getInt(6));
                message.setLatitude(resultSet.getString(7));
                message.setLongtitude(resultSet.getString(8));
                message.setParent(null);

                if(message.getCreator() == userId){
                    message.setFullname("You");
                }else {
                    String sql1 = "SELECT uname FROM user WHERE uid = ? ";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                    preparedStatement1.setInt(1, message.getCreator());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    if (resultSet1.next()) {
                        message.setFullname(resultSet1.getString(1));
                    }
                }
                messages.add(message);
            }
            System.out.println(messages);
            ArrayList<Message> messages1 = new ArrayList<>();
            for(Message message:messages){
                System.out.println("loop");
                int mid = message.getId();
                System.out.println(mid);
                String sql2 = "SELECT * FROM comment WHERE mid = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, mid);
                ResultSet cResultSet = preparedStatement2.executeQuery();
                while(cResultSet.next()){
                    Message m = new Message();
                    m.setId(cResultSet.getInt(1));
                    m.setParent(cResultSet.getInt(2));
                    m.setCreator(cResultSet.getInt(3));
                    m.setContent(cResultSet.getString(4));
                    int rcid = cResultSet.getInt(5);
                    if(rcid!=0){
                        m.setParent(rcid);
                    }
                    if(m.getCreator() == userId){
                        m.setFullname("You");
                    }else {
                        String sql3 = "SELECT uname FROM user WHERE uid = ? ";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, m.getCreator());
                        ResultSet resultSet3 = preparedStatement3.executeQuery();
                        if (resultSet3.next()) {
                            m.setFullname(resultSet3.getString(1));
                        }
                    }
                    messages1.add(m);
                }
            }
            for(Message message:messages1){
                messages.add(message);
            }

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(resultSet, preparedStatement, connection);
        }
        return messages;
    }
    public ArrayList<Message> getHoodMembersMessages(int userId) {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM message WHERE uid in (select uid from user where bid in(select bid from hood_block where hid= (select hid from hood_block where bid = (select bid from user where uid = ?))))  AND feed = 'hood' ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setCreator(resultSet.getInt(2));
                message.setContent(resultSet.getString(3));
                message.setCreated(resultSet.getString(4).substring(0,19));
                message.setFeed(resultSet.getString(5));
                message.setTo_uid(resultSet.getInt(6));
                message.setLatitude(resultSet.getString(7));
                message.setLongtitude(resultSet.getString(8));
                message.setParent(null);

                if(message.getCreator() == userId){
                    message.setFullname("You");
                }else {
                    String sql1 = "SELECT uname FROM user WHERE uid = ? ";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                    preparedStatement1.setInt(1, message.getCreator());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    if (resultSet1.next()) {
                        message.setFullname(resultSet1.getString(1));
                    }
                }
                messages.add(message);
            }
            System.out.println(messages);
            ArrayList<Message> messages1 = new ArrayList<>();
            for(Message message:messages){
                System.out.println("loop");
                int mid = message.getId();
                System.out.println(mid);
                String sql2 = "SELECT * FROM comment WHERE mid = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, mid);
                ResultSet cResultSet = preparedStatement2.executeQuery();
                while(cResultSet.next()){
                    Message m = new Message();
                    m.setId(cResultSet.getInt(1));
                    m.setParent(cResultSet.getInt(2));
                    m.setCreator(cResultSet.getInt(3));
                    m.setContent(cResultSet.getString(4));
                    int rcid = cResultSet.getInt(5);
                    if(rcid!=0){
                        m.setParent(rcid);
                    }
                    if(m.getCreator() == userId){
                        m.setFullname("You");
                    }else {
                        String sql3 = "SELECT uname FROM user WHERE uid = ? ";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, m.getCreator());
                        ResultSet resultSet3 = preparedStatement3.executeQuery();
                        if (resultSet3.next()) {
                            m.setFullname(resultSet3.getString(1));
                        }
                    }
                    messages1.add(m);
                }
            }
            for(Message message:messages1){
                messages.add(message);
            }

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(resultSet, preparedStatement, connection);
        }
        return messages;
    }

    public ArrayList<Message> getPersonalMessges(int userId) {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM message WHERE to_uid = ?  AND feed = 'person' ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setCreator(resultSet.getInt(2));
                message.setContent(resultSet.getString(3));
                message.setCreated(resultSet.getString(4).substring(0,19));
                message.setFeed(resultSet.getString(5));
                message.setTo_uid(resultSet.getInt(6));
                message.setLatitude(resultSet.getString(7));
                message.setLongtitude(resultSet.getString(8));
                message.setParent(null);

                if(message.getCreator() == userId){
                    message.setFullname("You");
                }else {
                    String sql1 = "SELECT uname FROM user WHERE uid = ? ";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                    preparedStatement1.setInt(1, message.getCreator());
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    if (resultSet1.next()) {
                        message.setFullname(resultSet1.getString(1));
                    }
                }
                messages.add(message);
            }
            System.out.println(messages);
            ArrayList<Message> messages1 = new ArrayList<>();
            for(Message message:messages){
                System.out.println("loop");
                int mid = message.getId();
                System.out.println(mid);
                String sql2 = "SELECT * FROM comment WHERE mid = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, mid);
                ResultSet cResultSet = preparedStatement2.executeQuery();
                while(cResultSet.next()){
                    Message m = new Message();
                    m.setId(cResultSet.getInt(1));
                    m.setParent(cResultSet.getInt(2));
                    m.setCreator(cResultSet.getInt(3));
                    m.setContent(cResultSet.getString(4));
                    int rcid = cResultSet.getInt(5);
                    if(rcid!=0){
                        m.setParent(rcid);
                    }
                    if(m.getCreator() == userId){
                        m.setFullname("You");
                    }else {
                        String sql3 = "SELECT uname FROM user WHERE uid = ? ";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, m.getCreator());
                        ResultSet resultSet3 = preparedStatement3.executeQuery();
                        if (resultSet3.next()) {
                            m.setFullname(resultSet3.getString(1));
                        }
                    }
                    messages1.add(m);
                }
            }
            for(Message message:messages1){
                messages.add(message);
            }

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(resultSet, preparedStatement, connection);
        }
        return messages;
    }

    public ArrayList<Message> getMyMessges(int userId) {
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            String sql = "SELECT * FROM message WHERE uid = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getInt(1));
                message.setCreator(resultSet.getInt(2));
                message.setContent(resultSet.getString(3));
                message.setCreated(resultSet.getString(4).substring(0,19));
                message.setFeed(resultSet.getString(5));
                message.setTo_uid(resultSet.getInt(6));
                message.setLatitude(resultSet.getString(7));
                message.setLongtitude(resultSet.getString(8));
                message.setParent(null);
                message.setFullname("You");
                messages.add(message);
            }
            ArrayList<Message> messages1 = new ArrayList<>();
            for(Message message:messages){
                System.out.println("loop");
                int mid = message.getId();
                System.out.println(mid);
                String sql2 = "SELECT * FROM comment WHERE mid = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, mid);
                ResultSet cResultSet = preparedStatement2.executeQuery();
                while(cResultSet.next()){
                    Message m = new Message();
                    m.setId(cResultSet.getInt(1));
                    m.setParent(cResultSet.getInt(2));
                    m.setCreator(cResultSet.getInt(3));
                    m.setContent(cResultSet.getString(4));
                    int rcid = cResultSet.getInt(5);
                    if(rcid!=0){
                        m.setParent(rcid);
                    }
                    if(m.getCreator() == userId){
                        m.setFullname("You");
                    }else {
                        String sql3 = "SELECT uname FROM user WHERE uid = ? ";
                        PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                        preparedStatement3.setInt(1, m.getCreator());
                        ResultSet resultSet3 = preparedStatement3.executeQuery();
                        if (resultSet3.next()) {
                            m.setFullname(resultSet3.getString(1));
                        }
                    }
                    messages1.add(m);
                }
            }
            for(Message message:messages1){
                messages.add(message);
            }

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(resultSet, preparedStatement, connection);
        }
        return messages;
    }
}
