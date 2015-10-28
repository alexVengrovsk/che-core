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

import com.google.gwt.user.client.ui.IsWidget;


/**
 * View of {@link LoaderPresenter}.
 *
 * @author Roman Nikitenko
 */
public interface LoaderView extends IsWidget {
    interface ActionDelegate {
        /**
         * Performs any actions appropriate in response to the user having clicked the expander area.
         */
        void onExpanderClicked();
    }
    void addOperation(String operation);

    void setCurrentOperation(String operation);

    /** Sets the delegate to receive events from this view. */
    void setDelegate(ActionDelegate delegate);

    /** Hide loader */
    void hide();

    /** Expand Operations area. */
    void expandOperations();

    /** Collapse Operations area. */
    void collapseOperations();
}