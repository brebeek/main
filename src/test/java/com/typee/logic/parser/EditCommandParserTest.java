package com.typee.logic.parser;

import org.junit.jupiter.api.Test;
import com.typee.commons.core.Messages;
import com.typee.logic.commands.CommandTestUtil;
import com.typee.logic.commands.EditCommand;

public class EditCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DESCRIPTION_TEAM_MEETING,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        CommandParserTestUtil.assertParseFailure(parser, "-5" + CommandTestUtil.DESCRIPT_DESC_MEET,
                MESSAGE_INVALID_FORMAT);

        // zero index
        CommandParserTestUtil.assertParseFailure(parser, "0" + CommandTestUtil.DESCRIPT_DESC_MEET,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC,
                .MESSAGE_CONSTRAINTS); // invalid name

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_NAME_DESC
                        + CommandTestUtil.VALID_ADDRESS_MEETING + CommandTestUtil.VALID_PHONE_MEETING,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DESCRIPT_DESC_MEET;

        EditCommand.EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_MEETING)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + CommandTestUtil.DESCRIPT_DESC_MEET;
        EditCommand.EditEngagementDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_MEETING)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }



}
