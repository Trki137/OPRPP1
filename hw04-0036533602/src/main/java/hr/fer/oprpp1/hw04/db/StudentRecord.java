package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class represents student that we get from table in database
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class StudentRecord {
    /**
     * JMBAG of student
     */
    private final String jmbag;
    /**
     * First name of student
     */
    private final String firstName;
    /**
     * Last name o'f student
     */
    private final String lastName;
    /**
     * Student grade
     */
    private final int grade;

    /**
     * Constructor for StudentRecord
     *
     * @param jmbag jmbag of student
     * @param firstName student first name
     * @param lastName student last name
     * @param grade student grade
     */

    public StudentRecord(String jmbag, String firstName, String lastName, int grade){
        this.jmbag = jmbag;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
    }

    /**
     * Returns student grade
     *
     * @return int student grade
     */

    public int getGrade() {
        return grade;
    }

    /**
     * Returns student first name
     *
     * @return String student first name
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns student jmbag
     * @return String student jmbag
     */

    public String getJmbag() {
        return jmbag;
    }

    /**
     * Returns student last name
     *
     * @return String student last name
     */

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return jmbag +", " + firstName +" "+lastName+" , "+grade;
    }

    /**
     * Checks if two StudentRecord objects are equal
     *
     * @param o object that we compare with current
     * @return boolean true if they are equal, else false
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(jmbag, that.jmbag);
    }

    /**
     * Returns hash of object
     *
     * @return int hash code of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
