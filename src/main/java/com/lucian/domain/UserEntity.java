package com.lucian.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",
            unique = true,
            nullable = false)
    private Integer userId;

    @Column(name = "email",
            unique = true,
            nullable = false,
            columnDefinition = "varbinary(255)")
    @ColumnTransformer(
            read = "AES_DECRYPT(email, 'R5#-l8^.Lk--zJ')",
            write = "AES_ENCRYPT(?, 'R5#-l8^.Lk--zJ')")
    private String email;

    @Column(name = "password",
            unique = true,
            nullable = false,
            columnDefinition = "varbinary(255)")
    @ColumnTransformer(
            read = "AES_DECRYPT(password, 'R5#-l8^.Lk--zJ')",
            write = "AES_ENCRYPT(?, 'R5#-l8^.Lk--zJ')")
    private String password;

    @Column(name = "first_name",
            nullable = false,
            columnDefinition = "varbinary(255)")
    @ColumnTransformer(
            read = "AES_DECRYPT(first_name, 'R5#-l8^.Lk--zJ')",
            write = "AES_ENCRYPT(?, 'R5#-l8^.Lk--zJ')")
    private String firstName;

    @Column(name = "last_name",
            nullable = false,
            columnDefinition = "varbinary(255)")
    @ColumnTransformer(
            read = "AES_DECRYPT(last_name, 'R5#-l8^.Lk--zJ')",
            write = "AES_ENCRYPT(?, 'R5#-l8^.Lk--zJ')")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "is_active")
    private Boolean isActive;


}