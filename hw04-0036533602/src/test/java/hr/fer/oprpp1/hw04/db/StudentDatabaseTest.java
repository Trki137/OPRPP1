package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    @Test
    public void testGetByJMBAG(){
        List<String> databaseContent = null;
        try {
            databaseContent = Files.readAllLines(Paths.get("src/database.txt"), StandardCharsets.UTF_8);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        StudentDatabase sd = new StudentDatabase(databaseContent);
        assertEquals(sd.getIndexStudentRecord().get("0000000001"),sd.forJMBAG("0000000001"));
    }
    @Test
    public void getAllRecords(){
        List<String> databaseContent = null;
        try {
            databaseContent = Files.readAllLines(Paths.get("src/database.txt"), StandardCharsets.UTF_8);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }

        StudentDatabase sd = new StudentDatabase(databaseContent);

        List<StudentRecord> filteredRecords = sd.filter(record -> true);

        assertEquals(databaseContent.size(), filteredRecords.size());
    }

    @Test
    public void getNoRecords(){
        List<String> databaseContent = null;
        try {
            databaseContent = Files.readAllLines(Paths.get("src/database.txt"), StandardCharsets.UTF_8);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        StudentDatabase sd = new StudentDatabase(databaseContent);

        List<StudentRecord> filteredRecords = sd.filter(record -> false);

        assertEquals(0,filteredRecords.size());
    }

    @Test
    public void testWrongGradeThrows(){
        List<String> databaseContent = Arrays.asList("0000000001\tAkšamović\tMarin\t6");
        List<String> databaseContent2 = Arrays.asList("0000000001\tAkšamović\tMarin\t-1");

        assertThrows(StudentRecordException.class,() -> new StudentDatabase(databaseContent));
        assertThrows(StudentRecordException.class,() -> new StudentDatabase(databaseContent2));
    }

    @Test
    public void testSameJmbagThrows(){
        List<String> databaseContent = Arrays.asList("0000000001\tAkšamović\tMarin\t2","0000000001\tAkšamović\tMarin\t2");

        assertThrows(StudentRecordException.class,() -> new StudentDatabase(databaseContent));
    }
}
