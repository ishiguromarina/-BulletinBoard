package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import beans.UserMessage;
import exception.SQLRuntimeException;

public class UserMessageDao {

    public ArrayList<UserMessage> getUserMessages(Connection connection, String startDateTime, String endDateTime, String category) {

        PreparedStatement ps = null;
        ArrayList<UserMessage> ret = new ArrayList<UserMessage>();
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder();

            //SQL文字列
            sql.append("SELECT ");
            sql.append("messages.id as postId, ");
            sql.append("messages.title as title, ");
            sql.append("messages.text as text, ");
            sql.append("messages.category as category, ");
            sql.append("users.id as userId, ");
            sql.append("users.name as userName, ");
            sql.append("messages.created_date as created_date ");
            sql.append("FROM messages ");
            sql.append("INNER JOIN users ");
            sql.append("ON messages.user_id = users.id ");
            sql.append("WHERE messages.created_date BETWEEN ? AND ? ");
            if(!StringUtils.isEmpty(category)) {
            	sql.append("AND category LIKE ? ");
            }
            sql.append("ORDER BY messages.created_date DESC ");

            //SQL実行
            ps = connection.prepareStatement(sql.toString());
            ps.setString(1, startDateTime);
            ps.setString(2, endDateTime);
            if(!StringUtils.isEmpty(category)) {
            	ps.setString(3, "%" + category + "%");
            }

            //SQL実行
            rs = ps.executeQuery();

            	//取得できた分繰り返し
                while (rs.next()) {

                	//Beanのインスタンス生成
                    UserMessage message = new UserMessage();

                    //Beanへ格納
                    message.setId(rs.getInt("postId"));
                    message.setTitle(rs.getString("title"));
                    message.setText(rs.getString("text"));
                    message.setCategory(rs.getString("category"));
                    message.setUserId(rs.getInt("userId"));
                    message.setUserName(rs.getString("userName"));
                    message.setCreatedDate(rs.getTimestamp("created_date"));

                    //Beanをリストへ格納
                    ret.add(message);
                }
            return ret;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
            close(rs);
        }
    }

}