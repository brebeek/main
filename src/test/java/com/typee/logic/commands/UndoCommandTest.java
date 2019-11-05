package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;

public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEngagementList(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model.deleteEngagement(getTypicalEngagements().get(0));
        model.saveEngagementList();

        expectedModel.deleteEngagement(getTypicalEngagements().get(0));
        expectedModel.saveEngagementList();
    }

    @Test
    public void execute_single_undoableState() {
        try {
            expectedModel.undoEngagementList();
        } catch (NullUndoableActionException e) {
            throw new AssertionError("Wrong null undoable command");
        }

        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS_PREFIX, expectedModel);

        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }
}
