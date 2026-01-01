package ch.scaille.mldonkey.gui.search;

import ch.scaille.gui.model.ListModel;
import ch.scaille.javabeans.IBindingController;
import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.model.FileQueryResult;

import javax.swing.*;
import java.util.List;

/**
 * One search result
 */
public record SearchResult(
        FileQuery fileQuery,
        ListModel<FileQueryResult> filtered,
        JComponent swingComponent,
        IBindingController selectedResultsController,
        IBindingController lastSelectedResult,
        List<IBindingController> bindings) {

    /**
     * Becomes the visible search view
     */
    public void attach() {
        lastSelectedResult.transmitChangesBothWays();
        selectedResultsController.transmitChangesBothWays();
    }

    /**
     * Becomes the hidden search view
     */
    public void detach() {
        lastSelectedResult.stopTransmit();
        selectedResultsController.stopTransmit();
    }

    public void close() {
        bindings.forEach(IBindingController::stopTransmit);
    }
}