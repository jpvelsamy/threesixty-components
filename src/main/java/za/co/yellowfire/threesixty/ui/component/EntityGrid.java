package za.co.yellowfire.threesixty.ui.component;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.renderers.HtmlRenderer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.vaadin.viritin.grid.MGrid;
import za.co.yellowfire.threesixty.ui.view.TableDefinition;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Optional;

/**
 * @author Mark P Ashworth
 */
public class EntityGrid<T> extends MGrid<T> {

    public EntityGrid(Class<T> beanType) {
        super(beanType);
    }

    public EntityGrid<T> withDataProvider(final DataProvider<T, ?> dataProvider) {
        setDataProvider(dataProvider);
        return this;
    }

    public EntityGrid<T> withDefinition(final TableDefinition definition) {
        int i = 0;
        Column<T, ?> column;

        /* Remove the columns added by the constructor when evaluating the beanType */
        removeAllColumns();

        /* Redefine the columns based upon the definition */
        for(String p : definition.getPropertyNames()) {
            if (p.equals(definition.getIdPropertyName())) {
                column = addColumn(row -> buildNavigationLink(row, p, Optional.of(definition.getEntityViewName()).orElse("error")), new HtmlRenderer());
                column.setCaption(definition.getHeaders()[i]);
            } else {
                column = addColumn(p);
                column.setCaption(definition.getHeaders()[i]);
            }
            i++;
        }
        return this;
    }

    @Override
    public EntityGrid<T> withProperties(String... properties) {
        return (EntityGrid<T>) super.withProperties(properties);
    }

    @Override
    public EntityGrid<T> withColumnHeaders(String... properties) {
        return (EntityGrid<T>) super.withColumnHeaders(properties);
    }

    @Override
    public EntityGrid<T> setRows(List<T> rows) {
        return (EntityGrid<T>) super.setRows(rows);
    }

    /**
     * Builds the navigation link for the table column that is used to drill down into a single instance of the row
     * @param value The row object to read the property link value from
     * @return The navigation link
     */
    private String buildNavigationLink(final Object value, final String property, final String entityViewName) {
        final String linkValue = getPropertyValue(value, property);
        return "<a href='#!" + buildNavigationState(entityViewName, linkValue) + "' target='_top'>" + linkValue + "</a>";
    }
    /**
     * Builds the navigation state for the entity view path, i.e. !#entityViewName/id
     * @param entityViewName The entity view name
     * @param id The id value of the table that was clicked
     * @return The entity view path
     */
    private String buildNavigationState(final String entityViewName, final String id) {
        return entityViewName + (id != null && StringUtils.isBlank(id) ? "" : "/" + id);
    }
    /**
     * Returns the property (i.e. column) value that should be used for the link of getLinkPropertyId()
     * @param value The object to read the link value from
     * @return The link value from the object or `Unavailable exception` if an IllegalArgumentException || IllegalAccessException is thrown
     */
    private String getPropertyValue(final Object value, final String property) {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(getBeanType(), property);
        try {
            return Optional.ofNullable(descriptor.getReadMethod().invoke(value)).orElse("").toString();
        } catch (Exception e) {
            return "Unavailable exception";
        }
    }
}
