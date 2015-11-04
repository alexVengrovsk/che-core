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
package org.eclipse.che.api.workspace.server.model.impl;


import org.eclipse.che.api.core.model.workspace.ModuleConfig;
import org.eclipse.che.api.core.model.workspace.ProjectConfig;

import java.util.*;

import static java.util.stream.Collectors.toMap;

//TODO move?

/**
 * Data object for {@link ModuleConfig}.
 *
 * @author Eugene Voevodin
 */
public class ModuleConfigImpl implements ModuleConfig {

    private String                    name;
    private String                    path;
    private String                    description;
    private String                    type;
    private List<String>              mixinTypes;
    private Map<String, List<String>> attributes;
    private List<ModuleConfig>        modules;

    public ModuleConfigImpl() {
    }

    public ModuleConfigImpl(ProjectConfig projectCfg) {
        name = projectCfg.getName();
        path = projectCfg.getPath();
        description = projectCfg.getDescription();
        type = projectCfg.getType();
        mixinTypes = new ArrayList<>(projectCfg.getMixinTypes());
        modules = new ArrayList<>(projectCfg.getModules() != null ? projectCfg.getModules() : Collections.<ModuleConfig>emptyList());
        attributes = projectCfg.getAttributes()
                               .entrySet()
                               .stream()
                               .collect(toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public List<String> getMixinTypes() {
        if (mixinTypes == null) {
            mixinTypes = new ArrayList<>();
        }
        return mixinTypes;
    }

    public void setMixinTypes(List<String> mixinTypes) {
        this.mixinTypes = mixinTypes;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        return attributes;
    }

    @Override
    public List<ModuleConfig> getModules() {
        return modules;
    }

    public void setModules(List<ModuleConfig> modules) {
        this.modules = modules;
    }

    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuleConfigImpl)) return false;
        final ModuleConfigImpl other = (ModuleConfigImpl)o;
        return Objects.equals(name, other.name) &&
               Objects.equals(path, other.path) &&
               Objects.equals(description, other.description) &&
               Objects.equals(type, other.type) &&
               getMixinTypes().equals(other.getMixinTypes()) &&
               getAttributes().equals(other.getAttributes()) &&
               getModules().equals(other.getModules());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + Objects.hashCode(name);
        hash = hash * 31 + Objects.hashCode(path);
        hash = hash * 31 + Objects.hashCode(description);
        hash = hash * 31 + Objects.hashCode(type);
        hash = hash * 31 + getMixinTypes().hashCode();
        hash = hash * 31 + getAttributes().hashCode();
        hash = hash * 31 + getModules().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "ModuleConfigImpl{" +
               "name='" + name + '\'' +
               ", path='" + path + '\'' +
               ", description='" + description + '\'' +
               ", type='" + type + '\'' +
               ", mixinTypes=" + mixinTypes +
               ", attributes=" + attributes +
               ", modules=" + modules +
               '}';
    }
}
