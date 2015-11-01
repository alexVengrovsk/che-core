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

import org.eclipse.che.ide.util.input.SignalKeyLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Information about the operation.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class InitialLoadingInfo implements LoadingInfo {

    private List<OperationInfo> operations = new ArrayList<>();

    @Inject
    public InitialLoadingInfo(LoaderPresenter loader) {
        for (Operations operation : Arrays.asList(Operations.values())) {
            operations.add(new OperationInfo(operation.getValue(), OperationInfo.Status.WAITING, loader));
        }
    }

    public OperationInfo getOperation(Operations operation) {
        for (OperationInfo operationInfo : operations) {
            if (operationInfo.getOperation().equals(operation.getValue())) {
                return operationInfo;
            }
        }
        return null;
    }

    public void setOperationStatus(Operations operation) {

    }

    @Override
    public List<OperationInfo> getOperations() {
        return operations;
    }

    public enum Operations {
        WORKSPACE_BOOTING("Initializing workspace"),
        MACHINE_BOOTING("Developer Machine booting"),
        EXTENSIONS_BOOTING("Initializing extensions");

        private final String value;

        private Operations(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
