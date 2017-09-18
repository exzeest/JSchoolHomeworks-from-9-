package HomeWork_lesson_9;
import HomeWork_lesson_9.Source.Gender;
import HomeWork_lesson_9.Source.Student;

import java.io.*;

public class TestSerialization {
    public static void main ( String[] args ) throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream("temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Student student = new Student ("Nick", 23, 5, Gender.MAN);
        oos.writeObject(student);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("temp.out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        Student importedStudent = (Student) oin.readObject();
        System.out.println("version="+importedStudent.version);
        System.out.println (importedStudent.toString ());
    }
}
