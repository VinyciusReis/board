package com.br.exemplo.dao;

import java.sql.SQLException;
import java.util.Optional;

import com.br.exemplo.entity.Board;
import com.mysql.cj.jdbc.StatementImpl;

import static com.br.exemplo.persistence.Connection.getConnection;

public class BoardDAO {

    public Board insert(final Board board) throws SQLException {
        var sql = "INSERT INTO Boards (name) VALUES (?);";
        try(var statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, board.getName());
            statement.executeUpdate();
            if(statement instanceof StatementImpl impl) {
                board.setId(impl.getLastInsertID());
            }
        }
        return board;
    }

    public void delete(final Long id) throws SQLException {
        var sql = "DELETE FROM boards WHERE id = ?";
        try(var statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<Board> findById(final Long id) throws SQLException {
        var sql = "SELECT id, name FROM boards WHERE id = ?";
        try(var statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Board board = new Board();
                board.setId(resultSet.getLong("id"));
                board.setName(resultSet.getString("name"));

                return Optional.of(board);
            }
        }
        return Optional.empty();
    }
}