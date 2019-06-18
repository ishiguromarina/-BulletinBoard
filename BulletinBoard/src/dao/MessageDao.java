package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Message;
import exception.SQLRuntimeException;

public class MessageDao {

	//メッセージ登録
    public void insert(Connection connection, Message message) {

    	//SQL文字列
        PreparedStatement ps = null;
        try {
        	//SQL文字列作成
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO messages ( ");
            sql.append("user_id");
            sql.append(", text");
            sql.append(", title");
            sql.append(", category");
            sql.append(", created_date");
            sql.append(", updated_date");
            sql.append(") VALUES (");
            sql.append(" ?"); // user_id
            sql.append(", ?"); // text
            sql.append(", ?"); // title
            sql.append(", ?"); // category
            sql.append(", CURRENT_TIMESTAMP"); // created_date
            sql.append(", CURRENT_TIMESTAMP"); // updated_date
            sql.append(")");

            //SQL文字列格納
            ps = connection.prepareStatement(sql.toString());

            //パラメータ格納
            ps.setInt(1, message.getUserId());
            ps.setString(2, message.getText());
            ps.setString(3, message.getTitle());
            ps.setString(4, message.getCategory());

            //SQL実行
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }
}