package com.br.exemplo.entity;

import static com.br.exemplo.entity.BoardColumnKindEnum.INITIAL;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Board {

    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumn> columns = new ArrayList<>();

    public BoardColumn getInitialColumn() {
        return columns.stream()
                .filter(c -> c.getKind().equals(INITIAL))
                .findFirst().orElseThrow();
    }
}
