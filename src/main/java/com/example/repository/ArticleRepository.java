package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {
	
	private static final RowMapper<Article>ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);

	@Autowired
	NamedParameterJdbcTemplate template;
	
	public List<Article> findAll() {
		String sql = "SELECT id,name,content "
				+ " FROM articles "
				+ " ORDER BY id DESC ;";
		
		List<Article>list = template.query(sql, ARTICLE_ROW_MAPPER);
		
		return list;
	}
	
	public void insert(Article article) {
		String sql = "INSERT INTO articles (name,content) "
				+ " VALUES (:name,:content);";
		
		SqlParameterSource param = 
				new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
		
		template.update(sql, param);
		
	}
	
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id = :id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		template.update(sql, param);
	}
}
