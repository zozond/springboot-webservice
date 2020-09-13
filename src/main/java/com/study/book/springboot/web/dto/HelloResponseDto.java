package com.study.book.springboot.web.dto;

public class HelloResponseDto {
    private String name;
    private int amount;

    public HelloResponseDto(String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
