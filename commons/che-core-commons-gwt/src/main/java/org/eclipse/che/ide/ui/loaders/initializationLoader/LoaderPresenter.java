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

/**
 * Loader for displaying information about the operation.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class LoaderPresenter implements OperationInfo.StatusListener, LoaderView.ActionDelegate {

    private final LoaderView view;
    private List<OperationInfo> operations;
    private boolean expandPanelState;

    @Inject
    public LoaderPresenter(LoaderView view) {
        this.view = view;
        view.setDelegate(this);
        expandPanelState = false;
    }

    public Widget getCustomComponent() {
        return view.asWidget();
    }

    public void showProgressLoading(LoadingInfo loadingInfo) {
        operations = loadingInfo.getOperations();
        view.setCurrentOperation(operations.get(0).getOperation());
        for (OperationInfo operation : operations) {
            view.addOperation(operation.getOperation());
        }
    }


    /**
     * Hide loader and clean it.
     */
    public void hide() {

    }

    @Override
    public void onStatusChanged() {
//        view.update();
    }

    @Override
    public void onExpanderClicked() {
        if (expandPanelState) {
//            view.collapseOperations();
        } else {
            view.expandOperations();
        }
        expandPanelState = !expandPanelState;
    }
}
