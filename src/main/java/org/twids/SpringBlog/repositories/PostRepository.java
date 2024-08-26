package org.twids.SpringBlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twids.SpringBlog.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
