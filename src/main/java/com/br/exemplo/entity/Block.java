package com.br.exemplo.entity;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Block {

    private Long id;
    private String block_reason;
    private OffsetDateTime blocked_at;
    private String unblock_reason;
    private OffsetDateTime unblocked_at;
}
