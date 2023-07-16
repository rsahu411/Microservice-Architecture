package com.lcwd.user.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="micro_users")
public class User {

    @Id
   @Column(name="id")
    private String userId;

    @Column(name="name",length= 20)
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="about")
    private String about;
    @Transient
    private List<Rating> ratings=new ArrayList<>();

}
