package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Comment;
import exception.SQLRuntimeException;

public class CommentDao {

	//メッセージ登録
    public void insert(Connection connection, Comment comment) {

    	//SQL文字列
        PreparedStatement ps = null;
        try {
        	//SQL文字列作成
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO comments ( ");
            sql.append("user_id ");
            sql.append(", message_id ");
            sql.append(", text ");
            sql.append(", created_date ");
            sql.append(", updated_date ");
            sql.append(") VALUES (");
            sql.append(" ?"); // user_id
            sql.append(", ?"); // post_id
            sql.append(", ?"); // text
            sql.append(", CURRENT_TIMESTAMP"); // created_date
            sql.append(", CURRENT_TIMESTAMP"); // updated_date
            sql.append(")");

            //SQL文字列格納
            ps = connection.prepareStatement(sql.toString());

            //パラメータ格納
            ps.setInt(1, comment.getUserId());
            ps.setInt(2, comment.getPostId());
            ps.setString(3, comment.getText());

            //SQL実行
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }
}