package org.iesvdm.tddjava.connect4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Connect4TDDSpec {

    private Connect4TDD tested;

    private OutputStream output;

    @BeforeEach
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();
        tested = new Connect4TDD(new PrintStream(output));
    }

    /*
     * The board is composed by 7 horizontal and 6 vertical empty positions
     */

    @Test
    public void whenTheGameStartsTheBoardIsEmpty() {
        assertThat(tested.getNumberOfDiscs()).isEqualTo(0);
    }

    /*
     * Players introduce discs on the top of the columns.
     * Introduced disc drops down the board if the column is empty.
     * Future discs introduced in the same column will stack over previous ones
     */

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        /*
            LAMBDA FUNCION ANONIMA EN ESTE CASO SIN ARGUMENTOS DE ENTRADA Y DEVOLVIENDO VOID
            DADO QUE NO HAY RETURN DENTRO DEL BLOQUE LAMBDA
            () -> {

            }
         */
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            tested.putDiscInColumn(7);
        });
        RuntimeException runtimeException2 = assertThrows(RuntimeException.class, () -> {
            tested.putDiscInColumn(-1);
        });

        assertThat(runtimeException.getMessage()).isEqualTo("Invalid column 7");
        assertThat(runtimeException.getMessage()).isEqualTo("Invalid column -1");
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
        assertThat(tested.putDiscInColumn(1)).isEqualTo(0);
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        tested.putDiscInColumn(1);
        assertThat(tested.putDiscInColumn(1)).isEqualTo(1);
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {
        int cantidadAntes = tested.getNumberOfDiscs();
        tested.putDiscInColumn(1);
        assertThat(tested.getNumberOfDiscs()).isEqualTo(cantidadAntes+1);
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            for (int i = 0; i < 7; i++) {
                tested.putDiscInColumn(0);
            }
        });
        assertThat(runtimeException.getMessage()).isEqualTo("No more room in column 0");
    }

    /*
     * It is a two-person game so there is one colour for each player.
     * One player uses red ('R'), the other one uses green ('G').
     * Players alternate turns, inserting one disc every time
     */

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed() {
        assertThat(tested.getCurrentPlayer()).isEqualTo("R");
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsRed() {
        assertThat(tested.getCurrentPlayer()).isEqualTo("R");
    }

    /*
     * We want feedback when either, event or error occur within the game.
     * The output shows the status of the board on every move
     */

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {

    }

    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {

    }

    /*
     * When no more discs can be inserted, the game finishes and it is considered a draw
     */

    @Test
    public void whenTheGameStartsItIsNotFinished() {
        assertThat(tested.isFinished()).isEqualTo(false);
    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                tested.putDiscInColumn(i);
            }
        }
        assertThat(tested.isFinished()).isEqualTo(true);
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight vertical line then that player wins
     */

    @Test
    public void when4VerticalDiscsAreConnectedThenThatPlayerWins() {
        for (int i = 0; i < 3; i++) {
            tested.putDiscInColumn(0);
            tested.putDiscInColumn(1);
        }
        tested.putDiscInColumn(0);
        assertThat(tested.getWinner()).isEqualTo("R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight horizontal line then that player wins
     */

    @Test
    public void when4HorizontalDiscsAreConnectedThenThatPlayerWins() {
        for (int i = 0; i < 3; i++) {
            tested.putDiscInColumn(i);
            tested.putDiscInColumn(i);
        }
        tested.putDiscInColumn(3);
        assertThat(tested.getWinner()).isEqualTo("R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight diagonal line then that player wins
     */

    @Test
    public void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
        //diagonal ascendente
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(3);
        assertThat(tested.getWinner()).isEqualTo("R");
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        //diagonal descendente
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(6);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(0);
        assertThat(tested.getWinner()).isEqualTo("R");
    }
}
