package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEMATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Set;

import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coursemate.Name;
import seedu.address.model.coursemate.QueryableCourseMate;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new {@code CreateGroupCommand} object.
 */
public class CreateGroupCommandParser implements Parser<CreateGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CreateGroupCommand
     * and returns a CreateGroupCommand object to execute.
     * @throws ParseException if the user input does not conform the expected format or the group name is used
     */
    public CreateGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_COURSEMATE, PREFIX_SKILL);

        try {
            Name name = ParserUtil.parseName(argMultiMap.getPreamble());
            Set<QueryableCourseMate> queryableCourseMateSet =
                    ParserUtil.parseQueryableCourseMates(argMultiMap.getAllValues(PREFIX_COURSEMATE));
            Set<Skill> skillSet =
                    ParserUtil.parseSkills(argMultiMap.getAllValues(PREFIX_SKILL));
            return new CreateGroupCommand(name, queryableCourseMateSet, skillSet);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateGroupCommand.MESSAGE_USAGE));
        }
    }
}
