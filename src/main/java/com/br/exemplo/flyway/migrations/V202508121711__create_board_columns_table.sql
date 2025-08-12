CREATE TABLE boards_columns (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    kind VARCHAR(7) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT boards_columns_border_id_fk FOREIGN KEY(board_id) REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT boards_columns_uk UNIQUE KEY unique_board_id_order (board_id, `order`)

)ENGINE=InnoDB DEFAULT CHARSET UTF8