package hospitalsystem.exceptions;

/**
 * <p>Element is alredy registered on data base.</p>
 */
public class DuplicatedEntryException extends Exception {

	private static final long serialVersionUID = -721223942189524890L;

	public DuplicatedEntryException(Object element) {
        super("Element alredy registered to data base. Element: " + element);
    }

}
