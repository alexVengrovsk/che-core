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

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.eclipse.che.ide.ui.loaders.initializationLoader.OperationInfo.Status;

/**
 * Contains information about the operations of initial loading in IDE.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class InitialLoadingInfo implements LoadingInfo {

    private List<OperationInfo> operations   = new ArrayList<>();
    private List<String>        displayNames = new ArrayList<>();

    @Inject
    public InitialLoadingInfo(LoaderPresenter loader) {
        for (Operations operation : Arrays.asList(Operations.values())) {
            OperationInfo operationInfo = new OperationInfo(operation.getValue(), OperationInfo.Status.WAITING, loader);
            operations.add(operationInfo);
            displayNames.add(operation.getValue());
        }
    }

    @Override
    public void setOperationStatus(String operationName, Status status) {
        for (OperationInfo operationInfo : operations) {
            if (operationInfo.getOperationName().equals(operationName)) {
                operationInfo.setStatus(status);
            }
        }
    }

    @Override
    public List<OperationInfo> getOperations() {
        return operations;
    }

    @Override
    public List<String> getDisplayNames() {
        return displayNames;
    }

    /** The set of operations required for the initial loading in IDE. */
    public enum Operations {
        WORKSPACE_BOOTING("Initializing workspace"),
        MACHINE_BOOTING("Developer Machine booting");

        private final String value;

        private Operations(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
