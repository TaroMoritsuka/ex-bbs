package com.example.domain;

import java.util.List;

/**
 * 記事のドメインクラス.
 * @author taro
 *
 */
public class Article {
	
	/**投稿ID*/
	private Integer id;
	/**投稿者名*/
	private String name;
	/**コメント*/
	private String content;
	/**記事に対するコメント*/
	private List<Comment> commentList;
	
	
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Article(int id, String name, String content, List<Comment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.commentList = commentList;
	}
	
	public Integer getId() {
		return id;
	}
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", content=" + content + ", commentList=" + commentList + "]";
	}
	
	

}
