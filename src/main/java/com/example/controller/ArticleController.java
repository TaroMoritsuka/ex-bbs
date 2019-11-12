package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@ModelAttribute
	public CommentForm setUpForm() {
		return new CommentForm();
	}
	
	
	/**
	 *　記事一覧を表示する画面へフォワード.
	 * @param model
	 * @return 記事一覧画面を返す
	 */
	@RequestMapping("")
	public String index(Model model) {
	    List<Article> articles = articleRepository.findAllWithComment();
	    model.addAttribute("articles",articles);
	    return "bbs";
	}
	
	/**
	 * 記事を投稿する.
	 * @param articleForm
	 * @param model
	 * @return　記事一覧画面を返す
	 */
	@RequestMapping("/insert")
	public String insert(@Validated ArticleForm articleForm,BindingResult result,RedirectAttributes redirectattributes,Model model){
		if(result.hasErrors()) {
			return index(model);
		}
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());
		System.out.println(article);
		articleRepository.insert(article);
		return index(model);
	}
	
	/**
	 * コメントを投稿する.
	 * @param commentForm
	 * @param model
	 * @return　記事一覧画面を返す
	 */
	@RequestMapping("/insert-comment")
	public String insertComment(@Validated CommentForm commentForm,BindingResult result,RedirectAttributes redirectattributes,Model model) {
		if(result.hasErrors()) {
//			model.addAttribute("articleId",commentForm.getArticleId());
			return index(model);
		}
		Comment comment = new Comment();
		comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		comment.setName(commentForm.getName());
		comment.setContent(commentForm.getContent());
		commentRepository.insert(comment);
		return index(model);
	}
	
	/**
	 * 記事を削除する.
	 * @param id　ID
	 * @param model
	 * @return　記事一覧画面を返す
	 */
	@RequestMapping("/delete-article")
	public String deleteById(Integer id, Model model) {
		articleRepository.deleteArticleWithCommentById(id);
		return index(model);
	}
}
