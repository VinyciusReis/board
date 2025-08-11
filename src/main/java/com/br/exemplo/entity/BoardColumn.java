package com.br.exemplo.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardColumn {

    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
    private Board board = new Board();
    private List<Card> cards = new ArrayList<>();
}
