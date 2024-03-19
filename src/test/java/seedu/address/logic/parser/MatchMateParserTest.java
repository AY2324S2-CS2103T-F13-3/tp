package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COURSE_MATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddSkillCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CreateGroupCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteSkillCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coursemate.ContainsKeywordPredicate;
import seedu.address.model.coursemate.CourseMate;
import seedu.address.testutil.AddSkillDescriptorBuilder;
import seedu.address.testutil.CourseMateBuilder;
import seedu.address.testutil.CourseMateUtil;
import seedu.address.testutil.DeleteSkillDescriptorBuilder;
import seedu.address.testutil.EditCourseMateDescriptorBuilder;

public class MatchMateParserTest {

    private final MatchMateParser parser = new MatchMateParser();

    @Test
    public void parseCommand_add() throws Exception {
        CourseMate courseMate = new CourseMateBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(CourseMateUtil.getAddCommand(courseMate));
        assertEquals(new AddCommand(courseMate), command);
    }

    @Test
    public void parseCommand_addSkill() throws Exception {
        CourseMate courseMate = new CourseMateBuilder().build();
        AddSkillCommand.AddSkillDescriptor descriptor = new AddSkillDescriptorBuilder(courseMate).build();
        AddSkillCommand command = (AddSkillCommand) parser.parseCommand(AddSkillCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COURSE_MATE.getOneBased() + " "
                + CourseMateUtil.getAddSkillDescriptorDetails(descriptor));
        assertEquals(new AddSkillCommand(INDEX_FIRST_COURSE_MATE, descriptor), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_COURSE_MATE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_COURSE_MATE), command);
    }

    @Test
    public void parseCommand_deleteSkill() throws Exception {
        CourseMate courseMate = new CourseMateBuilder().build();
        DeleteSkillCommand.DeleteSkillDescriptor descriptor = new DeleteSkillDescriptorBuilder(courseMate).build();
        DeleteSkillCommand command = (DeleteSkillCommand) parser.parseCommand(DeleteSkillCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COURSE_MATE.getOneBased() + " "
                + CourseMateUtil.getDeleteSkillDescriptorDetails(descriptor));
        assertEquals(new DeleteSkillCommand(INDEX_FIRST_COURSE_MATE, descriptor), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        CourseMate courseMate = new CourseMateBuilder().build();
        EditCommand.EditCourseMateDescriptor descriptor = new EditCourseMateDescriptorBuilder(courseMate).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COURSE_MATE.getOneBased() + " "
                + CourseMateUtil.getEditCourseMateDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_COURSE_MATE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        String keyword = "foo bar baz";
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keyword);
        assertEquals(new FindCommand(new ContainsKeywordPredicate(keyword)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_createGroup() throws Exception {
        assertTrue(parser.parseCommand(CreateGroupCommand.COMMAND_WORD + " CS2103T Group")
                instanceof CreateGroupCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_noSpaceAfterPrefix_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand("add Amy Bee -p11111111 -e amy@example.com"));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
