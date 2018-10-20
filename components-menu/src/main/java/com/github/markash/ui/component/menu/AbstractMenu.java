/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.markash.ui.component.menu;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;


import java.util.Collection;
import java.util.Optional;

public abstract class AbstractMenu extends CustomComponent {

    private MenuUtils utils;

    public AbstractMenu(
            final MenuUtils utils) {

        this.utils = utils;
    }

    public MenuUtils getUtils() {
        return utils;
    }

    @Override
    public void attach() {

        getTopMenuFactory()
                .ifPresent(factory -> factory.createTopMenu(this.utils.getTopMenuItems()));

        getSideMenuFactory()
                .ifPresent(factory -> factory.createSideMenu(this.utils.getSections(getUI().getClass())));

        getToolbarFactory()
                .ifPresent(ToolbarFactory::createToolbar);
    }


    protected abstract Optional<TopMenuFactory> getTopMenuFactory();
    protected abstract Optional<SideMenuFactory> getSideMenuFactory();
    protected abstract Optional<ToolbarFactory> getToolbarFactory();

    public enum MenuLocation {
        TOP,
        LEFT
    }

    public interface TopMenuFactory {
        Component createTopMenu(final Collection<MenuItemDescriptor> descriptor);
    }

    public interface SideMenuFactory {
        Component createSideMenu(final Collection<MenuSectionDescriptor> descriptor);
    }

    public interface ToolbarFactory {
        void createToolbar();
    }
    /**
     * Interface defining a factory for creating components that correspond to items in a side bar section. When
     * the item is clicked by the user, {@link MenuItemDescriptor#itemInvoked(com.vaadin.ui.UI)}
     * must be called.
     */
    public interface ComponentFactory<S, T extends Component> {

        /**
         * Creates a component to be added to a menu by a {@link ComponentFactory}.
         * Remember to call {@link MenuItemDescriptor#itemInvoked(com.vaadin.ui.UI)} when the item
         * is clicked by the user.
         *
         * @param menuLocation the location of the menu
         * @param descriptor the descriptor of the menu item, must not be {@code null}.
         * @return a component, never {@code null}.
         */
        T createItemComponent(MenuLocation menuLocation, S descriptor);
    }

    /**
     * Interface defining a filter that can limit which items show up in the side bar.
     */
    public interface ItemFilter {

        /**
         * Checks if the specified side bar item passes the filter or not. Items that do not pass are excluded
         * from the side bar.
         *
         * @param descriptor the descriptor of the side bar item, must not be {@code null}.
         * @return true if the item passes the filter, false if it does not.
         */
        boolean passesFilter(MenuItemDescriptor descriptor);

    }
}
