//package com.tech.user_insights.pojo;
//
//import java.math.BigDecimal;
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
//@Table(name = "order_items")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OrderItem {
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY) 
//	    private Integer id;
//
//	    @ManyToOne
//	    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order"))
//	    private Orders order;
//
//	    @ManyToOne
//	    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "fk_book"))
//	    private BookDetails book;
//
//	    @Column(nullable = false)
//	    private Integer quantity;
//
//	    @Column(nullable = false, precision = 10, scale = 2)
//	    private BigDecimal price;
//
//	    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
//	    private BigDecimal totalPrice;
//}
