package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleRepository repository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@RequestMapping("")
	public String index(Model model) {
		
		model.addAttribute("artList",repository.findAll());
		
		return "board";
	}
	
	@RequestMapping("/insert")
	public String insertArticle(ArticleForm form,Model model) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		
		repository.insert(article);
		
		return index(model);
	}
	
	@RequestMapping("/delete")
	public String deleteArticle(Integer id,Model model) {
		
		repository.deleteById(id);
		
		return index(model);
	}
}
