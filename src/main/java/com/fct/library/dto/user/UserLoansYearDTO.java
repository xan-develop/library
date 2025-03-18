package com.fct.library.dto.user;

public class UserLoansYearDTO {
    private Long id;
    private String name;
    private Long loanCount;
    
    public UserLoansYearDTO() {
    }
    public UserLoansYearDTO(Long id, String name, Long loanCount) {
        this.id = id;
        this.name = name;
        this.loanCount = loanCount;
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLoanCount() {
        return this.loanCount;
    }

    public void setLoanCount(Long loanCount) {
        this.loanCount = loanCount;
    }

}
