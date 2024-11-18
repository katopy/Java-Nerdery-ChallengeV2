/* (C)2024 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mocks.CallCostObject;
import mocks.CallSummary;
import mocks.CardWinner;
import mocks.TotalSummary;

public class ChallengeStream {

    /**
     * One stack containing five numbered cards from 0-9 are given to both players. Calculate which hand has winning number.
     * The winning number is calculated by which hard produces the highest two-digit number.
     *
     * calculateWinningHand([2, 5, 2, 6, 9], [3, 7, 3, 1, 2]) ➞ true
     *  P1 can make the number 96
     *  P2 can make the number 73
     *  P1 win the round since 96 > 73
     *
     * The function must return which player hand is the winner and the two-digit number produced. The solution must contain streams.
     *
     * @param player1  hand, player2 hand
     */
    public CardWinner calculateWinningHand(List<Integer> player1, List<Integer> player2) {
        // YOUR CODE HERE...
        //Optional<Integer> maxNumber = player1.stream().max(Comparator.naturalOrder());

        CardWinner player = new CardWinner();

        List<Integer> sortedNum1 = player1.stream().sorted(Comparator.reverseOrder()).limit(2).toList();
        List<Integer> sortedNum2 = player2.stream().sorted(Comparator.reverseOrder()).limit(2).toList();

        StringBuilder stringB1 = new StringBuilder();
        StringBuilder stringB2 = new StringBuilder();


//        for(int n : sortedNum1){
//            stringB1.append(n);
//        }
//        for(int n : sortedNum2){
//            stringB2.append(n);
//        }
        sortedNum1.forEach(stringB1::append);
        sortedNum2.forEach(stringB2::append);


        int n1 = Integer.parseInt(stringB1.toString());
        int n2 = Integer.parseInt(stringB2.toString());

        if (n1 > n2) {
            player.setWinner("P1");
            player.setWinTotal(n1);
            System.out.println("The name: " + player);
        } else if (n2 > n1) {
            player.setWinner("P2");
            player.setWinTotal(n2);
            System.out.println("The name: " + player.getWinTotal() + player.getWinner());


        } else {
            player.setWinner("TIE");
            player.setWinTotal(n1);
        }

        // System.out.println("The maximum number is: "  + sortedNum1.toArray(new String([0]));

        return player;
    }

    /**
     * Design a solution to calculate what to pay for a set of phone calls. The function must receive an
     * array of objects that will contain the identifier, type and duration attributes. For the type attribute,
     * the only valid values are: National, International and Local
     *
     * The criteria for calculating the cost of each call is as follows:
     *
     * International: first 3 minutes $ 7.56 -> $ 3.03 for each additional minute
     * National: first 3 minutes $ 1.20 -> $ 0.48 per additional minute
     * Local: $ 0.2 per minute.
     *
     * The function must return the total calls, the details of each call (the detail received + the cost of the call)
     * and the total to pay taking into account all calls. The solution must be done only using streams.
     *
     * @param {Call[]} calls - Call's information to be processed
     *
     * @returns {CallsResponse}  - Processed information
     */
    public TotalSummary calculateCost(List<CallCostObject> costObjectList) {
        // YOUR CODE HERE...
        /*
        My notes:

        1. It returns a TotalSummary: which takes a List of Call Summary (CallCostObject, totalCost),
        totalCalls (int), totalCost (double)
        2. It takes a CallCostObject: the identifier (String), the type (String), duration (int)
        3. The type will have a price based on minutes, and extra cost (conditions)
        4. Input: I need to take the CallCostObject and start working with it.
        5. CallSummary for individual call, and TotalSummary for the total of calls (callCostObject, specific info of that call, and the totalCost)
        6. TotalSummary it has the info of the total calls
        7. I need to process each element based on the criteria (type of call). To go through the conditions, I need to check if it's the accurate type

         */

        int totalCalls = 0;
//        double baseTypeCost = 0.0; // -> 3 minutes
//        double additionalCost = 0.0; // -> 3 minutes

        List<CallSummary> callSummaries = costObjectList.stream().filter(call ->
                        call.getType().equals("National") ||
                                call.getType().equals("International") ||
                                call.getType().equals("Local"))
                .map(call -> {
                    double totalCost = 0.0;
                    switch (call.getType()) {
                        case "International" -> {
                            double baseTypeCost = 7.56;
                            double additionalCost = 3.03;
                            totalCost = call.getDuration() <= 3
                                    ? baseTypeCost * (call.getDuration())
                                    : (baseTypeCost * 3) + (call.getDuration() - 3) * additionalCost;
                        }
                        case "National" -> {
                            double baseTypeCost = 1.20;
                            double additionalCost = 0.48;
                            totalCost = call.getDuration() <= 3
                                    ? baseTypeCost * (call.getDuration())
                                    : (baseTypeCost * 3) + (call.getDuration() - 3) * additionalCost;
                        }
                        case "Local" -> {
                            double baseTypeCost = 0.2;
                            totalCost = (call.getDuration() * baseTypeCost);
                        }
                        case null, default -> {
                            System.out.println("Type not identified." + call.getType());
                        }
                    }

                    return new CallSummary(call, totalCost);
                }).toList();

        totalCalls = callSummaries.size();
        double totalCost = callSummaries.stream()
                .mapToDouble(CallSummary::getTotalCost)
                .sum();
        return new TotalSummary(callSummaries, totalCalls, totalCost);
    }
}


//        double detailsCall = costObjectList.stream()
//                .filter(call -> call.getType().equals("International"))
//                .filter(call -> call.getDuration() <= 3)
//                .mapToDouble(CallCostObject::getDuration)
//                .sum() * international;

//        TotalSummary dou = (TotalSummary) costObjectList.stream()
//                        .filter(call -> call.getType().equals("International") && call.getDuration() <= 3)
//                        .map(c -> c.setDuration(totalSummary.getTotalCalls()))


// IntelliJ suggested to me to make it as a switch

// List<CallSummary> callSummaries = costObjectList.stream().filter(call ->
//                call.getType().equals("National") ||
//                        call.getType().equals("International") ||
//                        call.getType().equals("Local"))
//        .map(call -> {
//            double totalCost = 0.0;
//            if ("International".equals(call.getType())) {
//                double baseTypeCost = 7.56;
//                double additionalCost = 3.03;
//                totalCost = call.getDuration() <= 3
//                        ? baseTypeCost
//                        : baseTypeCost + (call.getDuration() - 3) * additionalCost;
//            } else if ("National".equals(call.getType())) {
//                double baseTypeCost = 1.20;
//                double additionalCost = 0.48;
//                totalCost = call.getDuration() <= 3
//                        ? baseTypeCost
//                        : baseTypeCost + (call.getDuration() - 3) * additionalCost;
//            } else if ("Local".equals(call.getType())) {
//                double baseTypeCost = 0.20;
//                totalCost = (call.getDuration() * baseTypeCost);
//            } else {
//                System.out.println("Type not identified." + call.getType());
//                totalCost = 0.0;
//            }
//
//            return new CallSummary(call, totalCost);
//
//        }).toList();
