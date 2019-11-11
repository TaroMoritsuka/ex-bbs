package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
	
	private final static RowMapper<Article> ARTICLE_ROW_MAPPER = (rs,i)->{
		Article article = new Article();
		List<Comment> commentList = new ArrayList<>();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	private final static RowMapper<Article> ARTICLE_ROW_MAPPER2 = (rs,i)->{
		Article article = new Article();
		List<Comment> commentList = new ArrayList<>();
		Comment comment = new Comment();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		comment.setId(rs.getInt("com_id"));
		comment.setName(rs.getString("com_name"));
		comment.setContent(rs.getString("com_content"));
		
		
			
		
		return article;
	};
	
	/**
	 * 記事の全件検索するメソッド.
	 * @return　投稿をIDの降順（一番上が最新）で返す
	 */
	public List<Article> findAll(){
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}
	
	public List<Article> findAllWithComment(){
		String sql = "SELECT a.id AS id,a.name AS name,a.content AS content,c.id AS com_id,c.name AS com_name,c.content AS com_content" 
				+ ",c.article_id AS article_id FROM articles AS a JOIN comments AS c ON a.id = c.article_id ORDER BY a.id DESC;";
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}
	
	public void insert(Article article) {
		System.out.println(article);
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content)";
		template.update(sql, param);
		
	}

}
