package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
public class CommentRepository {

	private static final RowMapper<Comment>COMMENT_ROW_MAPPER = 
			new BeanPropertyRowMapper<>(Comment.class);
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	public List<Comment> findByArticleId(int id) {
		String sql ="SELECT id,name,content FROM comments WHERE article_id = :id;";
				
//				" SELECT a.id AS id, a.name AS name, a.content AS content, "
//				+ " c.id AS com_id, c.name AS com_name, c.content AS com_content, c.article_id AS com_article_id"
//				+ " FROM articles AS a "
//				+ " INNER JOIN comments AS c "
//				+ " ON a.id = c.article_id "
//				+ " ORDER BY a.id DESC ;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		List<Comment>list = template.query(sql,param, COMMENT_ROW_MAPPER);
		
		return list;
	}
	
	public void insert(Comment comment) {
		String sql = "INSERT INTO comments (name,content,article_id) "
				+ " VALUES (:name,:content,:articleId);";
		
		SqlParameterSource param = 
				new MapSqlParameterSource()
				.addValue("name", comment.getName())
				.addValue("content", comment.getContent())
				.addValue("articleId",comment.getArticleId());
		
		template.update(sql, param);
		
	}
	
	public void deleteByArticleId(int id) {
		String sql = "DELETE FROM comments WHERE article_id = :id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		template.update(sql, param);
	}
	
}
