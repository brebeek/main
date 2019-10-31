package com.typee.logic.parser;


import static com.typee.logic.parser.CommandParserTestUtil.assertParseFailure;
import static com.typee.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import com.typee.commons.core.Messages;
import com.typee.logic.commands.FindCommand;
import com.typee.model.person.DescriptionContainsKeywordsPredicate;


public class FindCommandParserTest {


    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new DescriptionContainsKeywordsPredicate((Arrays.asList("meeting", "project"))));
        assertParseSuccess(parser, "meeting project", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n meeting \n \t project  \t", expectedFindCommand);
    }

}
