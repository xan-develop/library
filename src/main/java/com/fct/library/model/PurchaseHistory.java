package com.fct.library.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_history")
@Schema(description = "Historial de compras de libros")
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la compra", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_copy_id", nullable = false)
    @Schema(description = "Copia del libro comprado")
    private BookCopy bookCopy;

    @Column(nullable = false)
    @Schema(description = "Monto de la compra", example = "29.99")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Usuario que realizó la compra")
    private User user;

    @Column(nullable = false)
    @Schema(description = "Fecha de la compra", example = "2025-03-18")
    private LocalDate date;

    @Column(nullable = false, length = 255)
    @Schema(description = "Método de pago", example = "Tarjeta de crédito")
    private String method;

    @Column(unique = true, length = 50)
    @Schema(description = "ID del pago (puede venir de una pasarela de pago)", example = "PAY123456")
    private String paymentId;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookCopy getBookCopy() {
        return this.bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

}
