package com.br.exemplo.dao;

import static com.br.exemplo.persistence.Connection.getConnection;
import static com.br.exemplo.util.OffsetDateTimeConverter.toTimestamp;

import java.sql.SQLException;
import java.time.OffsetDateTime;

public class BlockDAO {

    public void block(final String reason, final Long cardId) throws SQLException {
        var sql = "INSERT INTO blocks(blocked_at, block_reason, card_id) VALUES (?, ?, ?);";
        try(var statement = getConnection().prepareStatement(sql)){
            var i = 1;
            statement.setTimestamp(i++, toTimestamp(OffsetDateTime.now()));
            statement.setString(i++, reason);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public void unblock(final String reason, final Long cardId) throws SQLException{
        var sql = "UPDATE BLOCKS SET unblocked_at = ?, unblock_reason = ? WHERE card_id = ? AND unblock_reason IS NULL;";
        try(var statement = getConnection().prepareStatement(sql)){
            var i = 1;
            statement.setTimestamp(i ++, toTimestamp(OffsetDateTime.now()));
            statement.setString(i ++, reason);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }
}