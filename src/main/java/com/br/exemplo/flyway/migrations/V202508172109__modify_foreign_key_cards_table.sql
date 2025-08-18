ALTER TABLE cards DROP FOREIGN KEY cards_board_column_id_fk;

ALTER TABLE cards ADD CONSTRAINT cards_board_column_fk
FOREIGN KEY (board_column_id) REFERENCES boards_columns(id)
ON DELETE CASCADE; 