package org.twids.SpringBlog.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twids.SpringBlog.models.Authority;
import org.twids.SpringBlog.repositories.AuthorityRepository;

@Service
public class AuthorityService {
    
    @Autowired
    private AuthorityRepository authorityRepository;

    public Authority save(Authority authority){
       return authorityRepository.save(authority);
    }

    public Optional<Authority> findById(Long id){
        return authorityRepository.findById(id);
    }
}
