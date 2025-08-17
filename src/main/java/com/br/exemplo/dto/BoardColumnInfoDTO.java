package com.br.exemplo.dto;

import com.br.exemplo.entity.BoardColumnKindEnum;

public record BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {

}