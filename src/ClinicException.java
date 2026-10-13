/** Своё исключение: врач/пациент не найден, время занято, неверные данные. */
public class ClinicException extends Exception {
    public ClinicException(String message) {
        super(message);
    }
}
