package org.twids.SpringBlog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.twids.SpringBlog.models.Account;
import org.twids.SpringBlog.models.Authority;
import org.twids.SpringBlog.repositories.AccountRepository;
import org.twids.SpringBlog.util.constants.Roles;

@Service
public class AccountService implements UserDetailsService {
    
    @Value("${spring.mvc.static-path-pattern}")
    private String photo_prefix;

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    //hesap kaydetme, güncelleme
    public Account save(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        //default role mekanizması, rolu olmayan herkes user.
        if(account.getRole()== null){
           account.setRole(Roles.USER.getRole());
        }
        //default fotoğraf
        if (account.getPhoto() == null){
            String path = photo_prefix.replace("**", "images/profilePicture.jpg");
            account.setPhoto(path);
        }      
        return accountRepository.save(account);
    }

    //kimlik doğrulama,rol,authority
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<Account> optionalAcccount = accountRepository.findOneByEmailIgnoreCase(email);
       if(!optionalAcccount.isPresent()){
            throw new UnsupportedOperationException("Account is not found, try forever or register.");
       }
       Account account = optionalAcccount.get();

       List<GrantedAuthority> grantedAuthority = new ArrayList<>();
       grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));

       for(Authority _auth: account.getAuthorities()){
           grantedAuthority.add(new SimpleGrantedAuthority(_auth.getName()));
       }

       //user nesnesi döner, nesne kullanıcının kimlik doğrulama ve yetkilendirme bilgilerini içerir. 
       return new User(account.getEmail(), account.getPassword(), grantedAuthority);
    }
    
    public Optional<Account> findOneByEmail(String email){
        return accountRepository.findOneByEmailIgnoreCase(email);
    }

    public Optional<Account> findById(Long id){
        return accountRepository.findById(id);
    }

    public Optional<Account> findByToken(String token){
        return accountRepository.findByToken(token);
    }
}
