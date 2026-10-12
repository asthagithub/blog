package com.example.blogging.blog.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private int id;

    @NotEmpty
    @Length(min = 3)
    private String name;

    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = ".*a1.*")
    @JsonIgnore
    private String password;
}
