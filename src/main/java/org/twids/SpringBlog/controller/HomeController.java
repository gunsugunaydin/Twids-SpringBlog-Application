package org.twids.SpringBlog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.twids.SpringBlog.models.Post;
import org.twids.SpringBlog.services.PostService;

@Controller
public class HomeController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false, name = "sort_by", defaultValue = "createdAt") String sort_by,
    @RequestParam(required = false, name="per_page", defaultValue = "2") String per_page,
    @RequestParam(required = false, name="page", defaultValue = "1") String page){

        Page<Post> posts_on_page = postService.findAll(Integer.parseInt(page)-1,Integer.parseInt(per_page), sort_by);
        
        //bu kısım, toplam sayfa sayısını alır ve her bir sayfa numarasını içeren bir liste oluşturur. 
        //IntStream.rangeClosed yöntemi, belirli bir aralıktaki tam sayıları üretir.
        int total_pages = posts_on_page.getTotalPages();//5
        List<Integer> pages = new ArrayList<>();
        if (total_pages > 0){
            pages = IntStream.rangeClosed(0, total_pages-1)//0-1-2-3-4
            .boxed().collect(Collectors.toList());//0-1-2-3-4'ü listeye ekle
        }

        List<String> links = new ArrayList<>();

        //bu döngü, her bir sayfa için bir HTML bağlantısı oluşturur. 
        //aktif sayfa belirlenir ve her bağlantının URL'si dinamik olarak oluşturulur.
        if(pages != null){
            for (int link: pages){
                String active = "";
                if(link == posts_on_page.getNumber()){
                    active = "active";
                }
                String _temp_link = "/?per_page="+per_page+"&page="+(link+1)+"&sort_by="+sort_by;
                links.add("<li class=\"page-item "+active+"\"><a href=\""+_temp_link+"\" class='page-link'>"+(link+1)+"</a></li>");
            }
            model.addAttribute("links", links);
        }
        
        model.addAttribute("posts",posts_on_page);
        return "home_views/home";
    }
}
