package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *  The {@code Crypto} class enables performing ciphering or digesting activities.
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class Crypto {

    /**
     * Size of I/O buffer
     */
    private static final int BUFFER_SIZE = 4*1024;

    /**
     * The name of the algorithm for creating a file digest.
     */
    private static final String DIGEST_SHA_ALGORITHM = "SHA-256";
    /**
     * Describes the operation (or set of operations) to be performed for the given file, in order to produce some output.
     */
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    /**
     * Type of algorithm for encryption and decryption
     */
    private static final String ALGORITHM = "AES";

    /**
     * List of all available operations
     */
    private static final List<String> availableOperations;
    /**
     * Scanner object to read user input
     */
    private static final Scanner sc = new Scanner(System.in);

    static {
        availableOperations = new ArrayList<>();
        availableOperations.add("checksha");
        availableOperations.add("encrypt");
        availableOperations.add("decrypt");
    }

    /**
     * Program used to perform ciphering or digesting activities on the given file(s).
     *
     * @param args an array of command-line arguments.
     */
    public static void main(String[] args) {
        if(args.length < 2 || args.length > 3)
            throw new IllegalArgumentException("Expected 2 or 3 arguments, but got "+args.length);

        if(!availableOperations.contains(args[0]))
            throw new IllegalArgumentException(args[0] +" is not supported operation");

        if(args[0].equals("checksha")){

            if(args.length != 2)
                throw new IllegalArgumentException("Expected 2 arguments, but got "+args.length);

            try {
                messageDigest(args[1]);
            }catch (NoSuchAlgorithmException e){
                System.out.println(e.getMessage());
            }

        }else{
            if(args.length != 3)
                throw new IllegalArgumentException("Expected 3 arguments, but got "+ args.length);

            try{
                encryption(args[0],args[1],args[2]);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

    }

    /**
     * Calculates digest from given file and compares it with given digest from user
     *
     * @param file from which we calculate digest
     * @throws NoSuchAlgorithmException if provided algorithm doesn't exist
     */
    private static void messageDigest(String file) throws NoSuchAlgorithmException {
        System.out.println("Please provide expected sha-256 digest for "+file+":");
        System.out.print("> ");

        String providedHash = sc.nextLine();

        MessageDigest sha256 = MessageDigest.getInstance(DIGEST_SHA_ALGORITHM);

        byte[] buffer = new byte[BUFFER_SIZE];

        try(InputStream is = new BufferedInputStream(Files.newInputStream(Path.of(file)))){

            do{
                int result = is.read(buffer);
                if(result == -1) break;

                sha256.update(buffer, 0 , result);

            }while(true);

        }catch (IOException e){
            System.out.println("File couldn't be open");
            System.out.println(e.getMessage());
        }

        String calculatedHash = Util.bytetohex(sha256.digest());

        if(calculatedHash.equals(providedHash))
            System.out.println("Digesting completed. Digest of " + file + " matches expected digest.\n");
        else
            System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest. Digest was:"+calculatedHash);

    }

    /**
     * Encrypts or decrypts the given file {@code inputFileName}, depending on the given arguments, to a new file of the name {@code outputFileName}.
     *
     * @param encrypt is it encryption or decryption
     * @param workFileName name of the file that is to be encrypted or decrypted.
     * @param resultFileName name of the file in which to store the result of the encryption or decryption.
     *
     * @throws NoSuchPaddingException when the requested padding mechanism is not available.
     * @throws NoSuchAlgorithmException when the requested cryptographic algorithm is not available.
     * @throws InvalidAlgorithmParameterException when the requested cryptographic algorithm is not available.
     * @throws InvalidKeyException when an invalid key is given.
     * @throws IllegalBlockSizeException  when the length of data provided to a block cipher does not match the block size of the cipher.
     * @throws BadPaddingException when a particular padding mechanism is expected for the given data, but the data is not padded properly.
     */
    private static void encryption(String encrypt, String workFileName, String resultFileName) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException , IllegalBlockSizeException, BadPaddingException {
        checkFilesType(workFileName,resultFileName);

        System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
        System.out.print("> ");

        String keyText = sc.nextLine().trim();

        if(keyText.length() != 32)
            throw new IllegalBlockSizeException("Expected 32 hex-digits, but got "+ keyText.length());

        System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
        System.out.print("> ");

        String ivText = sc.nextLine().trim();

        if(ivText.length() != 32)
            throw new IllegalBlockSizeException("Expected 32 hex-digits, but got "+ ivText.length());

        SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), ALGORITHM);
        AlgorithmParameterSpec parameterSpec = new IvParameterSpec(Util.hextobyte(ivText));

        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

        boolean encryptBool = encrypt.equals("encrypt");
        cipher.init(encryptBool ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,keySpec,parameterSpec);

        try (
                OutputStream os = new BufferedOutputStream((Files.newOutputStream(Path.of(resultFileName))));
                InputStream is = new BufferedInputStream(Files.newInputStream(Path.of(workFileName)))
        ) {

            byte[] input = new byte[BUFFER_SIZE];

            do {
                int result = is.read(input);
                if (result == -1) {
                    os.write(cipher.doFinal());
                    break;
                }

                os.write(cipher.update(input, 0, result));

                } while (true);

            System.out.printf("%s completed. Generated file %s based on file %s", encryptBool ? "Encryption":"Decryption", resultFileName, workFileName );
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks if given files are pdf or bin
     * @param workFileName source file
     * @param resultFileName destination file
     * @throws IllegalArgumentException if {@code workFileName} or {@code resultFileName} is not pdf or bin
     */
    private static void checkFilesType(String workFileName, String resultFileName) {
        if(!(workFileName.endsWith(".pdf") || workFileName.endsWith(".bin")))
            throw new IllegalArgumentException("Expected bin or pdf input file but got "+ workFileName);

        if(!(resultFileName.endsWith(".pdf") || resultFileName.endsWith(".bin")))
            throw new IllegalArgumentException("Expected bin or pdf output file but got "+ resultFileName);
    }



}
