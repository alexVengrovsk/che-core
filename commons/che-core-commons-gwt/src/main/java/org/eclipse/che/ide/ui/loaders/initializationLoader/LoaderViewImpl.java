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
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.ide.ui.loaders.LoaderResources;
import org.eclipse.che.ide.util.loging.Log;

import java.util.List;

/**
 * Implementation of {@link LoaderView}.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class LoaderViewImpl implements LoaderView {

    private static final String PRE_STYLE = "style='margin:0px;'";

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

    private FlowPanel                       rootElement;
    private ListDataProvider<OperationInfo> dataProvider;
    private List<OperationInfo>             operationData;
    private LoaderResources                 resources;
    private ActionDelegate                  delegate;

    @Inject
    public LoaderViewImpl(LoaderViewImplUiBinder uiBinder,
                          LoaderResources resources) {
        Log.error(getClass(), "loader view constructor");
        this.resources = resources;
        resources.Css().ensureInjected();
        rootElement = uiBinder.createAndBindUi(this);


        status.setText("LOADING:");
        iconPanel.getElement().appendChild((resources.loaderIcon().getSvg().getElement()));
        DivElement expander = Document.get().createDivElement();
        expander.appendChild(resources.expansionIcon().getSvg().getElement());
        expandHolder.getElement().appendChild(expander);

        expandHolder.sinkEvents(Event.ONCLICK);
        expandHolder.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onExpanderClicked();
            }
        }, ClickEvent.getType());
        operationPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onExpanderClicked();
            }
        }, ClickEvent.getType());
        operations.setVisible(false);
        operationPanel.addDomHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                operationPanel.setVisible(false);
            }
        }, BlurEvent.getType());

    }


    /** Return sanitized message (with all restricted HTML-tags escaped) in {@link SafeHtml}. */
    private SafeHtml buildSafeHtmlMessage(String message) {
        return new SafeHtmlBuilder()
                .appendHtmlConstant("<pre " + PRE_STYLE + ">")
                .append(SimpleHtmlSanitizer.sanitizeHtml(message))
                .appendHtmlConstant("</pre>")
                .toSafeHtml();
    }

    @Override
    public void addOperation(String operation) {
        Log.error(getClass(), "loader view set pocesses " + operation);
        this.operations.add(new HTML(operation));
    }

    @Override
    public void setCurrentOperation(String operation) {
        currentOperation.clear();
        currentOperation.add(new HTML(operation));
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void hide() {

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
