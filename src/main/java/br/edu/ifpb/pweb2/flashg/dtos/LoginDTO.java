package br.edu.ifpb.pweb2.flashg.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @Email
    private String email;

    @Size(min = 8, max = 64)
    private String password;
}
