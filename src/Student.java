public class Student {

    private String name;
    private Integer course;
    private Integer age;
    private Gender gender;

    Student ( String name, Integer age, Integer course, Gender gender ) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.gender = gender;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public Integer getCourse () {
        return course;
    }

    public void setCourse ( Integer course ) {
        this.course = course;
    }

    public Integer getAge () {
        return age;
    }

    public void setAge ( Integer age ) {
        this.age = age;
    }

    public Gender getGender () {
        return gender;
    }

    public void setGender ( Gender gender ) {
        this.gender = gender;
    }

}
