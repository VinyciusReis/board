package com.br.exemplo.entity;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {

    private Long id;
    private String title;
    private String descrption;
    private OffsetDateTime created_at;
    private BoardColumn boardColumn = new BoardColumn();
}
