package com.projecteams.project_management.user.dto.request;

import com.projecteams.project_management.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;

    @NotBlank(message = "Email is required")

    private String email;

    private String password;

    private boolean isActive;

    public User toEntity(User user){
        if (Objects.isNull(user)) {
            user = new User();
            user.setPassword(password);

        }
        user.setId(id);
        user.setEmail(email);

        return user;
    }

}
