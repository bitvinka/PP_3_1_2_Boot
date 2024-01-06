package com.example.boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


import java.util.Objects;


@Entity(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 20, message = "Имя должно быть в диапазоне от 2 до 20 символов")
    @Column(name="firstName", length = 20, nullable = false, unique = false, columnDefinition = "text")
    private String firstName;

    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 30, message = "Фамилия должна быть в диапазоне от 2 до 30 символов")
    @Column(name="lastName", length = 30, nullable = false, unique = false, columnDefinition = "text")
    private String lastName;

    @NotEmpty(message = "Поле не может быть пустым")
    @Email(message = "Вы ввели не корректный email")
    @Column(name="email", length = 40, nullable = false, unique = true, columnDefinition = "text")
    private String email;


    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
