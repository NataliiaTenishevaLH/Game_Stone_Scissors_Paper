import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mockito;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;


public class DefineWinnerTest {
    DefineWinner fixWinner;

    public DefineWinnerTest() {
        fixWinner = new DefineWinner();
    }

    @Test
    public void testAddingDataToStatistics() {
        DefineWinner mock = mock(DefineWinner.class);
        fixWinner.doClear();
        fixWinner.setWinner(1, Results.Winner);
        when(mock.getSize()).thenReturn(fixWinner.getSize());
        Assertions.assertEquals(mock.getSize(), 1);
    }

    @Test
    public void testMethodDefineWinner() {
        DefineWinner mock = mock(DefineWinner.class);
        fixWinner.defineUserWinner(1, "Scissors", "Paper");
        fixWinner.defineUserWinner(2, "Scissors", "Paper");
        fixWinner.defineUserWinner(3, "Scissors", "Paper");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());

        //Проверяем, что вернет Winner, если пользователь все время выигрывал
        Assertions.assertEquals(mock.getWinner(), Results.Winner);

        fixWinner.doClear();

        fixWinner.defineUserWinner(1, "Rock", "Scissors");
        fixWinner.defineUserWinner(2, "Paper", "Scissors");
        fixWinner.defineUserWinner(3, "Rock", "Scissors");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());

        //Проверяем, что вернет Winner, если пользователь выигрывал больше чем проигрывал
        Assertions.assertEquals(mock.getWinner(), Results.Winner);

        fixWinner.doClear();

        fixWinner.defineUserWinner(1, "Rock", "Paper");
        fixWinner.defineUserWinner(2, "Paper", "Scissors");
        fixWinner.defineUserWinner(3, "Paper", "Scissors");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());

        //Проверяем, что вернет Loser, если пользователь выигрывал меньше чем проигрывал
        Assertions.assertEquals(mock.getWinner(), Results.Loser);

        fixWinner.doClear();
        fixWinner.defineUserWinner(1, "Rock", "Scissors");
        fixWinner.defineUserWinner(2, "Rock", "Scissors");
        fixWinner.defineUserWinner(3, "Paper", "Scissors");
        fixWinner.defineUserWinner(4, "Paper", "Scissors");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());

        //Проверяем, что вернет программа вернет ничью, если выигрышу било поровну
        Assertions.assertEquals(mock.getWinner(), Results.Standoff);

        fixWinner.doClear();
        fixWinner.defineUserWinner(1, "Rock", "Rock");
        fixWinner.defineUserWinner(2, "Rock", "Scissors");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());
        Assertions.assertEquals(mock.getWinner(), Results.Winner);

        fixWinner.doClear();
        fixWinner.defineUserWinner(1, "Rock", "Scissors");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());
        Assertions.assertEquals(mock.getWinner(), Results.Winner);

        fixWinner.doClear();
        fixWinner.defineUserWinner(1, "Rock", "Paper");
        fixWinner.defineUserWinner(2, "Paper", "Rock");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());
        Assertions.assertEquals(mock.getWinner(), Results.Standoff);

        fixWinner.doClear();
        fixWinner.defineUserWinner(1, "Rock", "Rock");
        fixWinner.defineUserWinner(2, "Paper", "Rock");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());
        Assertions.assertEquals(mock.getWinner(), Results.Winner);
    }

    @RepeatedTest(3)
    void repeatedTest(TestInfo testInfo) {
        DefineWinner mock = mock(DefineWinner.class);
        fixWinner.defineUserWinner(1, "Scissors", "Paper");
        fixWinner.defineUserWinner(2, "Scissors", "Paper");
        fixWinner.defineUserWinner(3, "Scissors", "Paper");
        when(mock.getWinner()).thenReturn(fixWinner.getWinner());

        //Проверяем, что вернет Winner, если пользователь все время выигрывал
        Assertions.assertEquals(mock.getWinner(), Results.Winner);
    }

    @Test
    void testExpectedException() {
        DefineWinner mock = mock(DefineWinner.class);

        //Проверка на RuntimeException
        when(mock.getWinner()).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class, () -> {
            mock.defineUserWinner(1, "Paper", "Rock");
            mock.getWinner();
        });
    }
}
