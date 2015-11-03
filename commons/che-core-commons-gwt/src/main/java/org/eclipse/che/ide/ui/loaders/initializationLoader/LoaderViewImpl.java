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

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.ui.loaders.LoaderResources;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link LoaderView}.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class LoaderViewImpl implements LoaderView {

    private static final String LOADING = "LOADING:";

    @UiField
    FlowPanel iconPanel;
    @UiField
    FlowPanel expandHolder;
    @UiField
    FlowPanel operations;
    @UiField
    FlowPanel currentOperation;
    @UiField
    FlowPanel operationPanel;
    @UiField
    Label     status;

    DivElement progressBar;
    List<HTML> components;

    private FlowPanel       rootElement;
    private LoaderResources resources;
    private ActionDelegate  delegate;

    @Inject
    public LoaderViewImpl(LoaderViewImplUiBinder uiBinder,
                          LoaderResources resources) {
        this.resources = resources;
        resources.Css().ensureInjected();
        rootElement = uiBinder.createAndBindUi(this);

        progressBar = Document.get().createDivElement();
        operationPanel.getElement().appendChild(progressBar);
        operationPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onExpanderClicked();
            }
        }, ClickEvent.getType());
        operations.setVisible(false);

        DivElement expander = Document.get().createDivElement();
        expander.appendChild(resources.expansionIcon().getSvg().getElement());
        expandHolder.getElement().appendChild(expander);


    }

    @Override
    public void setOperations(List<String> operations) {
        components = new ArrayList<>(operations.size());

        this.operations.clear();
        status.setText(LOADING);
        status.setStyleName(resources.Css().inProgressStatusLabel());
        iconPanel.getElement().appendChild((resources.loaderIcon().getSvg().getElement()));
        progressBar.addClassName(resources.Css().progressBarInProgressStatus());
        setProgressBarState(0);

        for (String operation : operations) {
            HTML operationComponent = new HTML(operation);
            operationComponent.addStyleName(resources.Css().waitStatus());
            this.components.add(operationComponent);
            this.operations.add(operationComponent);
        }
    }

    @Override
    public void setCurrentOperation(String operation) {
        currentOperation.clear();
        currentOperation.add(new HTML(operation));
    }

    @Override
    public void setErrorStatus(int index) {
        iconPanel.clear();
        HTML error = new HTML("!");
        error.addStyleName(resources.Css().iconPanelErrorStatus());
        iconPanel.add(error);

        components.get(index).addStyleName(resources.Css().errorStatus());
        progressBar.setClassName(resources.Css().progressBarErrorStatus());
        status.setStyleName(resources.Css().errorStatusLabel());
        setProgressBarState(100);
    }

    @Override
    public void setInProgressStatus(int index) {
        components.get(index).addStyleName(resources.Css().inProgressStatus());
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void expandOperations() {
        operations.setVisible(true);
        resize();
    }

    @Override
    public void collapseOperations() {
        operations.setVisible(false);
    }

    @Override
    public void setProgressBarState(int percent) {
        progressBar.getStyle().setProperty("width", percent + "%");
    }

    @Override
    public Widget asWidget() {
        return rootElement;
    }

    private void resize() {
        if (!operations.isVisible()) {
            return;
        }

        int top = currentOperation.getElement().getAbsoluteTop();
        int left = currentOperation.getElement().getAbsoluteLeft();
        operations.getElement().getStyle().setPropertyPx("top", top + 27);
        operations.getElement().getStyle().setPropertyPx("left", left);
    }

    interface LoaderViewImplUiBinder extends UiBinder<FlowPanel, LoaderViewImpl> {
    }
}
