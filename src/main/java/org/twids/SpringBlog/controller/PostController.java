package org.twids.SpringBlog.controller;

import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.twids.SpringBlog.models.Account;
import org.twids.SpringBlog.models.Post;
import org.twids.SpringBlog.services.AccountService;
import org.twids.SpringBlog.services.PostService;
import jakarta.validation.Valid;

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;
    
    //principal arayüzü, kimliği doğrulanmış bir kullanıcının kimlik bilgilerini temsil eder. 
    //principal nesnesini kullanarak, kimliği doğrulanmış olan kullanıcının bilgilerine erişebiliriz.

    //@PathVariable notasyonu, metod parametresinin URL'deki bir değeri almasını sağlar. 
    //@GetMapping, web tarayıcısından gelen GET taleplerine yanıt vermek için kullanılır
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal){
        //url'deki id, post veritabanında var mı yok mu? varsa "postu" optionalPost'da tut.
        Optional<Post> optionalPost = postService.getById(id);
        String authUser = "email";
        //optionalPostta post bulunuyorsa(boş değilse içi) postu getir ve post nesnesinde tut.
        //post nesnesini de html'de kullanmak için modele ekle.
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            
            //kulannıcı login yaptıysa, kullanıcı adını(email) al.
            if (principal != null){
                authUser = principal.getName();
            }
            //kullanıcı postu atan kişi ise modele "isOwner" olarak ekle.
            if (authUser.equals(post.getAccount().getEmail())){
                model.addAttribute("isOwner", true);
            }else{
                model.addAttribute("isOwner", false);
            }

            return "post_views/post";
        }else{
            //optionalPost boşşa, öyle bir post bulunamadı hatası.   
            return "404";
        }
    }


    //@PreAuthorize notasyonu, Spring Security'de bir metodun belirli bir koşul sağlandığında erişilebileceğini belirtmek için kullanılır. Yani, oturum açmış ise.
    //kullanıcının "Add Post" başlığını görebilmesini ve linke yönlendirmeyi header'da sağlıyoruz."th:href="@{/posts/add}">Add Post</a>"
    //linkin sayfayı getirmesini GET methodu ile sağlıyoruz ve yine isAuthenticated() olup olmadığı kontrol ediliyor.
    //ÜSTELİK YİNE principal ile bi daha kontrol ediyoruz...
    @GetMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPost(Model model, Principal principal) {
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        
        //login yapan kullanıcı hesabı veritabanında da kayıtlıysa(ekstra security),
        //kullanıcı hesap bilgilerini posta ekle(Author: '+ ${post.account.firstname}), postu modele ekle, add post sayfasını döndür.
        if(optionalAccount.isPresent()){
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add";
        }else{
            return "redirect:/";
        }
    }


    //@Valid,..., BindingResult parametrelerinin sırası önemli!!
    @PostMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@Valid @ModelAttribute Post post, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()){
            return "post_views/post_add";
        }
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0){
            return "redirect:/?error";
        }
        postService.save(post);
        return "redirect:/posts/"+post.getId();
    }


    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String getPostForEdit(@PathVariable Long id, Model model){
        Optional<Post> optionalPost = postService.getById(id);
        //post/id sayfasındayız, edit butonuna bastık. Veritabanında böyle bir post var mı yok mu bakıyor.
        //varsa postu post nesnesinde tut, modele ekle, post/id/edit sayfasını döndür.
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_views/post_edit";
        }else{
            return "404";
        }
    }

    
    @PostMapping("/posts/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@Valid @ModelAttribute Post post, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()){
            return "post_views/post_edit";
        }
        
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post existingPost = optionalPost.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());
            postService.save(existingPost);
        }
        return "redirect:/posts/"+post.getId();
    }

    
    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long id){
        Optional<Post> optionalPost = postService.getById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/";
        }else{
            return "redirect:/?error";
        }
    }
}