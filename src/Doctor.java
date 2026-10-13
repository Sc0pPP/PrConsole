
public class Doctor extends Person {
    private String specialty;

    public Doctor(int id, String name, String specialty) {
        super(id, name);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String getRole() {
        return "Врач";
    }

    @Override
    public String toString() {
        return super.toString() + " (" + specialty + ")";
    }
}
