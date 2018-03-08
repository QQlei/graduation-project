package com.graduation.repository;

import com.graduation.domain.Praise;
import com.graduation.domain.view.CommentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PraiseRepository extends JpaRepository<Praise, Long> {


	Long countByCollectId(Long collectId);
	
	Praise findByUserIdAndCollectId(Long userId, Long collectId);
	
	public String findPraiseUserSql="select u.id as userId,u.userName as userName,u.profilePicture as profilePicture,p.createTime as createTime "
			+ "from Praise p,User u WHERE p.userId=u.id";
	
	@Query(findPraiseUserSql+ " and p.id=?1")
	CommentView findPraiseUser(Long id);
	
}