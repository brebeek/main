package com.typee.logic.commands;

import static com.typee.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.typee.commons.core.index.Index;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.engagement.Engagement;
import com.typee.model.person.DescriptionContainsKeywordsPredicate;
import com.typee.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DESCRIPTION_LUNCH_APPOINTMENT = "Lunch";
    public static final String VALID_DESCRIPTION_GOOGLE_INTERVIEW = "Googs";
    public static final String VALID_DESCRIPTION_TEAM_MEETING = "Team Project Meeting";


    public static final String VALID_DESCRIPTION_MEETING = "Meeting Bee";
    public static final String VALID_DESCRIPTION_INTERVIEW = "Interview Choo";
    public static final String VALID_TIME_MEETING = "18/10/2019/1800";
    public static final String VALID_TIME_INTERVIEW = "20/10/2019/0800";
    public static final String VALID_ADDRESS_MEETING = "Block 312, Meeting Street 1";
    public static final String VALID_ADDRESS_INTERVIEW = "Block 123, Interviewby Street 3";
    public static final String VALID_PRIORITY_MEETING = "LOW";
    public static final String VALID_PRIORITY_INTERVIEW= "HIGH";

    public static final String DESCRIPT_DESC_MEET = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TEAM_MEETING;
    public static final String DESCRIPT_DESC_INTERVIEW = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_GOOGLE_INTERVIEW;

    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "James&";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEngagementDescriptor DESC_MEET;
    public static final EditCommand.EditEngagementDescriptor DESC_INTERVIEW;

    static {
        DESC_MEET = new EditPersonDescriptorBuilder().withName(VALID_DESCRIPTION_TEAM_MEETING).build();
        DESC_INTERVIEW = new EditPersonDescriptorBuilder().withName(VALID_DESCRIPTION_GOOGLE_INTERVIEW).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        EngagementList expectedEngagementList = new EngagementList(actualModel.getEngagementList());
        List<Engagement> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEngagementList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedEngagementList, actualModel.getEngagementList());
        assertEquals(expectedFilteredList, actualModel.getFilteredEngagementList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the engagement at the given {@code targetIndex} in the
     * {@code model}'s engagement list.
     */
    public static void showEngagementAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEngagementList().size());

        Engagement engagement = model.getFilteredEngagementList().get(targetIndex.getZeroBased());
        final String[] splitDescription = engagement.getDescription().split("\\s+");
        model.updateFilteredEngagementList(new DescriptionContainsKeywordsPredicate((
                Arrays.asList(splitDescription[0]))
        ));

        assertEquals(1, model.getFilteredEngagementList().size());
    }

}
