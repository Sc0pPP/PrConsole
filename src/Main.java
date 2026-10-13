import java.util.Scanner;

/** Вариант 13 «Поликлиника». Здесь только точка входа и меню. */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Clinic clinic = new Clinic();

    public static void main(String[] args) {
        fillTestData();
        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = readInt("Выберите пункт: ");
                try {
                    switch (choice) {
                        case 1 -> print(clinic.getPeople());
                        case 2 -> addDoctor();
                        case 3 -> addPatient();
                        case 4 -> print(clinic.findByName(readText("Часть имени: ")));
                        case 5 -> clinic.rename(readInt("Номер: "), readText("Новое ФИО: "));
                        case 6 -> clinic.removePerson(readInt("Номер: "));
                        case 7 -> System.out.println(clinic.book(readInt("Номер врача: "),
                                readInt("Номер пациента: "), readText("Время (дд.мм.гггг чч:мм): ")));
                        case 8 -> System.out.println("Отменено: " + clinic.cancel(readInt("Номер записи: ")));
                        case 9 -> print(clinic.getAppointments());
                        case 10 -> print(clinic.getSchedule(clinic.findDoctor(readInt("Номер врача: "))));
                        case 11 -> print(clinic.getHistory(clinic.findPatient(readInt("Номер пациента: "))));
                        case 0 -> running = false;
                        default -> System.out.println("Нет такого пункта");
                    }
                } catch (ClinicException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        } finally {
            scanner.close();
            System.out.println("Программа завершена.");
        }
    }

    private static void printMenu() {
        System.out.println("\n===== ПОЛИКЛИНИКА =====");
        System.out.println("1. Показать врачей и пациентов");
        System.out.println("2. Добавить врача");
        System.out.println("3. Добавить пациента");
        System.out.println("4. Найти по имени");
        System.out.println("5. Изменить ФИО");
        System.out.println("6. Удалить врача/пациента");
        System.out.println("7. Записать пациента к врачу");
        System.out.println("8. Отменить запись");
        System.out.println("9. Показать все записи");
        System.out.println("10. Расписание врача");
        System.out.println("11. История посещений пациента");
        System.out.println("0. Выход");
    }

    private static void addDoctor() throws ClinicException {
        String name = readText("ФИО врача: ");
        String specialty = readText("Специальность: ");
        System.out.println("Добавлен: " + clinic.addDoctor(name, specialty));
    }

    private static void addPatient() throws ClinicException {
        String name = readText("ФИО пациента: ");
        int year = readInt("Год рождения: ");
        System.out.println("Добавлен: " + clinic.addPatient(name, year));
    }

    /** Печать массива; элементы выводятся через toString() своего класса (полиморфизм). */
    private static void print(Object[] items) {
        if (items.length == 0) {
            System.out.println("Ничего нет.");
        }
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /** Повторяет запрос, пока не введено целое число — буквы не роняют программу. */
    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести целое число!");
            }
        }
    }

    private static void fillTestData() {
        try {
            clinic.addDoctor("Иванов Пётр Сергеевич", "Терапевт");        // 1
            clinic.addDoctor("Смирнова Анна Викторовна", "Кардиолог");     // 2
            clinic.addPatient("Орлова Мария Ивановна", 1990);              // 3
            clinic.addPatient("Белов Алексей Николаевич", 1985);           // 4
            clinic.addPatient("Громова Екатерина Андреевна", 2001);        // 5
            clinic.book(1, 3, "14.10.2026 10:00");
            clinic.book(1, 4, "14.10.2026 10:30");
            clinic.book(2, 3, "15.10.2026 11:00");
        } catch (ClinicException e) {
            System.out.println("Ошибка тестовых данных: " + e.getMessage());
        }
    }
}
