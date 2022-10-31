//package com.example.tattooweb.tattoo;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
//@Entity
//public class Tattoo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
//    @Column(name = "id", nullable = false)
//    private Long id;
//    private int length;
//    private int width;
//    @Transient
//    private int totalArea;
//    private TattooComplexity tattooComplexity;
//
//    public int getTotalArea() {
//        return this.length*this.width;
//    }
//
//}