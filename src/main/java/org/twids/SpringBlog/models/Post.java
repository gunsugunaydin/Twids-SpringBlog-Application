package org.twids.SpringBlog.models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    //@NotEmpty genelde list için, @Notblank field'ler için kullanılır, ikisi de kullanılabilir.
    @NotBlank(message= "Missing Title")
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message= "Missing post body")
    private String body;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //postun 1 twid atanı olabilir, 1 yazan taraf accountda bulunur.
    //Post M-----------1 Account
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = true)
    private Account account;
}
