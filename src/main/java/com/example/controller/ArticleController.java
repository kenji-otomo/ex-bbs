package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
	ArticleRepository repositoryA;
	@Autowired
	CommentRepository repositoryC;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	
	
	@RequestMapping("")
	public String index(Model model) {
		
		List<Article>articles = repositoryA.findAll();
		
		
		
		
//		System.out.println(repositoryA.findAll());
//		System.out.println(articles.size());
		
		
		for (Article article : articles) {
			article.setCommentList(repositoryC.findByArticleId(article.getId()));
		}
		
		
		model.addAttribute("artList",articles);
		
		//System.out.println(articles.get(0).getCommentList());
		
		return "board";
	}
	
	@RequestMapping("/insertA")
	public String insertArticle(ArticleForm form,Model model) {
		
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		
		repositoryA.insert(article);
		
		return "redirect:";
	}
	
	@RequestMapping("/delete")
	public String deleteArticle(Integer id,Model model) {
		
		repositoryA.deleteById(id);
		repositoryC.deleteByArticleId(id);		
		return index(model);
	}
	
	@RequestMapping("/insertC")
	public String insertComment(CommentForm form,Model model) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		
		repositoryC.insert(comment);
		
		return "redirect:";
	}
}
