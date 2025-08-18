package com.br.exemplo.dao;

import static com.br.exemplo.entity.BoardColumnKindEnum.findByName;
import static com.br.exemplo.persistence.Connection.getConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.br.exemplo.entity.BoardColumn;

public class BoardColumnDAO {
    
    public void insert(final BoardColumn boardColumn) throws SQLException {
        var sql = "INSERT INTO boards_columns (name, `order`, kind, board_id) VALUES (?, ? , ?, ?);";
        try(var statement = getConnection().prepareStatement(sql)) {
            var i = 1;
            statement.setString(i++, boardColumn.getName());
            statement.setInt(i++, boardColumn.getOrder());
            statement.setString(i++, boardColumn.getKind().name());
            statement.setLong(i, boardColumn.getBoard().getId());
            statement.executeUpdate();
        }
    }

    public List<BoardColumn> findByBoardId(final Long cardId) throws SQLException {
        List<BoardColumn> columns = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind, board_id FROM boards_columns WHERE board_id = ?;";
        try(var statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, cardId);
            var resultSet = statement.executeQuery();
            while(resultSet.next()) {
                var boardColumn = new BoardColumn();
                boardColumn.setId(resultSet.getLong("id"));
                boardColumn.setName(resultSet.getString("name"));
                boardColumn.setOrder(resultSet.getInt("order"));
                boardColumn.setKind(findByName(resultSet.getString("kind")));
                boardColumn.getBoard().setId(cardId);
                columns.add(boardColumn);
            }
        }
        return columns;
    }
}