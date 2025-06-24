package com.example.chatx_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {

    private int page = 1;

    private int size = 10;

    private int offset;

    public int getOffset() {
        return (page - 1) * size;
    }

}
