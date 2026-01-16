package com.projecteams.project_management.user.dto.request;

import java.util.Objects;

import com.projecteams.project_management.user.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    private boolean isActive;

    public User toEntity(User user) {
        if (Objects.isNull(user)) {
            user = new User();
            user.setPassword(password);

        }
        user.setId(id);
        user.setEmail(email);

        return user;
    }

}
