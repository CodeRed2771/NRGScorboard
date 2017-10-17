package com.coderedrobotics.nrgscoreboard.ui.controllers.helpers;

import com.coderedrobotics.nrgscoreboard.Match;
import com.coderedrobotics.nrgscoreboard.Team;
import com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController;
import static com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController.PlayoffType.EIGHT_ALLIANCE_DOUBLE;
import static com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController.PlayoffType.EIGHT_ALLIANCE_SINGLE;
import static com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController.PlayoffType.FOUR_ALLIANCE_DOUBLE;
import static com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController.PlayoffType.FOUR_ALLIANCE_SINGLE;
import static com.coderedrobotics.nrgscoreboard.ui.controllers.PlayoffsControllerController.PlayoffType.TWO_ALLIANCE_DOUBLE;

/**
 *
 * @author Michael
 */
public class EliminationsAdvancer {

    private Team qf1Winner1, qf1Winner2;
    private Team qf2Winner1, qf2Winner2;
    private Team qf3Winner1, qf3Winner2;
    private Team qf4Winner1, qf4Winner2;
    private Team sf1Winner1, sf1Winner2;
    private Team sf2Winner1, sf2Winner2;
    private boolean qf1TiebreakerExists = false;
    private boolean qf2TiebreakerExists = false;
    private boolean qf3TiebreakerExists = false;
    private boolean qf4TiebreakerExists = false;
    private boolean sf1TiebreakerExists = false;
    private boolean sf2TiebreakerExists = false;

    public Match[] handleEliminationAdvancement(Match[] matches, Match previousMatch, int currentMatchIndex, PlayoffsControllerController.PlayoffType playOffType) {
        switch (playOffType) {
            case EIGHT_ALLIANCE_DOUBLE:
                if (previousMatch.getType() == Match.MatchType.QUARTERFINAL && previousMatch.isTieBreaker()) {
                    Team winner1 = previousMatch.getRedScore() > previousMatch.getBlueScore() ? previousMatch.getRed1() : previousMatch.getBlue1();
                    Team winner2 = previousMatch.getRedScore() > previousMatch.getBlueScore() ? previousMatch.getRed2() : previousMatch.getBlue2();
                    switch (previousMatch.getNumberInSeries()) {
                        case 1:
                            qf1Winner1 = winner1;
                            qf1Winner2 = winner2;
                            break;
                        case 2:
                            qf2Winner1 = winner1;
                            qf2Winner2 = winner2;
                            break;
                        case 3:
                            qf3Winner1 = winner1;
                            qf3Winner2 = winner2;
                            break;
                        case 4:
                            qf4Winner1 = winner1;
                            qf4Winner2 = winner2;
                            break;
                    }
                }

                if (previousMatch.getType() == Match.MatchType.SEMIFINAL && previousMatch.isTieBreaker()) {
                    Team winner1 = previousMatch.getRedScore() > previousMatch.getBlueScore() ? previousMatch.getRed1() : previousMatch.getBlue1();
                    Team winner2 = previousMatch.getRedScore() > previousMatch.getBlueScore() ? previousMatch.getRed2() : previousMatch.getBlue2();
                    switch (previousMatch.getNumberInSeries()) {
                        case 1:
                            sf1Winner1 = winner1;
                            sf1Winner2 = winner2;
                            break;
                        case 2:
                            sf2Winner1 = winner1;
                            sf2Winner2 = winner2;
                            break;
                    }
                }

                if (currentMatchIndex <= 7 && matches[0].isScored() && matches[1].isScored()
                        && matches[2].isScored() && matches[3].isScored() && matches[4].isScored()
                        && matches[5].isScored() && matches[6].isScored() && matches[7].isScored()) {
                    Team qf1Winner1_1 = matches[0].getRedScore() > matches[0].getBlueScore() ? matches[0].getRed1() : matches[0].getBlue1();
                    Team qf1Winner2_1 = matches[0].getRedScore() > matches[0].getBlueScore() ? matches[0].getRed2() : matches[0].getBlue2();
                    Team qf2Winner1_1 = matches[1].getRedScore() > matches[1].getBlueScore() ? matches[1].getRed1() : matches[1].getBlue1();
                    Team qf2Winner2_1 = matches[1].getRedScore() > matches[1].getBlueScore() ? matches[1].getRed2() : matches[1].getBlue2();
                    Team qf3Winner1_1 = matches[2].getRedScore() > matches[2].getBlueScore() ? matches[2].getRed1() : matches[2].getBlue1();
                    Team qf3Winner2_1 = matches[2].getRedScore() > matches[2].getBlueScore() ? matches[2].getRed2() : matches[2].getBlue2();
                    Team qf4Winner1_1 = matches[3].getRedScore() > matches[3].getBlueScore() ? matches[3].getRed1() : matches[3].getBlue1();
                    Team qf4Winner2_1 = matches[3].getRedScore() > matches[3].getBlueScore() ? matches[3].getRed2() : matches[3].getBlue2();

                    Team qf1Winner1_2 = matches[4].getRedScore() > matches[4].getBlueScore() ? matches[4].getRed1() : matches[4].getBlue1();
                    Team qf1Winner2_2 = matches[4].getRedScore() > matches[4].getBlueScore() ? matches[4].getRed2() : matches[4].getBlue2();
                    Team qf2Winner1_2 = matches[5].getRedScore() > matches[5].getBlueScore() ? matches[5].getRed1() : matches[5].getBlue1();
                    Team qf2Winner2_2 = matches[5].getRedScore() > matches[5].getBlueScore() ? matches[5].getRed2() : matches[5].getBlue2();
                    Team qf3Winner1_2 = matches[6].getRedScore() > matches[6].getBlueScore() ? matches[6].getRed1() : matches[6].getBlue1();
                    Team qf3Winner2_2 = matches[6].getRedScore() > matches[6].getBlueScore() ? matches[6].getRed2() : matches[6].getBlue2();
                    Team qf4Winner1_2 = matches[7].getRedScore() > matches[7].getBlueScore() ? matches[7].getRed1() : matches[7].getBlue1();
                    Team qf4Winner2_2 = matches[7].getRedScore() > matches[7].getBlueScore() ? matches[7].getRed2() : matches[7].getBlue2();

                    if (qf1Winner1_1 != qf1Winner1_2) {
                        matches[8] = new Match(matches[0].getRed1(), matches[0].getRed2(), matches[0].getBlue1(), matches[0].getBlue2(), 9, Match.MatchType.QUARTERFINAL, 1, -1);
                        matches[8].setIsTieBreaker(true);
                        qf1TiebreakerExists = true;
                    } else {
                        qf1Winner1 = qf1Winner1_1;
                        qf1Winner2 = qf1Winner2_1;
                        qf1TiebreakerExists = false;
                    }
                    if (qf2Winner1_1 != qf2Winner1_2) {
                        matches[9] = new Match(matches[1].getRed1(), matches[1].getRed2(), matches[1].getBlue1(), matches[1].getBlue2(), 10, Match.MatchType.QUARTERFINAL, 2, -1);
                        matches[9].setIsTieBreaker(true);
                        qf2TiebreakerExists = true;
                    } else {
                        qf2Winner1 = qf2Winner1_1;
                        qf2Winner2 = qf2Winner2_1;
                        qf2TiebreakerExists = false;
                    }
                    if (qf3Winner1_1 != qf3Winner1_2) {
                        matches[10] = new Match(matches[2].getRed1(), matches[2].getRed2(), matches[2].getBlue1(), matches[2].getBlue2(), 11, Match.MatchType.QUARTERFINAL, 3, -1);
                        matches[10].setIsTieBreaker(true);
                        qf3TiebreakerExists = true;
                    } else {
                        qf3Winner1 = qf3Winner1_1;
                        qf3Winner2 = qf3Winner2_1;
                        qf3TiebreakerExists = false;
                    }
                    if (qf4Winner1_1 != qf4Winner1_2) {
                        matches[11] = new Match(matches[3].getRed1(), matches[3].getRed2(), matches[3].getBlue1(), matches[3].getBlue2(), 12, Match.MatchType.QUARTERFINAL, 4, -1);
                        matches[11].setIsTieBreaker(true);
                        qf4TiebreakerExists = true;
                    } else {
                        qf4Winner1 = qf4Winner1_1;
                        qf4Winner2 = qf4Winner2_1;
                        qf4TiebreakerExists = false;
                    }
                }

                if (qf1Winner1 != null && qf2Winner1 != null && qf3Winner1 != null && qf4Winner1 != null && previousMatch.getType() == Match.MatchType.QUARTERFINAL) {
                    matches[12] = new Match(qf1Winner1, qf1Winner2, qf4Winner1, qf4Winner2, 13, Match.MatchType.SEMIFINAL, 1, 4);
                    matches[13] = new Match(qf2Winner1, qf2Winner2, qf3Winner1, qf3Winner2, 14, Match.MatchType.SEMIFINAL, 2, 4);
                    matches[14] = new Match(qf1Winner1, qf1Winner2, qf4Winner1, qf4Winner2, 15, Match.MatchType.SEMIFINAL, 3, 4);
                    matches[15] = new Match(qf2Winner1, qf2Winner2, qf3Winner1, qf3Winner2, 16, Match.MatchType.SEMIFINAL, 4, 4);
                }

                if (currentMatchIndex == 15) {
                    Team sf1Winner1_1 = matches[12].getRedScore() > matches[12].getBlueScore() ? matches[12].getRed1() : matches[12].getBlue1();
                    Team sf1Winner2_1 = matches[12].getRedScore() > matches[12].getBlueScore() ? matches[12].getRed2() : matches[12].getBlue2();
                    Team sf2Winner1_1 = matches[13].getRedScore() > matches[13].getBlueScore() ? matches[13].getRed1() : matches[13].getBlue1();
                    Team sf2Winner2_1 = matches[13].getRedScore() > matches[13].getBlueScore() ? matches[13].getRed2() : matches[13].getBlue2();

                    Team sf1Winner1_2 = matches[14].getRedScore() > matches[14].getBlueScore() ? matches[14].getRed1() : matches[14].getBlue1();
                    Team sf1Winner2_2 = matches[14].getRedScore() > matches[14].getBlueScore() ? matches[14].getRed2() : matches[14].getBlue2();
                    Team sf2Winner1_2 = matches[15].getRedScore() > matches[15].getBlueScore() ? matches[15].getRed1() : matches[15].getBlue1();
                    Team sf2Winner2_2 = matches[15].getRedScore() > matches[15].getBlueScore() ? matches[15].getRed2() : matches[15].getBlue2();

                    if (sf1Winner1_1 != sf1Winner1_2) {
                        matches[16] = new Match(matches[12].getRed1(), matches[12].getRed2(), matches[12].getBlue1(), matches[12].getBlue2(), 17, Match.MatchType.SEMIFINAL, 1, -1);
                        matches[16].setIsTieBreaker(true);
                        sf1TiebreakerExists = true;
                    } else {
                        sf1Winner1 = sf1Winner1_1;
                        sf1Winner2 = sf1Winner2_1;
                        sf1TiebreakerExists = false;
                    }
                    if (sf2Winner1_1 != sf2Winner1_2) {
                        matches[17] = new Match(matches[13].getRed1(), matches[13].getRed2(), matches[13].getBlue1(), matches[13].getBlue2(), 18, Match.MatchType.SEMIFINAL, 2, -1);
                        matches[17].setIsTieBreaker(true);
                        sf2TiebreakerExists = true;
                    } else {
                        sf2Winner1 = sf2Winner1_1;
                        sf2Winner2 = sf2Winner2_1;
                        sf2TiebreakerExists = false;
                    }
                }

                if (sf1Winner1 != null && sf2Winner1 != null && previousMatch.getType() == Match.MatchType.SEMIFINAL) {
                    matches[18] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 19, Match.MatchType.FINAL, 1, 2);
                    matches[19] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 20, Match.MatchType.FINAL, 2, 2);
                    matches[20] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 21, Match.MatchType.FINAL, 1, 1);
                    matches[20].setIsTieBreaker(true);
                }
                break;
            case EIGHT_ALLIANCE_SINGLE:
                if (currentMatchIndex == 3) {
                    Team qf1Winner1 = matches[0].getRedScore() > matches[0].getBlueScore() ? matches[0].getRed1() : matches[0].getBlue1();
                    Team qf1Winner2 = matches[0].getRedScore() > matches[0].getBlueScore() ? matches[0].getRed2() : matches[0].getBlue2();
                    Team qf2Winner1 = matches[1].getRedScore() > matches[1].getBlueScore() ? matches[1].getRed1() : matches[1].getBlue1();
                    Team qf2Winner2 = matches[1].getRedScore() > matches[1].getBlueScore() ? matches[1].getRed2() : matches[1].getBlue2();
                    Team qf3Winner1 = matches[2].getRedScore() > matches[2].getBlueScore() ? matches[2].getRed1() : matches[2].getBlue1();
                    Team qf3Winner2 = matches[2].getRedScore() > matches[2].getBlueScore() ? matches[2].getRed2() : matches[2].getBlue2();
                    Team qf4Winner1 = matches[3].getRedScore() > matches[3].getBlueScore() ? matches[3].getRed1() : matches[3].getBlue1();
                    Team qf4Winner2 = matches[3].getRedScore() > matches[3].getBlueScore() ? matches[3].getRed2() : matches[3].getBlue2();

                    Match sf1 = new Match(qf1Winner1, qf1Winner2, qf4Winner1, qf4Winner2, 5, Match.MatchType.SEMIFINAL, 1, 2);
                    Match sf2 = new Match(qf2Winner1, qf2Winner2, qf3Winner1, qf3Winner2, 6, Match.MatchType.SEMIFINAL, 2, 2);
                    matches[4] = sf1;
                    matches[5] = sf2;
                }
                if (currentMatchIndex == 5) {
                    Team sf1Winner1 = matches[4].getRedScore() > matches[4].getBlueScore() ? matches[4].getRed1() : matches[4].getBlue1();
                    Team sf1Winner2 = matches[4].getRedScore() > matches[4].getBlueScore() ? matches[4].getRed2() : matches[4].getBlue2();
                    Team sf2Winner1 = matches[5].getRedScore() > matches[5].getBlueScore() ? matches[5].getRed1() : matches[5].getBlue1();
                    Team sf2Winner2 = matches[5].getRedScore() > matches[5].getBlueScore() ? matches[5].getRed2() : matches[5].getBlue2();

                    Match finalMatch = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 7, Match.MatchType.FINAL, 1, 1);
                    matches[6] = finalMatch;
                }
                break;
            case FOUR_ALLIANCE_DOUBLE:
                if (previousMatch.getType() == Match.MatchType.SEMIFINAL && previousMatch.isTieBreaker()) {
                    Team winner1 = previousMatch.getRedScore() > previousMatch.getBlueScore() ? previousMatch.getRed1() : previousMatch.getBlue1();
                    Team winner2 = previousMatch.getRedScore() > previousMatch.getBlueScore() ? previousMatch.getRed2() : previousMatch.getBlue2();
                    switch (previousMatch.getNumberInSeries()) {
                        case 1:
                            sf1Winner1 = winner1;
                            sf1Winner2 = winner2;
                            break;
                        case 2:
                            sf2Winner1 = winner1;
                            sf2Winner2 = winner2;
                            break;
                    }
                }

                if (currentMatchIndex == 3) {
                    Team sf1Winner1_1 = matches[0].getRedScore() > matches[0].getBlueScore() ? matches[0].getRed1() : matches[0].getBlue1();
                    Team sf1Winner2_1 = matches[0].getRedScore() > matches[0].getBlueScore() ? matches[0].getRed2() : matches[0].getBlue2();
                    Team sf2Winner1_1 = matches[1].getRedScore() > matches[1].getBlueScore() ? matches[1].getRed1() : matches[1].getBlue1();
                    Team sf2Winner2_1 = matches[1].getRedScore() > matches[1].getBlueScore() ? matches[1].getRed2() : matches[1].getBlue2();

                    Team sf1Winner1_2 = matches[2].getRedScore() > matches[2].getBlueScore() ? matches[2].getRed1() : matches[2].getBlue1();
                    Team sf1Winner2_2 = matches[2].getRedScore() > matches[2].getBlueScore() ? matches[2].getRed2() : matches[2].getBlue2();
                    Team sf2Winner1_2 = matches[3].getRedScore() > matches[3].getBlueScore() ? matches[3].getRed1() : matches[3].getBlue1();
                    Team sf2Winner2_2 = matches[3].getRedScore() > matches[3].getBlueScore() ? matches[3].getRed2() : matches[3].getBlue2();

                    if (sf1Winner1_1 != sf1Winner1_2) {
                        matches[4] = new Match(matches[0].getRed1(), matches[0].getRed2(), matches[0].getBlue1(), matches[0].getBlue2(), 5, Match.MatchType.SEMIFINAL, 1, -1);
                        matches[4].setIsTieBreaker(true);
                        sf1TiebreakerExists = true;
                    } else {
                        sf1Winner1 = sf1Winner1_1;
                        sf1Winner2 = sf1Winner2_1;
                        sf1TiebreakerExists = false;
                    }
                    if (sf2Winner1_1 != sf2Winner1_2) {
                        matches[5] = new Match(matches[1].getRed1(), matches[1].getRed2(), matches[1].getBlue1(), matches[1].getBlue2(), 6, Match.MatchType.SEMIFINAL, 2, -1);
                        matches[5].setIsTieBreaker(true);
                        sf2TiebreakerExists = true;
                    } else {
                        sf2Winner1 = sf2Winner1_1;
                        sf2Winner2 = sf2Winner2_1;
                        sf2TiebreakerExists = false;
                    }
                }

                if (sf1Winner1 != null && sf2Winner1 != null && previousMatch.getType() == Match.MatchType.SEMIFINAL) {
                    matches[6] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 7, Match.MatchType.FINAL, 1, 2);
                    matches[7] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 8, Match.MatchType.FINAL, 2, 2);
                    matches[8] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 9, Match.MatchType.FINAL, 1, 1);
                    matches[8].setIsTieBreaker(true);
                }
                break;
            case FOUR_ALLIANCE_SINGLE:
                if (currentMatchIndex == 0) {
                    if (previousMatch.getRedScore() > previousMatch.getBlueScore()) {
                        sf1Winner1 = previousMatch.getRed1();
                        sf1Winner2 = previousMatch.getRed2();
                    } else {
                        sf1Winner1 = previousMatch.getBlue1();
                        sf1Winner2 = previousMatch.getBlue2();
                    }
                } else if (currentMatchIndex == 1) {
                    if (previousMatch.getRedScore() > previousMatch.getBlueScore()) {
                        sf2Winner1 = previousMatch.getRed1();
                        sf2Winner2 = previousMatch.getRed2();
                    } else {
                        sf2Winner1 = previousMatch.getBlue1();
                        sf2Winner2 = previousMatch.getBlue2();
                    }
                    matches[2] = new Match(sf1Winner1, sf1Winner2, sf2Winner1, sf2Winner2, 3, Match.MatchType.FINAL, 1, 1);
                }
                break;
            case TWO_ALLIANCE_DOUBLE:
                if (currentMatchIndex == 1) {
                    boolean didRedWinMatch1 = matches[0].getRedScore() > matches[0].getBlueScore();
                    boolean didRedWinMatch2 = matches[1].getRedScore() > matches[1].getBlueScore();
                    if (didRedWinMatch1 != didRedWinMatch2) {
                        Match tiebreaker = new Match(matches[0].getRed1(), matches[0].getRed2(), matches[0].getBlue1(), matches[0].getBlue2(), 3, Match.MatchType.FINAL, 3, -1);
                        tiebreaker.setIsTieBreaker(true);
                        matches[2] = tiebreaker;
                    }
                }
                break;
        }
        return matches;
    }

    public int getNextMatchAfter(PlayoffsControllerController.PlayoffType type, int previousMatch) {
        switch (type) {
            case EIGHT_ALLIANCE_DOUBLE:
                if (previousMatch >= 7 && previousMatch <= 11) {
                    if (previousMatch <= 7 && (qf1Winner1 == null || qf1TiebreakerExists)) {
                        return 8; // qf1 tiebreaker
                    }
                    if (previousMatch <= 8 && (qf2Winner1 == null || qf2TiebreakerExists)) {
                        return 9; // qf2 tiebreaker
                    }
                    if (previousMatch <= 9 && (qf3Winner1 == null || qf3TiebreakerExists)) {
                        return 10; // qf3 tiebreaker
                    }
                    if (previousMatch <= 10 && (qf4Winner1 == null || qf4TiebreakerExists)) {
                        return 11; //qf4 tiebreaker
                    }
                    return 12; // sf1
                }
                if (previousMatch >= 15 && previousMatch <= 17) {
                    if (previousMatch == 15 && (sf1Winner1 == null || sf1TiebreakerExists)) {
                        return 16; //sf1 tiebreaker
                    }
                    if (previousMatch == 16 && (sf2Winner1 == null || sf2TiebreakerExists)) {
                        return 17; // sf2 tiebreaker
                    }
                    return 18; // final 1
                }
                if (previousMatch == 20) {
                    return 20; // last match
                }
                return previousMatch + 1;
            case EIGHT_ALLIANCE_SINGLE:
                if (previousMatch < 6) {
                    return previousMatch + 1;
                }
                return previousMatch;
            case FOUR_ALLIANCE_DOUBLE:
                if (previousMatch >= 3 && previousMatch <= 5) {
                    if (previousMatch == 3 && (sf1Winner1 == null || sf1TiebreakerExists)) {
                        return 4;
                    }
                    if (previousMatch == 4 && (sf2Winner1 == null || sf2TiebreakerExists)) {
                        return 5;
                    }
                    return 6;
                }
                if (previousMatch == 8) {
                    return 8;
                }
                return previousMatch + 1;
            case FOUR_ALLIANCE_SINGLE:
                if (previousMatch < 2) {
                    return previousMatch + 1;
                }
                return previousMatch;
            case TWO_ALLIANCE_DOUBLE:
                if (previousMatch < 2) {
                    return previousMatch + 1;
                }
                return previousMatch;
            case TWO_ALLIANCE_SINGLE:
                return previousMatch;
        }
        return previousMatch;
    }
}
