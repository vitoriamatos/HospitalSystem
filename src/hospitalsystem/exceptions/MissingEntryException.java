package hospitalsystem.exceptions;

/**
 * <p>Data searched for is missing from base.</p>
 */
public class MissingEntryException extends Exception {

	private static final long serialVersionUID = -5206213161946574372L;

	public MissingEntryException(Object element) {
        super("The entry searched for is missing from data base. Element: " + element);
    }
}
