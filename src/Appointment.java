public class Appointment {
    private int id;
    private Doctor doctor;
    private Patient patient;
    private String time;
    private boolean cancelled;

    public Appointment(int id, Doctor doctor, Patient patient, String time) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getTime() {
        return time;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) o;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Запись №" + id + ": " + time + ", " + doctor.getName() + " (" + doctor.getSpecialty() + "), "
                + patient.getName() + (cancelled ? " [отменена]" : "");
    }
}
