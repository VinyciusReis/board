package com.br.exemplo.service;

import java.sql.SQLException;
import java.util.Optional;

import com.br.exemplo.dao.BoardColumnDAO;
import com.br.exemplo.dao.BoardDAO;
import com.br.exemplo.entity.Board;

public class BoardService {

    private final BoardDAO boardDAO = new BoardDAO();
    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO();

    public Board insert(final Board board) throws SQLException {
        boardDAO.insert(board);
        var columns = board.getColumns().stream().map(
            c -> {
                c.setBoard(board);
                return c;
            }
        ).toList();

        for(var column : columns) {
            boardColumnDAO.insert(column);
        }
        return board;
    }

    public Optional<Board> findById(final Long id) throws SQLException {
        var optional = boardDAO.findById(id);
        if(optional.isPresent()) {
            var entity = optional.get();
            entity.setColumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    public boolean delete(final Long id) throws SQLException {
        var optional = boardDAO.findById(id);
        if(optional.isPresent()) {
            boardDAO.delete(id);
            return true;
        }
        return false;
    }
}