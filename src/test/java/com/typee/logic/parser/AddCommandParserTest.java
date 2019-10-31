package com.typee.logic.parser;

//import static com.typee.testutil.TypicalPersons.MEETING;
//import static com.typee.testutil.TypicalPersons.INTERVIEW;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    /*
    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(INTERVIEW).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.NAME_DESC_INTERVIEW, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_MEETING
                + CommandTestUtil.NAME_DESC_INTERVIEW, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_INTERVIEW, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_INTERVIEW, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_INTERVIEW, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(INTERVIEW).build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_INTERVIEW,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(MEETING).build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_MEETING, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_INTERVIEW, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.NAME_DESC_INTERVIEW,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

     */
}
