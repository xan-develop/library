package com.fct.library.dto;

import java.util.List;

import com.fct.library.dto.loan.LoanResponseDTO;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<LoanResponseDTO> loans;

    // Constructor
    public UserDTO(Long id, String name, String email, String phone, List<LoanResponseDTO> loans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.loans = loans;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<LoanResponseDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanResponseDTO> loans) {
        this.loans = loans;
    }
}
