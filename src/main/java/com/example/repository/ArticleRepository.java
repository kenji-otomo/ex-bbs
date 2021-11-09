package com.example.repository;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
//import com.example.domain.Comment;

@Repository
public class ArticleRepository {
	
	private static final RowMapper<Article>ARTICLE_ROW_MAPPER =
			new BeanPropertyRowMapper<>(Article.class);
//			(rs,i) -> {
//				Article article = new Article();
//				article.setId(rs.getInt("id"));
//				article.setName(rs.getString("name"));
//				article.setContent(rs.getString("content"));
//				
//				List<Comment>list = new ArrayList<Comment>();
//				Comment comment = new Comment();
//				comment.setId(rs.getInt("com_id"));
//				comment.setName(rs.getString("com_name"));
//				comment.setContent(rs.getString("com_content"));
//				comment.setArticleId(rs.getInt("com_article_id"));
//				list.add(comment);
//				
//				article.setCommentList(list);
//				
//				return article;
//			};
	

	@Autowired
	NamedParameterJdbcTemplate template;
	
	public List<Article> findAll() {
		String sql ="SELECT id,name,content FROM articles ORDER BY id DESC;";
				
//				" SELECT a.id AS id, a.name AS name, a.content AS content, "
//				+ " c.id AS com_id, c.name AS com_name, c.content AS com_content, c.article_id AS com_article_id"
//				+ " FROM articles AS a "
//				+ " INNER JOIN comments AS c "
//				+ " ON a.id = c.article_id "
//				+ " ORDER BY a.id DESC ;";
		
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
