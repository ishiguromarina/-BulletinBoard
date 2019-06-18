package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.UserComment;
import exception.SQLRuntimeException;

public class UserCommentDao {

    public ArrayList<UserComment> getUserComments(Connection connection, int num) {

        PreparedStatement ps = null;
        ArrayList<UserComment> ret = new ArrayList<UserComment>();
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder();

            //SQL文字列
            sql.append("SELECT ");
            sql.append("comments.id as id, ");
            sql.append("comments.text as text, ");
            sql.append("comments.message_id as messageId, ");
            sql.append("users.id as userId, ");
            sql.append("users.name as userName, ");
            sql.append("comments.created_date as created_date ");
            sql.append("FROM comments ");
            sql.append("INNER JOIN users ");
            sql.append("ON comments.user_id = users.id ");
            sql.append("ORDER BY created_date DESC limit " + num);

            //SQL実行
            ps = connection.prepareStatement(sql.toString());

            //SQL実行
            rs = ps.executeQuery();

            	//取得できた分繰り返し
                while (rs.next()) {

                	//Beanのインスタンス生成
                    UserComment userComment = new UserComment();

                    //Beanへ格納
                    userComment.setId(rs.getInt("id"));
                    userComment.setText(rs.getString("text"));
                    userComment.setUserId(rs.getInt("userId"));
                    userComment.setMessageId(rs.getInt("messageId"));
                    userComment.setUserName(rs.getString("userName"));
                    userComment.setCreatedDate(rs.getTimestamp("created_date"));

                    //Beanをリストへ格納
                    ret.add(userComment);
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