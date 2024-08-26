package org.twids.SpringBlog.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.twids.SpringBlog.models.Post;
import org.twids.SpringBlog.repositories.PostRepository;

//.NET de "public class PostService implements PostRepository" yazılırdı, fazladan kod yazılırdı
//(repo içi de dolu), Spring İLE Spring Data JPA, PostRepository'yi otomatik olarak bir bean 
//olarak yapılandırır ve uygulamada herhangi bir yerde kullanmamıza olanak tanır
//(@Autowired ile enjekte edilir) Spring IoC kapsayıcısı, PostRepository'yi oluşturur ve 
//PostService'e gerekli bağımlılığı(1.1) sağlar. 

@Service
public class PostService {
   
    @Autowired
    private PostRepository postRepository;
    
    //dönen değer, Optional<Post> tipindedir, yani post bulunursa içinde postu tutar, bulunamazsa boş olur.
    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    //offset = sayfa numarası
    public Page<Post> findAll(int offset, int pageSize, String field){
        return postRepository.findAll(PageRequest.of(offset, pageSize).withSort(Direction.ASC, field));
    }

    public void delete(Post post){
        postRepository.delete(post);
    }
    
    public Post save(Post post){
        if (post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }
}
