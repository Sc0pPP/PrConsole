import java.util.Arrays;

public class Clinic {
    private Person[] people = new Person[2];
    private int peopleCount = 0;
    private Appointment[] appointments = new Appointment[2];
    private int appointmentCount = 0;
    private int nextId = 1;
    private int nextAppointmentId = 1;

    // ---------- врачи и пациенты ----------

    public Doctor addDoctor(String name, String specialty) throws ClinicException {
        checkText(name, "ФИО");
        checkText(specialty, "Специальность");
        Doctor doctor = new Doctor(nextId++, name, specialty);
        addPerson(doctor);
        return doctor;
    }

    public Patient addPatient(String name, int birthYear) throws ClinicException {
        checkText(name, "ФИО");
        if (birthYear < 1900 || birthYear > 2026) {
            throw new ClinicException("Год рождения должен быть от 1900 до 2026");
        }
        Patient patient = new Patient(nextId++, name, birthYear);
        addPerson(patient);
        return patient;
    }

    private void addPerson(Person person) {
        if (peopleCount == people.length) {
            people = Arrays.copyOf(people, people.length * 2); // расширяем массив вручную
        }
        people[peopleCount++] = person;
    }

    public Person[] getPeople() {
        return Arrays.copyOf(people, peopleCount);
    }

    public Person findById(int id) throws ClinicException {
        for (int i = 0; i < peopleCount; i++) {
            if (people[i].getId() == id) {
                return people[i];
            }
        }
        throw new ClinicException("Человек с номером " + id + " не найден");
    }

    public Doctor findDoctor(int id) throws ClinicException {
        Person person = findById(id);
        if (!(person instanceof Doctor)) {
            throw new ClinicException("Номер " + id + " — не врач");
        }
        return (Doctor) person;
    }

    public Patient findPatient(int id) throws ClinicException {
        Person person = findById(id);
        if (!(person instanceof Patient)) {
            throw new ClinicException("Номер " + id + " — не пациент");
        }
        return (Patient) person;
    }

    /** Поиск по части имени без учёта регистра. */
    public Person[] findByName(String part) {
        Person[] result = new Person[peopleCount];
        int count = 0;
        for (int i = 0; i < peopleCount; i++) {
            if (people[i].getName().toLowerCase().contains(part.toLowerCase())) {
                result[count++] = people[i];
            }
        }
        return Arrays.copyOf(result, count);
    }

    public void rename(int id, String newName) throws ClinicException {
        checkText(newName, "ФИО");
        findById(id).setName(newName);
    }

    public void removePerson(int id) throws ClinicException {
        Person person = findById(id);
        for (int i = 0; i < appointmentCount; i++) {
            Appointment a = appointments[i];
            if (!a.isCancelled() && (a.getDoctor().equals(person) || a.getPatient().equals(person))) {
                throw new ClinicException("Нельзя удалить: есть действующая запись №" + a.getId());
            }
        }
        int index = 0;
        while (people[index] != person) {
            index++;
        }
        for (int i = index; i < peopleCount - 1; i++) {
            people[i] = people[i + 1];
        }
        people[--peopleCount] = null;
    }

    // ---------- записи на приём ----------

    /** Запись к врачу. Защита от двойной записи: врач и пациент не могут быть заняты в одно время. */
    public Appointment book(int doctorId, int patientId, String time) throws ClinicException {
        if (!time.matches("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}")) {
            throw new ClinicException("Время нужно вводить в формате дд.мм.гггг чч:мм");
        }
        Doctor doctor = findDoctor(doctorId);
        Patient patient = findPatient(patientId);
        for (int i = 0; i < appointmentCount; i++) {
            Appointment a = appointments[i];
            if (a.isCancelled() || !a.getTime().equals(time)) {
                continue;
            }
            if (a.getDoctor().equals(doctor)) {
                throw new ClinicException("У врача " + doctor.getName() + " это время уже занято");
            }
            if (a.getPatient().equals(patient)) {
                throw new ClinicException("Пациент " + patient.getName() + " в это время уже записан к другому врачу");
            }
        }
        if (appointmentCount == appointments.length) {
            appointments = Arrays.copyOf(appointments, appointments.length * 2);
        }
        Appointment appointment = new Appointment(nextAppointmentId++, doctor, patient, time);
        appointments[appointmentCount++] = appointment;
        return appointment;
    }

    public Appointment cancel(int id) throws ClinicException {
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].getId() == id) {
                if (appointments[i].isCancelled()) {
                    throw new ClinicException("Запись №" + id + " уже отменена");
                }
                appointments[i].cancel();
                return appointments[i];
            }
        }
        throw new ClinicException("Запись с номером " + id + " не найдена");
    }

    public Appointment[] getAppointments() {
        return Arrays.copyOf(appointments, appointmentCount);
    }

    /** Действующие записи врача (расписание). */
    public Appointment[] getSchedule(Doctor doctor) {
        Appointment[] result = new Appointment[appointmentCount];
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) {
            if (!appointments[i].isCancelled() && appointments[i].getDoctor().equals(doctor)) {
                result[count++] = appointments[i];
            }
        }
        return Arrays.copyOf(result, count);
    }

    /** Все записи пациента, в том числе отменённые (история). */
    public Appointment[] getHistory(Patient patient) {
        Appointment[] result = new Appointment[appointmentCount];
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].getPatient().equals(patient)) {
                result[count++] = appointments[i];
            }
        }
        return Arrays.copyOf(result, count);
    }

    private void checkText(String text, String field) throws ClinicException {
        if (text == null || text.trim().isEmpty()) {
            throw new ClinicException(field + " не может быть пустым");
        }
    }
}
