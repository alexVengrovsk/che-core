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
package org.eclipse.che.ide.part.explorer.project;

import com.google.gwt.event.shared.HandlerRegistration;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.api.mvp.View;
import org.eclipse.che.ide.api.parts.base.BaseActionDelegate;
import org.eclipse.che.ide.api.project.node.HasStorablePath;
import org.eclipse.che.ide.api.project.node.Node;
import org.eclipse.che.ide.project.node.ProjectDescriptorNode;
import org.eclipse.che.ide.ui.smartTree.TreeNodeStorage.StoreSortInfo;
import org.eclipse.che.ide.ui.smartTree.event.CollapseNodeEvent;
import org.eclipse.che.ide.ui.smartTree.event.ExpandNodeEvent;
import org.eclipse.che.ide.ui.smartTree.event.GoIntoStateEvent;

import java.util.List;

/**
 * @author Vlad Zhukovskiy
 */
public interface ProjectExplorerView extends View<ProjectExplorerView.ActionDelegate> {

    void setRootNodes(List<Node> nodes);

    void addNode(Node node);

    /**
     * Remove node from the project tree.
     *
     * @param node
     *         node which should be remove
     * @param closeMissingFiles
     *         true if opened nodes in editor part should be closed
     */
    void removeNode(Node node, boolean closeMissingFiles);

    void setRootNode(Node node);

    void replaceParentNode(ProjectDescriptorNode descriptorNode);

    List<StoreSortInfo> getSortInfo();

    void onApplySort();

    void scrollFromSource(HasStorablePath path);

    boolean setGoIntoModeOn(Node node);

    HandlerRegistration addGoIntoStateHandler(GoIntoStateEvent.GoIntoStateHandler handler);

    void resetGoIntoMode();

    boolean isGoIntoActivated();

    boolean isFoldersAlwaysOnTop();

    void setFoldersAlwaysOnTop(boolean foldersAlwaysOnTop);

    void reloadChildren(Node parent);

    void reloadChildren(Node parent, boolean deep);

    void reloadChildrenByType(Class<?> type);

    void expandAll();

    void collapseAll();

    List<Node> getVisibleNodes();

    void showHiddenFiles(boolean show);

    boolean isShowHiddenFiles();

    /**
     * Search node in the project explorer tree by storable path.
     *
     * @param path
     *         path to node
     * @param forceUpdate
     *         force children reload
     * @param closeMissingFiles
     *         allow editor to close removed files if they were opened
     * @return promise object with found node or promise error if node wasn't found
     */
    Promise<Node> getNodeByPath(HasStorablePath path, boolean forceUpdate, boolean closeMissingFiles);

    void select(Node item, boolean keepExisting);

    void select(List<Node> items, boolean keepExisting);

    boolean isExpanded(Node node);

    void setExpanded(Node node, boolean expand);

    HandlerRegistration addExpandHandler(ExpandNodeEvent.ExpandNodeHandler handler);

    HandlerRegistration addCollapseHandler(CollapseNodeEvent.CollapseNodeHandler handler);

    void setVisible(boolean visible);

    public interface ActionDelegate extends BaseActionDelegate {
        void onSelectionChanged(List<Node> selection);

        void onDeleteKeyPressed();
    }
}
