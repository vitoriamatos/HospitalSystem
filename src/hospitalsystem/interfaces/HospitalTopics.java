package hospitalsystem.interfaces;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;

import java.util.List;

/**
 * <p>
 * Specifies the basic tasks each module has to have to manage the Academic
 * System.
 * </p>
 *
 * @param <T> the entity managed by the module
 */
public interface HospitalTopics<T> {

    /**
     * <p>
     * Return an object if it exists based on its identification key.
     * </p>
     *
     * @param id the target object identification key
     * @return the desired object or null if it doesn't exists
     */
    T find(String id);

    /**
     * <p>
     * Return a list with all the base entries.
     * </p>
     *
     * @return a list with all the data registered
     */
    List<T> list();

    /**
     * <p>
     * Modify an object's attributes except its ID key if it exists.
     * </p>
     *
     * @param data the target object identification key
     * @throws MissingEntryException if the data given does not exists on the base
     */
    void modify(T data) throws MissingEntryException;

    /**
     * <p>
     * Register the data passed to the base if it isn't alredy so.
     * </p>
     *
     * @param data the entity instance to register
     * @throws DuplicatedEntryException if the data is alredy registered in the base
     */
    void register(T data) throws DuplicatedEntryException;

    /**
     * <p>
     * Remove an object from the base.
     * </p>
     *
     * @param id the target object identification key
     */
    void remove(String id);

}
