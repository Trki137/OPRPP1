package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represent our database
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class StudentDatabase {

    /**
     * All students
     */
    private List<StudentRecord> studentRecords;

    /**
     * All student but they can be fetched in O(1)
     */
    private Map<String, StudentRecord> indexStudentRecord;

    /**
     * Constructor that gets all students in {@code databaseContent} and we save them into {@code studentRecords}  and {@code indexStudentRecords}
     * @param databaseContent
     */
    public StudentDatabase(List<String> databaseContent){
        this.studentRecords = new ArrayList<>();
        this.indexStudentRecord = new HashMap<>();

        fillData(databaseContent);
    }

    private void fillData(List<String> databaseContent) {

        for (String line : databaseContent){
            String[] arrOfValues = line.split("\\t");

            StudentRecord newStudentRecord = new StudentRecord(
                    arrOfValues[0],
                    arrOfValues[2],
                    arrOfValues[1],
                    Integer.parseInt(arrOfValues[3])
            );

            if(newStudentRecord.getGrade() > 5 || newStudentRecord.getGrade() < 1)
                throw new StudentRecordException("Grade must between 1 and 5\n Student "+ newStudentRecord+" has grade: "+newStudentRecord.getGrade());

            if(studentRecords.contains(newStudentRecord))
                throw new StudentRecordException("There is already student with jmbag: "+newStudentRecord.getJmbag());

            studentRecords.add(newStudentRecord);
            indexStudentRecord.put(
                    newStudentRecord.getJmbag(),
                    newStudentRecord
            );
        }
    }

    public StudentRecord forJMBAG(String jmbag){
        return indexStudentRecord.get(jmbag);
    }

    public List<StudentRecord> filter(IFilter filter){
        List<StudentRecord> filteredStudentRecords = new ArrayList<>();

        for(StudentRecord record : studentRecords)
            if(filter.accepts(record)) filteredStudentRecords.add(record);

        return filteredStudentRecords;
    }

    public List<StudentRecord> getStudentRecords() {
        return studentRecords;
    }

    public Map<String, StudentRecord> getIndexStudentRecord() {
        return indexStudentRecord;
    }
}
