package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * 記事のフォーム
 * @author taro
 *
 */
public class ArticleForm {
	/**名前*/
	@NotBlank(message="名前は必須です")
	private String name;
	/**記事内容*/
	@NotBlank(message="投稿内容は必須です")
	private String content;
	
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
		return "ArticleForm [name=" + name + ", content=" + content + "]";
	}
	
}
