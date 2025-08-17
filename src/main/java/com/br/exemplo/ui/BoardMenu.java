package com.br.exemplo.ui;

import java.sql.SQLException;
import java.util.Scanner;

import com.br.exemplo.dto.BoardColumnInfoDTO;
import com.br.exemplo.entity.Board;
import com.br.exemplo.entity.Card;
import com.br.exemplo.service.CardService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter(",|\\R");
    
    private final CardService cardService = new CardService();

    private Board entity;

    public void execute() {
        try{
            System.out.println("Bem-vindo ao gerenciador de tarefas! Selecione uma das opções abaixo:");
            while(true) {
                System.out.println("1- Criar um card");
                System.out.println("2- Mover um card");
                System.out.println("3- Cancelar um card");
                System.out.println("4- Bloquear um card");
                System.out.println("5- Desbloquear umm card");
                System.out.println("6- Ver card");
                System.out.println("7- Voltar para o menu anterior");
                System.out.println("8- Sair");
                var option = scanner.nextInt();
                switch(option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> cancelCard();
                    case 4 -> blockCard();
                    case 5 -> unblockCard();
                    case 6 -> showCard();
                    case 7 -> System.out.println("Voltando para o menu anterior...");
                    case 8 -> System.exit(0);
                    default -> System.out.println("Opção inválida! Selecione uma das opções do menu.");
                }
                if(option == 7)
                    break;
                }
        }catch(SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void createCard() throws SQLException {
        Card card = new Card();
        System.out.println("Informe o título do card que deseja adicionar:");
        card.setTitle(scanner.next());
        System.out.println("Informe a descrição do card que deseja adicionar:");
        card.setDescrption(scanner.next());
        card.setBoardColumn(entity.getInitialColumn());
        cardService.insert(card);
    }

    private void moveCardToNextColumn() {
        Card card =  new Card();
        System.out.println("Informe o id do card que deseja mover:");
        card.setId(scanner.nextLong());
        var boardColumnsInfo = entity.getColumns().stream()
                                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                                .toList();
        cardService.moveToNextColumn(card.getId(), boardColumnsInfo);
    }

    private void cancelCard() {

    }

    private void blockCard() {

    }

    private void unblockCard() {

    }

    private void showCard() {
        
    }
}