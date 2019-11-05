package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;

public class RedoCommandTest {

    private Model model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEngagementList(), new UserPrefs());

    @BeforeEach
    public void setUp() throws NullUndoableActionException, NullRedoableActionException {
        model.deleteEngagement(getTypicalEngagements().get(0));
        model.saveEngagementList();
        model.undoEngagementList();

        expectedModel.deleteEngagement(getTypicalEngagements().get(0));
        expectedModel.saveEngagementList();
        expectedModel.undoEngagementList();
        expectedModel.redoEngagementList();
    }

    @Test
    public void execute_single_redoableState() {
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS_PREFIX, expectedModel);

        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
