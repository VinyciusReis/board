package com.br.exemplo.dto;

import java.time.OffsetDateTime;

public record CardDetailsDTO(Long id,
                             String title,
                             String description,
                             boolean blocked,
                             OffsetDateTime blocked_at,
                             String block_reason,
                             int blocks_amount,
                             Long column_id,
                             String column_name
) {
}