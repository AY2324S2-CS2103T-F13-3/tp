package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.group.ContainsGroupKeywordPredicate;

/**
 * Finds and lists all groups in the group list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends Command {

    public static final String COMMAND_WORD = "find-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all groups whose names contain a "
            + "the specified keyphrase (case-insensitive) and displays them as a list.\n"
            + "KEYPHRASE can contain any string.\n"
            + "Parameters: KEYPHRASE\n"
            + "Example: " + COMMAND_WORD + " CS2103T G18";

    private final ContainsGroupKeywordPredicate predicate;

    public FindGroupCommand(ContainsGroupKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_GROUPS_LISTED_OVERVIEW, model.getFilteredGroupList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindGroupCommand)) {
            return false;
        }

        FindGroupCommand otherFindGroupCommand = (FindGroupCommand) other;
        return predicate.equals(otherFindGroupCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
