package com.br.exemplo.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

    private Long id;
    private String name;
    private List<BoardColumn> columns = new ArrayList<>();
}
