/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.ui.loaders.initializationLoader;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import static org.eclipse.che.ide.ui.loaders.initializationLoader.OperationInfo.Status.SUCCESS;

/**
 * Loader for displaying information about a process of loading.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class LoaderPresenter implements OperationInfo.StatusListener, LoaderView.ActionDelegate {

    private final LoaderView          view;
    private       List<OperationInfo> operations;
    private       boolean             expandPanelState;

    @Inject
    public LoaderPresenter(LoaderView view) {
        this.view = view;
        this.view.setDelegate(this);

        expandPanelState = false;
    }

    /**
     * @return custom Widget that represents the loader's action in UI.
     */
    public Widget getCustomComponent() {
        return view.asWidget();
    }

    /**
     * Displays information about a process of loading.
     *
     * @param loadingInfo
     *         the object which contains information about operations of loading
     */
    public void show(LoadingInfo loadingInfo) {
        operations = loadingInfo.getOperations();
        List<String> displayNames = loadingInfo.getDisplayNames();

        view.setOperations(displayNames);
        updateProgressBarState();
    }

    @Override
    public void onExpanderClicked() {
        if (expandPanelState) {
            view.collapseOperations();
        } else {
            view.expandOperations();
        }
        expandPanelState = !expandPanelState;
    }

    @Override
    public void onStatusChanged(OperationInfo operation) {
        switch (operation.getStatus()) {
            case IN_PROGRESS:
                view.setInProgressStatus(operations.indexOf(operation));
                view.setCurrentOperation(operation.getOperationName());
                break;
            case SUCCESS:
                updateProgressBarState();
                break;
            case ERROR:
                view.setErrorStatus(operations.indexOf(operation));
                view.setCurrentOperation("Error while " + operation.getOperationName());
                break;
        }
    }

    private void updateProgressBarState() {
        if (operations.size() == 0) {
            return;
        }

        int completedOperations = 0;
        for (OperationInfo operation : operations) {
            completedOperations = operation.getStatus().equals(SUCCESS) ? completedOperations + 1 : completedOperations;
        }

        int completedState = completedOperations * 100 / operations.size();
        view.setProgressBarState(completedState);
    }
}
