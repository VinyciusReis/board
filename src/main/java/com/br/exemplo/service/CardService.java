package com.br.exemplo.service;

import static com.br.exemplo.entity.BoardColumnKindEnum.CANCEL;
import static com.br.exemplo.entity.BoardColumnKindEnum.FINAL;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.br.exemplo.dao.BlockDAO;
import com.br.exemplo.dao.CardDAO;
import com.br.exemplo.dto.BoardColumnInfoDTO;
import com.br.exemplo.dto.CardDetailsDTO;
import com.br.exemplo.entity.Card;
import com.br.exemplo.exception.CardBlockException;
import com.br.exemplo.exception.CardFinishedException;
import com.br.exemplo.exception.EntityNotFoundException;

public class CardService {

    private final CardDAO cardDAO = new CardDAO();

    public Card insert(final Card card) throws SQLException {
        cardDAO.insert(card);
        return card;
    }

    public void moveToNextColumn(final Long cardId, List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        var optional = cardDAO.findById(cardId);
        var card = optional.orElseThrow(
            () -> new EntityNotFoundException("O Card de id %s não foi encontrado!".formatted(cardId))
        );
        if (card.blocked()){
                var message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);
                throw new CardBlockException(message);
            }
        var currentColumn = boardColumnsInfo.stream()
                        .filter(bc -> bc.id().equals(card.column_id()))
                        .findFirst()
                        .orElseThrow();
        if(currentColumn.kind().equals(FINAL)) {
            throw new CardFinishedException("O card já foi finalizado!");
        }
        var nextColumn = boardColumnsInfo.stream()
                        .filter(bc -> bc.order() == currentColumn.order()+1)
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Card já está cancelado!"));
        cardDAO.moveToColumn(nextColumn.id(), cardId);
    }

    public void cancel(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
            var optional = cardDAO.findById(cardId);
            var card = optional.orElseThrow(
                () -> new EntityNotFoundException("O card com id %s não existe!".formatted(cardId))
            );
            if (card.blocked()){
                var message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);
                throw new CardBlockException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(card.column_id()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if(currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado!");
            }
            if(currentColumn.kind().equals(CANCEL)) {
                throw new IllegalStateException("O card já está cancelado!");
            }
            var cancelColumn = boardColumnsInfo.stream()
                                .filter(bc -> bc.kind().equals(CANCEL))
                                .findFirst().get();
            var cancelColumnId = cancelColumn.id();
            cardDAO.moveToColumn(cancelColumnId, cardId);
    }

    public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
            var optional = cardDAO.findById(id);
            var card = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );
            if (card.blocked()){
                var message = "O card %s já está bloqueado".formatted(id);
                throw new CardBlockException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(card.column_id()))
                    .findFirst()
                    .orElseThrow();
            if (currentColumn.kind().equals(FINAL) || currentColumn.kind().equals(CANCEL)){
                var message = "O card está em uma coluna do tipo %s e não pode ser bloqueado"
                        .formatted(currentColumn.kind());
                throw new IllegalStateException(message);
            }
            var blockDAO = new BlockDAO();
            blockDAO.block(reason, id);
    }

    public void unblock(final Long id, final String reason) throws SQLException {
            var optional = cardDAO.findById(id);
            var card = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );
            if (!card.blocked()){
                var message = "O card %s não está bloqueado".formatted(id);
                throw new CardBlockException(message);
            }
            var blockDAO = new BlockDAO();
            blockDAO.unblock(reason, id);
    }

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        return cardDAO.findById(id);
    }
}