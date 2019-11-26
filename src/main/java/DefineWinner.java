import java.util.*;

public class DefineWinner {
    private Map<Integer, Enum<Results>> statictics = new HashMap<Integer, Enum<Results>>();
    private int gameCount;

    public void defineUserWinner(int gameCount, String answerFirstParticipant, String answerSecondParticipant) {
        Results result;

        if (answerFirstParticipant.equals(answerSecondParticipant)) {
            result = Results.Standoff;
        } else if (
                (answerFirstParticipant.equals("Scissors") && (answerSecondParticipant.equals("Paper")))
                        || (answerFirstParticipant.equals("Paper") && (answerSecondParticipant.equals("Rock")))
                        || (answerFirstParticipant.equals("Rock") && (answerSecondParticipant.equals("Scissors")))
        ) {
            result = Results.Winner;
        } else {
            result = Results.Loser;
        }
        setWinner(gameCount, result);
        gameCount++;
    }

    public void setWinner(Integer gameCount, Results result) {
        statictics.put(gameCount, result);
    }

    public Enum<Results> getWinner() {

        List<Enum<Results>> winnerResults = new ArrayList(statictics.values());

        if (winnerResults.size() == 0) {
            return null;
        }

        long countOfWin = winnerResults.stream()
                .filter(u -> u.equals(Results.Winner))
                .count();
        if (countOfWin != 0) {
            double result = (countOfWin * 100 / winnerResults.size());
            if (result > 50) {
                return Results.Winner;
            } else if (result < 50) {
                return Results.Loser;
            } else if (result == 50) {
                long countOfStandoff = winnerResults.stream()
                        .filter(u -> u.equals(Results.Standoff))
                        .count();
                if (countOfStandoff == 0) {
                    return Results.Standoff;
                } else {
                    return Results.Winner;
                }

            }
            return Results.Standoff;
        } else {
            long countOfStandoff = winnerResults.stream()
                    .filter(u -> u.equals(Results.Standoff))
                    .count();
            if (countOfStandoff == 0) {
                return Results.Loser;
            } else {
                if ((countOfStandoff * 100 / winnerResults.size()) > 50) {
                    return Results.Standoff;
                } else {
                    return Results.Loser;
                }
            }
        }
    }


    public void doClear() {
        statictics.clear();
    }

    public int getSize() {
        return statictics.size();
    }

}
