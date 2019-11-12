package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ.
 * @author taro
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
//	private final static RowMapper<Comment> COMMENT_ROW_MAPPER = (rs,i)->{
//		Comment comment = new Comment();
//		comment.setId(rs.getInt("id"));
//		comment.setName(rs.getString("name"));
//		comment.setContent(rs.getString("content"));
//		comment.setArticleId(rs.getInt("article_id"));
//		return comment;
//	};
	
	/**
	 * コメントを投稿するメソッド.
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql = "INSERT INTO comments (article_id,name,content) VALUES (:articleId,:name,:content)";
		template.update(sql, param);
	}
	
	/**
	 * コメントを削除するメソッド.
	 * @param id ID
	 */
	public void deleteById(Integer articleId) {
		String sql = "DELETE FROM comments WHERE article_id = :articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}
}
