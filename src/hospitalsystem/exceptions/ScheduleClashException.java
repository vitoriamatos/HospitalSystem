package hospitalsystem.exceptions;

import hospitalsystem.model.enums.Schedule;

/**
 * <p>Desired schedule spot is alredy taken.</p>
 */
public class ScheduleClashException extends Exception {

	private static final long serialVersionUID = 4987791563165967380L;

	public ScheduleClashException(Schedule schedule) {
        super("Desired schedule spot is alredy taken. Spot desired: " + schedule);
    }

    public ScheduleClashException(String message) {
        super(message);
    }

}
