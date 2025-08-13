package com.br.exemplo.dao;

import java.sql.SQLException;
import java.util.Optional;

import com.br.exemplo.entity.Board;
import static com.br.exemplo.persistence.Connection.getConnection;

public class BoardDAO {

    public void insert(Board board) throws SQLException {
        var sql = "INSERT INTO boards (name) VALUES (?)";
        var statement = getConnection().prepareStatement(sql);

        statement.setString(1, board.getName());

        statement.executeUpdate();
    }

    public void update() {

    }

    public void delete(final Long id) throws SQLException {

    }

    public Optional<Board> findById(final Long id) throws SQLException {
        var sql = "SELECT * FROM boards WHERE id = ?";
        var statement = getConnection().prepareStatement(sql);

        statement.setLong(1, id);
        var resultSet = statement.executeQuery();

        if(resultSet.next()) {
            Board board = new Board();
            board.setId(resultSet.getLong("id"));
            board.setName(resultSet.getString("name"));

            return Optional.of(board);
        }
        return Optional.empty();
    }
}
