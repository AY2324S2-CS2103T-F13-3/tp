package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.coursemate.CourseMate;
import seedu.address.model.coursemate.QueryableCourseMate;
import seedu.address.model.coursemate.exceptions.CourseMateNotFoundException;
import seedu.address.model.skill.Skill;

/**
 * Adds a courseMate to the contact list.
 */
public class AddSkillCommand extends Command {

    public static final String COMMAND_WORD = "add-skill";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds skills to a courseMate. "
            + "CourseMates can be specified either by name or by the '#' notation.\n"
            + "Parameters: COURSEMATE "
            + PREFIX_SKILL + " SKILL "
            + "[" + PREFIX_SKILL + " SKILL]...\n"
            + "Example: " + COMMAND_WORD + " #1 "
            + PREFIX_SKILL + " Python "
            + PREFIX_SKILL + " Java";

    public static final String MESSAGE_SUCCESS = "Skill(s) successfully added";
    public static final String MESSAGE_NOT_EDITED = "At least one skill should be provided.";
    public static final String MESSAGE_DUPLICATE_COURSE_MATE = "This courseMate already exists in the contact list. \n"
            + "Consider adding a suffix to disambiguate";

    private final QueryableCourseMate queryableCourseMate;
    private final AddSkillDescriptor addSkillDescriptor;
    /**
     * @param queryableCourseMate courseMate that we want to edit
     * @param addSkillDescriptor list of skills to edit the courseMate with
     */
    public AddSkillCommand(QueryableCourseMate queryableCourseMate, AddSkillDescriptor addSkillDescriptor) {
        requireNonNull(queryableCourseMate);
        requireNonNull(addSkillDescriptor);

        this.queryableCourseMate = queryableCourseMate;
        this.addSkillDescriptor = new AddSkillDescriptor(addSkillDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Set<Skill> newSkills = model.getNewSkills(addSkillDescriptor.skills);

        List<CourseMate> courseMateToEditList;
        try {
            courseMateToEditList = model.findCourseMate(queryableCourseMate);
        } catch (CourseMateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_COURSE_MATE_NAME);
        }

        //If there are more than 1 matching names
        if (courseMateToEditList.size() > 1) {
            return new SimilarNameCommand(queryableCourseMate).execute(model);
        }

        CourseMate courseMateToEdit = courseMateToEditList.get(0);

        CourseMate editedCourseMate = addSkillToCourseMate(courseMateToEdit, addSkillDescriptor);

        if (!courseMateToEdit.isSameCourseMate(editedCourseMate) && model.hasCourseMate(editedCourseMate)) {
            throw new CommandException(MESSAGE_DUPLICATE_COURSE_MATE);
        }

        model.setCourseMate(courseMateToEdit, editedCourseMate);
        model.setRecentlyProcessedCourseMate(editedCourseMate);
        return new CommandResult(Messages.messageNewSkill(newSkills) + MESSAGE_SUCCESS,
                false, false, true);
    }

    /**
     * Creates and returns a {@code CourseMate} with the details of {@code courseMateToEdit}
     * edited with {@code addSkillDescriptor}.
     */
    private static CourseMate addSkillToCourseMate(CourseMate courseMateToEdit,
                                                     AddSkillDescriptor addSkillDescriptor) {
        requireNonNull(courseMateToEdit);

        addSkillDescriptor.mergeSkills(courseMateToEdit.getSkills());
        Set<Skill> updatedSkills = addSkillDescriptor.getSkills().orElse(courseMateToEdit.getSkills());

        return new CourseMate(courseMateToEdit.getName(), courseMateToEdit.getPhone(),
                courseMateToEdit.getEmail(), courseMateToEdit.getTelegramHandle(), updatedSkills);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddSkillCommand)) {
            return false;
        }

        AddSkillCommand otherAddSkillCommand = (AddSkillCommand) other;
        return queryableCourseMate.getIndex().equals(otherAddSkillCommand.queryableCourseMate.getIndex())
                && addSkillDescriptor.equals(otherAddSkillCommand.addSkillDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", queryableCourseMate.getIndex())
                .add("addSkillDescriptor", addSkillDescriptor)
                .toString();
    }

    /**
     * Stores the list of skills to add to the courseMate.
     */
    public static class AddSkillDescriptor {
        private Set<Skill> skills;

        public AddSkillDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code skills} is used internally.
         */
        public AddSkillDescriptor(AddSkillDescriptor toCopy) {
            setSkills(toCopy.skills);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return skills != null && skills.size() != 0;
        }

        /**
         * Adds {@code skills} to this object's {@code skills}.
         * A defensive copy of {@code skills} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
        }

        /**
         * Merges the set of skills in the object with the set of skills in the argument
         */
        public void mergeSkills(Set<Skill> argSkills) {
            this.skills.addAll(argSkills);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddSkillDescriptor)) {
                return false;
            }

            AddSkillDescriptor otherAddSkillDescriptor = (AddSkillDescriptor) other;
            return Objects.equals(skills, otherAddSkillDescriptor.skills);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).add("skills", skills).toString();
        }
    }
}
