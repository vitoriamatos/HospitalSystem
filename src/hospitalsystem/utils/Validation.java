package hospitalsystem.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {

    private static final String VALID_EMAIL_REGEX = "[a-z0-9&'_+`-]+(?:.[a-z0-9&'+_`-]+)*@(?:[a-z0-9](?:[a-z0-9]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9]*[a-z0-9])?";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    // ======== CPF ========

    private static String getRawCpf(String cpf) {
        return cpf.trim().replace(".", "").replace("-", "");
    }


    // Helper methods

    public static boolean isBirthDateValid(String birthDate) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            dateFormat.parse(birthDate);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isCpfValid(String cpf) {
        cpf = getRawCpf(cpf);
        return isDigitValid(cpf, "first") && isDigitValid(cpf, "second");
    }

    // ======== Name ========

    /**
     * <p>Verification digit validation.</p>
     *
     * @param cpf   the cpf to validate
     * @param digit which digit to validate
     * @return True if digit is valid, false if it is not
     */
    private static boolean isDigitValid(String cpf, String digit) {
        // Assert which digit is going to be verified
        int digitsQty;
        int numToDecreaseFrom;
        int verificationDigitPosition;
        if (digit.equalsIgnoreCase("first")) {
            digitsQty = 9;
            numToDecreaseFrom = 10;
            verificationDigitPosition = 9;
        } else {
            digitsQty = 10;
            numToDecreaseFrom = 11;
            verificationDigitPosition = 10;
        }

        // Verify digit
        int sum = 0;
        for (int i = 0; i < digitsQty; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (numToDecreaseFrom - i);
        }
        int remainder = (sum * 10) % 11;
        if (remainder > 9) {
            remainder = 0;
        }

        return remainder == Character.getNumericValue(cpf.charAt(verificationDigitPosition));
    }

    // ======== Birth Date ========

    public static boolean isEmailValid(String email) {
        return email.matches(VALID_EMAIL_REGEX);
    }

    // ======== Email ========

    public static boolean isNameValid(String name) {
        char character;
        for (int i = 0; i < name.length(); i++) {
            character = name.charAt(i);
            if (!(Character.isLetter(character) || Character.isWhitespace(character))) {
                return false;
            }
        }
        return true;
    }

}
