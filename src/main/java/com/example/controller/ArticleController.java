package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@RequestMapping("")
	public String index(Model model) {
	    List<Article> articles = commentRepository.findByArticleId();
	    model.addAttribute("articles",articles);
	    return "bbs";
	}
	
	@RequestMapping("/insert")
	public String insert(ArticleForm articleForm, Model model) {
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());
		System.out.println(article);
		articleRepository.insert(article);
		return index(model);
	}
	
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm commentForm, Model model) {
		System.out.println(1);
		Comment comment = new Comment();
		System.out.println(2);
		comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		System.out.println(3);
		comment.setName(commentForm.getName());
		System.out.println(4);
		comment.setContent(commentForm.getContent());
		System.out.println(5);
		System.out.println(comment);
		System.out.println(6);
		commentRepository.insert(comment);
		return index(model);
	}

}
