package hr.fer.oprpp1.hw05.crypto;

/**
 * Contains methods for the conversion between hexadecimal representation and bytes
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class Util {
    /**
     * Converts the given hexadecimal string {@code keyText} to bytes.
     *
     * @param keyText hexadecimal string that is to be converted.
     * @throws NullPointerException if the given {@code keyText} is {@code null}.
     * @throws IllegalArgumentException if key is not even sized or contains invalid hex values
     * @return byte array containing the byte representation of the given hexadecimal string.
     */

    public static byte[] hextobyte(String keyText){
        if(keyText == null)
            throw new NullPointerException("Key cant be null");

        if(keyText.length()  %  2 != 0)
            throw new IllegalArgumentException("Key must have even sized");

        if(!checkKeyText(keyText))
            throw new IllegalArgumentException("Key is invalid");

        if(keyText.length() == 0) return new byte[0];

        byte[] byteArray = new byte[keyText.length() / 2];

        for(int i = 0;  i < keyText.length(); i+=2){
            byteArray[i / 2] = getHex(keyText.substring(i, i+2));
        }

        return byteArray;
    }

    /**
     * Converts the given bytes {@code bytearray} to a hexadecimal string.
     *
     * @param bytearray byte array containing bytes that are to be converted to a hexadecimal string.
     * @return hexadecimal representation of the given bytes.
     */

    public static String bytetohex(byte[] bytearray){
        if(bytearray.length == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (byte b : bytearray) {
            sb.append(byteToHexCode(b));
        }
        return sb.toString();
    }

    /**
     * Converst byte value to hex value
     * @param b byte value
     * @return hex value converted from {@code b}
     */

    private static String byteToHexCode(byte b) {
        StringBuilder sb = new StringBuilder();
        boolean negative = false;

        if(b < 0) {
            negative = true;
            b = (byte) -b;
        }

        String byteCode = getByteCode(String.valueOf(b),8).toString();

        if(negative)
            byteCode = complement(byteCode);

        byte biggerHalf = calculateHexValue(byteCode.substring(0,4));
        byte smallerHalf = calculateHexValue(byteCode.substring(4));

        sb.append(convertBinaryToHex(biggerHalf));
        sb.append(convertBinaryToHex(smallerHalf));

        return sb.toString();
    }

    /**
     * Converts decimal value to hex value
     * @param value decimal value to be converted
     * @return String hex value of {@code value}
     */
    private static String convertBinaryToHex(byte value) {
        if(value < 10) return String.valueOf(value);

        return switch (String.valueOf(value)){
            case "10" -> "a";
            case "11" -> "b";
            case "12" -> "c";
            case "13" -> "d";
            case "14" -> "e";
            default -> "f";
        };
    }

    /**
     * Checks if {@code keyText} is appropriate hex value
     *
     * @param keyText String value to be checked if it is appropriate hex value
     * @return true if it is appropriate hex value, else false
     */

    private static boolean checkKeyText(String keyText) {
        char[] characthers = keyText.toCharArray();

        for (char characther : characthers) {
            if (Character.isDigit(characther)) continue;

            if (characther >= 65 && characther <= 70) continue;

            if (characther >= 97 && characther <= 102) continue;

            return false;
        }

        return true;
    }


    /**
     * Converts hex value to byte value
     *
     * @param hex hexadecimal value
     * @return byte value converted from {@code hex}
     */

    private static byte getHex(String hex) {
        String biggerHalf = hex.substring(0,1);
        String smallerHalf = hex.substring(1,2);

        if(!Character.isDigit(biggerHalf.charAt(0))) biggerHalf = getHexNumber(biggerHalf);
        if(!Character.isDigit(smallerHalf.charAt(0))) smallerHalf = getHexNumber(smallerHalf);

        StringBuilder biggerHalfBuilder = getByteCode(biggerHalf,4);
        StringBuilder smallerHalfBuilder = getByteCode(smallerHalf,4);

        String code = biggerHalfBuilder.append(smallerHalfBuilder).toString();

        boolean negative = code.startsWith("1");
        if(negative) code = complement(code);

        byte value = calculateHexValue(code);

        return negative ? (byte) ((byte) -1 * value) : value;
    }

    /**
     * Turns hexadecimal value to binary
     *
     * @param hexValue hexadecimal value
     * @param sequence_length how long is binary number
     * @return binary number
     */

    private static StringBuilder getByteCode(String hexValue, int sequence_length) {
        StringBuilder hexBuilder = new StringBuilder();

        for(int i = 0;  i < sequence_length; i++){
            int value = Integer.parseInt(hexValue);

            if(value == 0) hexBuilder.insert(0,"0");

            else {
                hexBuilder.insert(0,Math.abs(value % 2));
                hexValue = String.valueOf(value / 2);
            }
        }

        return hexBuilder;
    }

    /**
     * Calculates decimal value of binary number
     * @param code binary number
     * @return byte decimal value of binary number
     */
    private static byte calculateHexValue(String code) {
        byte value = 0;
        char[] arr = code.toCharArray();
        int power = 0;


        for(int i = arr.length - 1; i >= 0; i--){
            if(arr[i] == '0'){
                power++;
                continue;
            }
            value += Math.pow(2,power++);
        }
        return value;
    }

    /**
     * Converts hex letter to decimal number
     *
     * @param hexValueLetter expects hex value between a and f
     * @return corresponding hex value
     */
    private static String getHexNumber(String hexValueLetter) {

        return switch (hexValueLetter.toLowerCase()){
            case "a" -> "10";
            case "b" -> "11";
            case "c" -> "12";
            case "d" -> "13";
            case "e" -> "14";
            default -> "15";
        };
    }

    /**
     * Perform second complement on {@code code}
     *
     * @param code binary number
     * @return String second complement of {@code code}
     */

    private static String complement(String code) {
        char[] arr = code.toCharArray();
        int index;


        for(index = 0; index < arr.length; index++ )
            arr[index] = arr[index] == '0' ? '1': '0';

        index--;

        while(true){
            if(arr[index] == '0'){
                arr[index] = '1';
                break;
            }

            arr[index--] = '0';
        }

        return String.valueOf(arr);
    }

}
