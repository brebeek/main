package com.typee.model;

import java.util.LinkedList;
import java.util.List;

import com.typee.logic.commands.Command;
import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;

/**
 * {@code EngagementList} with a list of its previous states.
 */
public class HistoryManager extends EngagementList {

    private final List<ReadOnlyEngagementList> engagementsHistoryList;
    private final LinkedList<Command> commandHistoryList;
    private int versionPointer;

    public HistoryManager(ReadOnlyEngagementList initialList) {
        super(initialList);
        versionPointer = 0;
        engagementsHistoryList = new LinkedList<>();
        engagementsHistoryList.add(new EngagementList(initialList));
        commandHistoryList = new LinkedList<>();
    }

    /**
     * Reverts the list to its previous state.
     */
    public void undo() throws NullUndoableActionException {
        if (!isUndoable()) {
            throw new NullUndoableActionException();
        }
        versionPointer--;
        resetData(engagementsHistoryList.get(versionPointer));
    }

    /**
     * Reverts the list to its previously undone state.
     */
    public void redo() throws NullRedoableActionException {
        if (!isRedoable()) {
            throw new NullRedoableActionException();
        }

        versionPointer++;
        resetData(engagementsHistoryList.get(versionPointer));
    }

    public boolean isUndoable() {
        return versionPointer > 0;
    }

    public boolean isRedoable() {
        return versionPointer < engagementsHistoryList.size() - 1;
    }

    private void clearUpToNow() {
        engagementsHistoryList.subList(versionPointer + 1, engagementsHistoryList.size()).clear();
        commandHistoryList.subList(versionPointer + 1, commandHistoryList.size()).clear();
    }

    /**
     * Pushes a command to the local {@code commandHistoryList}.
     * @param command command to be pushed.
     */
    public void pushCommandHistory(Command command) {
        commandHistoryList.push(command);
    }

    public Command getLatestCommand() {
        return commandHistoryList.pop();
    }

    /**
     * Saves the current state of appointmentList and discards previous undone changes.
     */
    public void saveState() {
        clearUpToNow();
        engagementsHistoryList.add(new EngagementList(this));
        versionPointer++;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof HistoryManager)) {
            return false;
        }

        HistoryManager otherStatedAppointmentList = (HistoryManager) other;
        return super.equals(otherStatedAppointmentList)
                && versionPointer == otherStatedAppointmentList.versionPointer;
    }
}
