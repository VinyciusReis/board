package com.br.exemplo.ui;

import static com.br.exemplo.entity.BoardColumnKindEnum.findByName;
import static com.br.exemplo.entity.BoardColumnKindEnum.PENDING;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.br.exemplo.entity.Board;
import com.br.exemplo.entity.BoardColumn;
import com.br.exemplo.entity.BoardColumnKindEnum;
import com.br.exemplo.service.BoardService;

public class MainMenu {

    private Scanner scanner = new Scanner(System.in).useDelimiter(",|\\R");

    private final BoardService boardService = new BoardService();

    public void execute() throws SQLException {
        System.out.println("Bem-vindo ao gerenciador de boards. Escolha uma das opções abaixo:");
        while(true) {
            System.out.println("1- Criar um novo board");
            System.out.println("2- Selecionar um board");
            System.out.println("3- Remover um board");
            System.out.println("4- Sair");
            var option = scanner.nextInt();
            switch(option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida! Seleciona uma das opções do menu.");
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new Board();
        System.out.println("Informe o nome do seu board:");
        entity.setName(scanner.next());

        System.out.println("Quantas colunas deseja criar além das 3 colunas obrigatórias?");
        var additionalColumns = scanner.nextInt();

        var columns = createInitialColumns(additionalColumns);

        for(int i=0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna PENDING do board:");
            var columnName = scanner.next();
            var pendingColumn = createColumn(columnName, PENDING, i+1);
            columns.add(i+1, pendingColumn);
        }
        entity.setColumns(columns);
        boardService.insert(entity);
    }

    private void selectBoard() throws SQLException {
        System.out.println("Digite o id do board que deseja selecionar:");
        var id = scanner.nextLong();
        var optional = boardService.findById(id);
            optional.ifPresentOrElse(
                b ->  {
                    new BoardMenu(b).execute();
                },
                () -> System.out.printf("Não foi encontrado um board com id %s\n", id)
            );
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Digite o id do board que deseja remover:");
        var id = scanner.nextLong();
        if(boardService.delete(id)) {
            System.out.println("Board removido com sucesso!");
        }else {
            System.out.printf("Board com id %s não existe!\n", id);
        }
    }

    private List<BoardColumn> createInitialColumns(int additionalColumns) {
        List<BoardColumn> columns = new ArrayList<>();
        var initialBoardColumnKindList = BoardColumnKindEnum.getInitialKindsList();
        var boardColumnCount = initialBoardColumnKindList.size() + additionalColumns;
        var order = 0;
        for(int i=0; i < initialBoardColumnKindList.size(); i++) {
            System.out.printf("Informe o nome da coluna %s\n", initialBoardColumnKindList.get(i));
            var columnName = scanner.next();
            if(order > 0) {
                order = boardColumnCount - (additionalColumns--);
            }
            var column = createColumn(columnName, findByName(initialBoardColumnKindList.get(i)), order++);
            columns.add(column);
        }
        return columns;
    }

    private BoardColumn createColumn(String name, BoardColumnKindEnum kind, int order) {
        var boardColumn = new BoardColumn();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}