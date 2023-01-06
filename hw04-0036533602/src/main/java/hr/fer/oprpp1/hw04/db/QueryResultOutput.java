package hr.fer.oprpp1.hw04.db;

import java.util.List;


/**
 * Class that writes output for query
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class QueryResultOutput {

    /**
     * Writes appropriate output for every query
     *
     * @param students all student that need to be printed out
     */
    public static void writeOutput(List<StudentRecord> students){
        if(students.size() == 0){
            System.out.println("Records selected: 0");
            return;
        }

        int lastNameSpace = getColumnSpaceLastName(students);
        int firstNameSpace = getColumnSpaceFirstName(students);
        int[] dimensions = new int[]{12,lastNameSpace,firstNameSpace,3};

        printTopDown(dimensions);

        for(StudentRecord studentRecord : students)
            printStudent(studentRecord,lastNameSpace - 2,firstNameSpace - 2);

        printTopDown(dimensions);
        System.out.println("Records selected: "+students.size());
    }

    /**
     * Helper method for printing student
     *
     * @param studentRecord record to print
     * @param lastNameWidth determines size of attribute lastName
     * @param firstNameWidth determines size of attribute firstName
     */
    private static void printStudent(StudentRecord studentRecord, int lastNameWidth, int firstNameWidth) {
        StringBuilder lastName = new StringBuilder(studentRecord.getLastName());
        StringBuilder firstName = new StringBuilder(studentRecord.getFirstName());

        while(lastName.length() < lastNameWidth) lastName.append(" ");
        while(firstName.length() < firstNameWidth) firstName.append(" ");


        String output = String.format("| %s | %s | %s | %d |", studentRecord.getJmbag(),lastName,firstName,studentRecord.getGrade());
        System.out.println(output);
    }

    /**
     * Prints header and footer of table
     * @param dimensions dimensions of every column
     */
    private static void printTopDown(int[] dimensions) {
        for(int i = 0; i < dimensions.length; i++){
            System.out.print("+");
            for (int j = 0; j < dimensions[i]; j++)
                System.out.print("=");
        }

        System.out.println("+");

    }

    /**
     * Calculates max width of column firtsName.
     * Finds the largest first name and then adds +2, so we have space for first name in our column
     *
     * @param students list of all students that have passed query
     * @return int largest first name + 2
     */
    private static int getColumnSpaceFirstName(List<StudentRecord> students) {
        int max = 0;

        for(var student : students)
            if(max == 0 || student.getFirstName().length() > max)
                max = student.getFirstName().length();

        return max + 2;
    }

    /**
     * Calculates max width of column lastName.
     * Finds the largest last name and then adds +2, so we have space for last name in our column
     *
     * @param students list of all students that have passed query
     * @return int largest last name + 2
     */
    private static int getColumnSpaceLastName(List<StudentRecord> students) {
        int max = 0;

        for(var student : students)
            if(max == 0 || student.getLastName().length() > max)
                max = student.getLastName().length();

        return max + 2;
    }
}
