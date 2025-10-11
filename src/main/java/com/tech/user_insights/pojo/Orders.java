//package com.tech.user_insights.pojo;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.ForeignKey;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "orders")
//public class Orders {
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) 
//    private Integer id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_user"))
//    private UserInfo user;
//
//    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
//    private BigDecimal totalPrice;
//
//    @Column(nullable = false, length = 50)
//    private String status = "pending";
//
//    @Column(name = "created_at", updatable = false, insertable = false)
//    private LocalDateTime createdAt;
//}
