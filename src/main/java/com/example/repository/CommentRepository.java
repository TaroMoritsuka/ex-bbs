package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ.
 * @author taro
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static RowMapper<Comment> COMMENT_ROW_MAPPER = (rs,i)->{
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	
	/**
	 * 投稿IDに対応するコメントを検索するメソッド.
	 * @param articleId
	 * @return コメントのリストをIDの降順（最新のコメントが一番上）で返す
	 */
	public List<Article> findByArticleId(){
		 List<Article> articles = articleRepository.findAll();
		    for (Article article : articles) {
		    	Integer articleId = article.getId();
		    	String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id = :articleId ORDER BY id DESC";
		    	SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		    	article.setCommentList(template.query(sql, param, COMMENT_ROW_MAPPER));
			}
		    System.out.println(articles);
		    return articles;
	}
	
	public void insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		String sql = "INSERT INTO comments (article_id,name,content) VALUES (:articleId,:name,:content)";
		template.update(sql, param);
	}
}
