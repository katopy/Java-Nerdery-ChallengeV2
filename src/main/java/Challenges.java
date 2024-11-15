/* (C)2024 */

import java.math.BigInteger;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

/* (C)2024 */
public class Challenges {

    /* *****
    Challenge 1

    "Readable Time"

    The function "readableTime" accepts a positive number as argument,
    you should be able to modify the function to return the time from seconds
    into a human readable format.

    Example:

    Invoking "readableTime(3690)" should return "01:01:30" (HH:MM:SS)
    ***** */

    public String readableTime(Integer seconds) {
        // YOUR CODE HERE...
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainSeconds);
    }

    /* *****
    Challenge 2

    "Circular Array"

    Given the following array "COUNTRY_NAMES", modify the function "circularArray"
    to return an array that meets the following criteria:

    - The index number passed to the function should be the first element in the resulting array
    - The resulting array must have the same length as the initial array
    - The value of the argument "index" will always be a positive number

    Example:

    Invoking "circularArray(2)" should return "["Island", "Japan", "Israel", "Germany", "Norway"]"
    ***** */

    public String[] circularArray(int index) { // test passed
        String[] COUNTRY_NAMES = {"Germany", "Norway", "Island", "Japan", "Israel"};
        // YOUR CODE HERE...
        if (index < 0) {
            System.out.println("Invalid index number");
            return COUNTRY_NAMES;
        }
        int arrayLength = COUNTRY_NAMES.length;
        int greaterIndex = index % arrayLength;

        // New List to store my array organized
        List<String> newArray = new ArrayList<>();

        newArray.addAll(Arrays.asList(COUNTRY_NAMES).subList(greaterIndex, arrayLength));
        newArray.addAll(Arrays.asList(COUNTRY_NAMES).subList(0, greaterIndex));

        return newArray.toArray(new String[0]);
    }

    /* *****
    Challenge 3

    "Own Powers"

    The function "ownPower" accepts two arguments. "number" and "lastDigits".

    The "number" indicates how long is the series of numbers you are going to work with, your
    job is to multiply each of those numbers by their own powers and after that sum all the results.

    "lastDigits" is the length of the number that your function should return, as a string!.
    See example below.

    Example:

    Invoking "ownPower(10, 3)" should return "317"
    because 1^1 + 2^2 + 3^3 + 4^4 + 5^5 + 6^6 + 7^7 + 8^8 + 9^9 + 10^10 = 10405071317
                                                                          2147483647
    The last 3 digits for the sum of powers from 1 to 10 is "317"
    ***** */

    public String ownPower(int number, int lastDigits) {
        // YOUR CODE HERE...
        BigInteger sum = BigInteger.ZERO;

        for (int i = 1; i <= number; i++) {
            BigInteger myValue = BigInteger.valueOf(i);
            BigInteger power = myValue.pow(i);
            sum = sum.add(power);
        }

        // I tried to pass use mod, but mathematically the last test was deleting the 0 at the left, so I chose to cast to string
//        BigInteger baseNum = BigInteger.TEN;
//        BigInteger powValue = baseNum.pow(lastDigits);
        //BigInteger mod = sum.mod(powValue); // last digits

        // Getting last digits
        String myNum = sum.toString();
        int beginIndex = myNum.length() - lastDigits;

        return myNum.substring(beginIndex); //mod.toString();
    }

    /* *****
    Challenge 4

    "Sum of factorial digits"

    A factorial (x!) means x! * (x - 1)... * 3 * 2 * 1.
    For example: 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800

    Modify the function "digitSum" to return a number that
    equals to the sum of the digits in the result of 10!

    Example:

    Invoking "digitSum(10)" should return "27".
    Since 10! === 3628800 and you sum 3 + 6 + 2 + 8 + 8 + 0 + 0
    ***** */

    public Integer digitSum(int n) {
        // YOUR CODE HERE...
        BigInteger resultFact = BigInteger.ONE;
        if(n <= 1){
            return 1;
        }
        for(int i = 1; i <= n; i++){
            BigInteger iBig = BigInteger.valueOf(i);
            resultFact = resultFact.multiply(iBig);
        }

        //sum = n * digitSum(n - 1); // x! * (x - 1)

        int sum = 0;
        String myNumber = resultFact.toString();
        List<Integer> listToSum = myNumber.chars()
                .map(Character::getNumericValue)
                .boxed().toList();

        for(Integer l : listToSum){
            sum += l;
        }
        return sum;
    }

    /**
     * Decryption.
     * Create a decryption function that takes as parameter an array of ASCII values. The addition between values is the ascii value decrypted.
     * decrypt([ 72, 33, -73, 84, -12, -3, 13, -13, -68 ]) ➞ "Hi there!"
     * H = 72, the sum of H 72 and 33 gives 105 which ascii value is i;
     * The function must return the string encoded using the encryption function below.
     *
     * @param ascivalues  hand, player2 hand
     */
    public String decrypt(List<Integer> ascivalues) { // test passed
        // YOUR CODE HERE...
        StringBuilder finalString = new StringBuilder();
        List<Integer> thisIndex = new ArrayList<>(Collections.singletonList(ascivalues.getFirst()));

        for(int i = 1; i < ascivalues.size(); i++) {
            int calculate = ascivalues.get(i) + thisIndex.get(i - 1);
            thisIndex.add(calculate);
        }

        for(int c : thisIndex){
            char myChar = (char) c;
            finalString.append(myChar);
        }

        return finalString.toString();
    }

    /**
     * Encryption Function.
     * Create am encryption function that takes a string and converts into an array of ASCII character values.
     * encrypt("Hello") ➞ [72, 29, 7, 0, 3]
     * // H = 72, the difference between the H and e is 29
     * The function must return an array of integer ascii values.
     *
     * @param text  hand, player2 hand
     */
    public List<Integer> encrypt(String text) {
        // YOUR CODE HERE...
        List<Integer> myListChars = new ArrayList<>();
        for(int c : text.toCharArray()){
            myListChars.add(c);
        }
        List<Integer> thisIndex = new ArrayList<>(Collections.singletonList(myListChars.getFirst()));

        for(int i = 1; i < myListChars.size(); i++) {
            int calculate = myListChars.get(i) - myListChars.get(i - 1);
            thisIndex.add(calculate);
        }

        return thisIndex;
    }
}
