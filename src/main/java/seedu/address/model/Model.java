package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.coursemate.CourseMate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<CourseMate> PREDICATE_SHOW_ALL_COURSE_MATES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a courseMate with the same identity as {@code courseMate} exists in the address book.
     */
    boolean hasCourseMate(CourseMate courseMate);

    /**
     * Deletes the given courseMate.
     * The courseMate must exist in the address book.
     */
    void deleteCourseMate(CourseMate target);

    /**
     * Adds the given courseMate.
     * {@code courseMate} must not already exist in the address book.
     */
    void addCourseMate(CourseMate courseMate);

    /**
     * Replaces the given courseMate {@code target} with {@code editedCourseMate}.
     * {@code target} must exist in the address book.
     * The courseMate identity of {@code editedCourseMate} must not be the same as another existing courseMate in the address book.
     */
    void setCourseMate(CourseMate target, CourseMate editedCourseMate);

    /** Returns an unmodifiable view of the filtered courseMate list */
    ObservableList<CourseMate> getFilteredCourseMateList();

    /**
     * Updates the filter of the filtered courseMate list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCourseMateList(Predicate<CourseMate> predicate);
}
