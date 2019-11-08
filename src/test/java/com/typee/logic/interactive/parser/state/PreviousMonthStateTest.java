package com.typee.logic.interactive.parser.state;

import static com.typee.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.CalendarPreviousMonthCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.CliSyntax;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.calendarstate.CalendarState;
import com.typee.logic.interactive.parser.state.calendarstate.PreviousMonthState;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;

public class PreviousMonthStateTest {

    private State previousMonthState;

    @BeforeEach
    public void setup() {
        try {
            ArgumentMultimap argumentMultimap = new ArgumentMultimap();
            argumentMultimap.put(CliSyntax.PREFIX_CALENDAR, "previousmonth");
            previousMonthState = new CalendarState(new ArgumentMultimap());
            previousMonthState = previousMonthState.transition(argumentMultimap);
        } catch (StateTransitionException e) {
            // StateTransitionException should not be thrown here.
        }
    }

    @Test
    public void buildCommand() {
        try {
            assertEquals(((PreviousMonthState) previousMonthState).buildCommand(), new CalendarPreviousMonthCommand());
        } catch (CommandException e) {
            // CommandException should not be thrown here.
        }
    }

    @Test
    public void transition() {
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        assertThrows(StateTransitionException.class, ()
            -> previousMonthState.transition(argumentMultimap));
    }

    @Test
    public void getStateConstraints() {
        assertEquals(previousMonthState.getStateConstraints(), "Previous month displayed.");
    }

    @Test
    public void isEndState() {
        assertTrue(previousMonthState.isEndState());
    }

    @Test
    public void getPrefix() {
        assertEquals(previousMonthState.getPrefix(), new Prefix(""));
    }

}
