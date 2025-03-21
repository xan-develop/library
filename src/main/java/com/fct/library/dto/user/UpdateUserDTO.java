package com.fct.library.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "Datos para actualizar un usuario existente")
public class UpdateUserDTO {
    
    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String name;
    
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "Email del usuario", example = "juan.perez@example.com")
    private String email;
    
    @Schema(description = "Número de teléfono del usuario", example = "612345678")
    private String phone;
    
    // Constructores
    public UpdateUserDTO() {
    }
    
    public UpdateUserDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters y setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
