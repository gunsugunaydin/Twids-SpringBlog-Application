package org.twids.SpringBlog.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.twids.SpringBlog.models.Account;
import org.twids.SpringBlog.models.Authority;
import org.twids.SpringBlog.models.Post;
import org.twids.SpringBlog.services.AccountService;
import org.twids.SpringBlog.services.AuthorityService;
import org.twids.SpringBlog.services.PostService;
import org.twids.SpringBlog.util.constants.Privillages;
import org.twids.SpringBlog.util.constants.Roles;

@Component
public class SeedData implements CommandLineRunner{

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {

       for(Privillages auth: Privillages.values()){
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivillage());
            authorityService.save(authority);

       }
        
       Account account01 = new Account();
       Account account02 = new Account();
       Account account03 = new Account();
       Account account04 = new Account();

       account01.setEmail("user@user.com");
       account01.setPassword("pass9876.");
       account01.setFirstname("User");
       account01.setLastname("UserLastname");
       account01.setAge(25);
       account01.setDate_of_birth(LocalDate.parse("1990-01-01"));
       account01.setGender("Male");

       account02.setEmail("gunsugunay98@gmail.com");
       account02.setPassword("pass9876.");
       account02.setFirstname("GünsuTheAdmin");
       account02.setLastname("Günaydın");
       account02.setRole(Roles.ADMIN.getRole());
       account02.setAge(25);
       account02.setDate_of_birth(LocalDate.parse("1998-11-18"));
       account02.setGender("Female");

       account03.setEmail("editor@editor.com");
       account03.setPassword("pass9876.");
       account03.setFirstname("Editor");
       account03.setLastname("EditorLastname");
       account03.setRole(Roles.EDITOR.getRole());
       account03.setAge(55);
       account03.setDate_of_birth(LocalDate.parse("1969-03-01"));
       account03.setGender("Male");

       account04.setEmail("super_editor@editor.com");
       account04.setPassword("pass9876.");
       account04.setFirstname("Editor");
       account04.setLastname("SuperEditorLastname");
       account04.setRole(Roles.EDITOR.getRole());
       account04.setAge(40);
       account04.setDate_of_birth(LocalDate.parse("1986-11-01"));
       account04.setGender("Female");
       
       Set<Authority> authorities = new HashSet<>();
       authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
       authorityService.findById(Privillages.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
       account04.setAuthorities(authorities);

       accountService.save(account01);
       accountService.save(account02);
       accountService.save(account03);
       accountService.save(account04);
       
       List<Post> posts = postService.findAll();
       if (posts.size() == 0){
            Post post01 = new Post();
            post01.setTitle("Tourism in Japan");
            post01.setBody("""
              <img src="https://www.state.gov/wp-content/uploads/2019/04/Japan-2107x1406.jpg" alt="Coffee cup" style="max-width:300px; height:180px; margin-bottom:20px;">
              <p>Tourism in Japan is a major industry and contributor to the Japanese economy. Foreigners visit Japan to see natural wonders, cities, historic landmarks, and entertainment venues. Japanese people seek similar attractions, as well as recreation and vacation areas. In 2019, Japan attracted 31.88 million international tourists.[1] Japan welcomed 2.78 million visitor arrivals in February 2024, surpassing 2019 levels.[2]

              Japan has 21 World Heritage Sites, including Himeji Castle, Historic Monuments of Ancient Kyoto and Nara. Popular attractions for foreigners include Tokyo and Osaka, Mount Fuji, ski resorts such as Niseko in Hokkaido, Okinawa, riding the Shinkansen and taking advantage of Japan's hotel and hotspring network.

              The 2017 Travel and Tourism Competitiveness Report ranked Japan 4th out of 141 countries overall, which was the highest in Asia. Japan gained relatively high scores in almost all of the featured aspects, such as health and hygiene, safety and security, cultural resources and business travel.[3] In the 2021 edition of the report, now called Travel and Tourism Development Index, Japan reached the 1st place.[4]
            
            """);
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Spring Boot Model–view–controller framework");
            post02.setBody("""
                      
              <h3><strong>Model–view–controller framework</strong></h3>
              <p><a href="https://en.wikipedia.org/wiki/File:Spring5JuergenHoeller2.jpg"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Spring5JuergenHoeller2.jpg/220px-Spring5JuergenHoeller2.jpg" alt="" srcset="//upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Spring5JuergenHoeller2.jpg/330px-Spring5JuergenHoeller2.jpg 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Spring5JuergenHoeller2.jpg/440px-Spring5JuergenHoeller2.jpg 2x" sizes="100vw" width="220"></a></p><p>&nbsp;</p><p>Spring MVC/Web Reactive presentation given by Jürgen Höller</p><p>The Spring Framework features its own <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller">model–view–controller</a> (MVC) <a href="https://en.wikipedia.org/wiki/Web_application_framework">web application framework</a>, which was not originally planned. The Spring developers decided to write their own Web framework as a reaction to what they perceived as the poor design of the (then) popular <a href="https://en.wikipedia.org/wiki/Jakarta_Struts">Jakarta Struts</a> Web framework,<a href="https://en.wikipedia.org/wiki/Spring_Framework#cite_note-21">[21]</a> as well as deficiencies in other available frameworks. In particular, they felt there was insufficient separation between the presentation and request handling layers, and between the request handling layer and the model.<a href="https://en.wikipedia.org/wiki/Spring_Framework#cite_note-22">[22]</a></p><p>Like Struts, Spring MVC is a request-based framework. The framework defines <a href="https://en.wikipedia.org/wiki/Strategy_pattern">strategy</a> interfaces for all of the responsibilities that must be handled by a modern request-based framework. The goal of each interface is to be simple and clear so that it's easy for Spring MVC users to write their own implementations, if they so choose. MVC paves the way for cleaner front end code. All interfaces are tightly coupled to the <a href="https://en.wikipedia.org/wiki/Java_Servlet">Servlet API</a>. This tight coupling to the Servlet API is seen by some as a failure on the part of the Spring developers to offer a high-level abstraction for Web-based applications[<a href="https://en.wikipedia.org/wiki/Wikipedia:Citation_needed"><i>citation needed</i></a>]. However, this coupling makes sure that the features of the Servlet API remain available to developers while also offering a high abstraction framework to ease working with it.</p><p>The DispatcherServlet class is the <a href="https://en.wikipedia.org/wiki/Front_controller">front controller</a><a href="https://en.wikipedia.org/wiki/Spring_Framework#cite_note-23">[23]</a> of the framework and is responsible for delegating control to the various interfaces during the execution phases of an <a href="https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol">HTTP request</a>.</p><p>The most important interfaces defined by Spring MVC, and their responsibilities, are listed below:</p><ul><li>Controller: comes between Model and View to manage incoming requests and redirect to proper response. Controller will map the http request to corresponding methods. It acts as a gate that directs the incoming information. It switches between going into model or view.</li><li>HandlerAdapter: execution of objects that handle incoming requests</li><li>HandlerInterceptor: interception of incoming requests comparable, but not equal to Servlet filters (use is optional and not controlled by DispatcherServlet).</li><li>HandlerMapping: selecting objects that handle incoming requests (handlers) based on any attribute or condition internal or external to those requests</li><li>LocaleResolver: resolving and optionally saving of the <a href="https://en.wikipedia.org/wiki/Locale_(computer_software)">locale</a> of an individual user</li><li>MultipartResolver: facilitate working with file uploads by wrapping incoming requests</li><li>View: responsible for returning a response to the client. Some requests may go straight to view without going to the model part; others may go through all three.</li><li>ViewResolver: selecting a View based on a logical name for the view (use is not strictly required)</li></ul><p>Each strategy interface above has an important responsibility in the overall framework. The abstractions offered by these interfaces are powerful, so to allow for a set of variations in their implementations, Spring MVC ships with implementations of all these interfaces and together offers a feature set on top of the Servlet API. However, developers and vendors are free to write other implementations. Spring MVC uses the Java java.util.Map interface as a data-oriented abstraction for the Model where keys are expected to be string values.</p><p>The ease of testing the implementations of these interfaces seems one important advantage of the high level of abstraction offered by Spring MVC. DispatcherServlet is tightly coupled to the Spring inversion of control container for configuring the web layers of applications. However, web applications can use other parts of the Spring Framework—including the container—and choose not to use Spring MVC.</p>
            
            """);
            
            post02.setAccount(account02);
            postService.save(post02);

            Post post03 = new Post();
            post03.setTitle("About Git");
            post03.setBody("""
               Git (/ɡɪt/)[8] is a distributed version control system: tracking changes in any set of files, usually used for coordinating work among programmers collaboratively developing source code during software development. Its goals include speed, data integrity, and support for distributed, non-linear workflows (thousands of parallel branches running on different systems).[9][10][11]

               Git was originally authored by Linus Torvalds in 2005 for development of the Linux kernel, with other kernel developers contributing to its initial development.[12] Since 2005, Junio Hamano has been the core maintainer. As with most other distributed version control systems, and unlike most client–server systems, every Git directory on every computer is a full-fledged repository with complete history and full version-tracking abilities, independent of network access or a central server.[13] Git is free and open-source software distributed under the GPL-2.0-only license.   
            
            """);
            post03.setAccount(account01);
            postService.save(post03);

            Post post04 = new Post();
            post04.setTitle("Does face yoga work, or is it just a trend of the popular age?");
            post04.setBody("""
                      
              Face yoga includes exercises and self-massages that target the facial muscles. Some try face yoga with the goal of slimming their face and reducing wrinkles. Face yoga may help boost circulation, relax your facial muscles, and reduce eye strain. Overall, trying face yoga is pretty low-stakes and could be a better fit for some than having any procedures done.

              However, facial movements likely won't achieve long-lasting or significant, noticeable results. Options like filler, skin resurfacing, or cosmetic surgery offer more definite results. 
            
            """);
            
            post04.setAccount(account02);
            postService.save(post04);

            Post post05 = new Post();
            post05.setTitle("Movie review: How to Lose a Guy in 10 Days");
            post05.setBody("""
                <img src="https://m.media-amazon.com/images/S/pv-target-images/872daff6e28b8c264959b4f6a385ba2ac70304817308df192acdedbe0a6cdae3.jpg" alt="Coffee cup" style="max-width:300px; height:180px; margin-bottom:20px;">
                <p>How to Lose a Guy in 10 Days is a 2003 romantic comedy film directed by Donald Petrie, starring Kate Hudson and Matthew McConaughey. It is based on the picture book of the same name by Michele Alexander and Jeannie Long. The book has no narrative, only a list of comedic dating "don'ts", so the characters and plot were created for the film. In the film, advertising executive Benjamin Barry makes a bet that he can make any woman fall in love with him, while women's magazine writer Andie Anderson plans to write an article about how she led a man to dump her, putting them at cross-purposes after they choose each other as their quarries. Andie employs a number of the dating "don'ts" from the picture book in her efforts to get Ben to break up with her.
            """);
            post05.setAccount(account01);
            postService.save(post05);

            Post post06 = new Post();
            post06.setTitle("6 Health Benefits You Didn’t Know Coffee Had");
            post06.setBody("""
               <img src="https://www.nescafe.com/mena/sites/default/files/2023-08/Coffee%20Types%20Banner%20Desktop.jpg" alt="Coffee cup" style="max-width:300px; height:180px; margin-bottom:20px;">
               <p>Ah, coffee, that sweet dark delicious drink which unites us all from different backgrounds, occupations and interests. It brings together both the officer and the criminal, the professor and the student, the boss and the employee.</p>
               <p>Coffee has been around for thousands of years, but it hasn’t been until recently that we’ve discovered just how many incredible benefits it actually has. Are you a coffee drinker? Then check out these six amazing health benefits you may not have known coffee had.</p>
               
               <h3>Coffee protects you from disease.</h3>
               <p>Recent studies have found that drinking coffee helps protect us from a variety of different diseases, such as type II diabetes, Parkinson’s disease, liver disease, Alzheimer’s disease, dementia and various types of cancers. Drinking coffee has even been shown to improve heart health. That’s one amazing drink, if you ask us.</p>
               
               <h3>Coffee is a source of antioxidants.</h3>
               <p>In the Western world, coffee is the greatest source of antioxidants in our diet. Coffee contains an enormous amount of antioxidants. While fruits and vegetables also contain antioxidants, our bodies absorb the most from coffee. In fact, a study by the Journal of Nutrition even showed most people get more antioxidants from coffee than fruits and vegetables… combined.</p>
               
               <h3>Coffee can reduce stress.</h3>
               <p>Researchers recently found even the smell of coffee can help reduce stress, especially that caused by sleep deprivation. A study involving rats at the Seoul National University found those exposed to the smell of coffee had changes in their brain proteins tied to stress. Which means we weren’t imagining it—coffee really does get us out of bed in the morning!</p>
               
               <h3>Coffee fights depression.</h3>
               <p>Not only does coffee make us happier; it also fights depression and reduces the risk of suicide. In one study by the National Institute of Health, participants who drank four or more cups of coffee per day were about 10% less likely to become depressed and have suicidal thoughts. And why does coffee make us so happy? We can thank the antioxidants for that!</p>
               
               <h3>Coffee increases physical ability.</h3>
               <p>Coffee has even been shown to improve athletic performance. Caffeine increases the number of fatty acids in the bloodstream, which in turn are absorbed by muscles and burned for fuel. This saves the body’s reserves of carbohydrates for later on, making coffee an excellent source of energy for endurance athletes.</p>
               
               <h3>Coffee helps you live longer.</h3>
               <p>Wait, what? Coffee can actually make you live longer? Yep! Of course, with the number of diseases it prevents it definitely helps. However, one study from the National Institute of Health even found that drinking coffee lowered the risk of death in men by 20% and in women by 26% over a period of 18-24 years. Eternal life here we come!</p>
               
               <p>There’s no doubt about it, coffee has some great benefits, so test them out and stop in to your local café or restaurant for a delicious cup of joe. And remember: stay healthy, drink coffee.</p>
            """);

            post06.setAccount(account02);
            postService.save(post06);
       }        
    }    
}

