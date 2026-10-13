public class Patient extends Person {
    private int birthYear;

    public Patient(int id, String name, int birthYear) {
        super(id, name);
        this.birthYear = birthYear;
    }

    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public String getRole() {
        return "Пациент";
    }

    @Override
    public String toString() {
        return super.toString() + " (" + birthYear + " г.р.)";
    }
}
