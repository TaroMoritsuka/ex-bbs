package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articlesテーブルを操作するリポジトリ.
 * @author taro
 *
 */
@Repository
public class ArticleRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static ResultSetExtractor<List<Article>> RESULT_SET_EXTRACTER = (rs)->{
		List<Article> articleList = new ArrayList<>();
		Integer preId = 0;
//		Article currentArticle = null;
		List<Comment> commentList = null;
		while(rs.next()) {
			if(preId != rs.getInt("id")) {
				commentList = new ArrayList<>();
				Article article = new Article(rs.getInt("id"),rs.getString("name"),rs.getString("content"),commentList);
//				currentArticle = article;
				preId = article.getId();
				articleList.add(article);
			} 
			if(rs.getInt("com_id") != 0) {
				Comment comment = new Comment(rs.getInt("com_id"),rs.getString("com_name"),rs.getString("com_content"),rs.getInt("id"));
//				currentArticle.getCommentList().add(comment);	
				commentList.add(comment);
			}
			
//			preId = rs.getInt("id");
		}
		return articleList;
	};
	
	/**
	 * 記事およびコメントを全件検索するメソッド.
	 * @return 記事のリストを返す
	 */
	public List<Article> findAllWithComment(){
		String sql = "SELECT a.id AS id,a.name AS name,a.content AS content,c.id AS com_id,c.name AS com_name,c.content AS com_content" 
				+ ",c.article_id AS article_id FROM articles AS a LEFT OUTER JOIN comments AS c ON a.id = c.article_id ORDER BY a.id DESC;";
		return template.query(sql, RESULT_SET_EXTRACTER);
	}
	
	/**
	 * 記事を投稿するメソッド.
	 * @param article 記事
	 */
	public void insert(Article article) {
		System.out.println(article);
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content)";
		template.update(sql, param);
	}
	
	/**
	 * 記事を削除するメソッド.
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
	public void deleteArticleWithCommentById(Integer id) {
		String sql = "WITH deleted AS (DELETE FROM articles WHERE id = :id RETURNING id) "
				+ "DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
