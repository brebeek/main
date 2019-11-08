package com.typee.logic.parser;

import static java.util.Objects.requireNonNull;

import com.typee.commons.core.Messages;
import com.typee.commons.core.index.Index;
import com.typee.logic.commands.EditCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME);

        Index index;

        try {
            index = InteractiveParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditEngagementDescriptor editPersonDescriptor = new EditCommand.EditEngagementDescriptor();
        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(InteractiveParserUtil
                    .parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
}
