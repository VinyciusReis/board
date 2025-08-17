package com.br.exemplo.entity;

import java.util.List;
import java.util.stream.Stream;

public enum BoardColumnKindEnum {
    INITIAL, FINAL, CANCEL, PENDING;

    public static BoardColumnKindEnum findByName(final String name) {
        return Stream.of(BoardColumnKindEnum.values())
                .filter(b -> b.name().equals(name))
                .findFirst()
                .orElseThrow();
    }

    public static List<String> getInitialKindsList() {
        return Stream.of(BoardColumnKindEnum.values())
                .filter(b -> !b.name().equals("PENDING"))
                .map(b -> b.name())
                .toList();
    }
}
