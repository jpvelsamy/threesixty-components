package com.github.markash.ui.view;

import com.github.markash.ui.component.field.HeaderToolbar;
import com.github.markash.ui.component.field.Toolbar;
import com.github.markash.ui.event.CloseOpenWindowsEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SuppressWarnings("serial")
public abstract class AbstractDashboardView extends VerticalLayout implements View {
    //private final VerticalLayout root;
    private final String viewCaption;
    private Toolbar toolbar;
	private boolean showToolbar = true;
	private transient ApplicationEventPublisher eventPublisher;

    private static final String STYLE_DASHBOARD_VIEW = "dashboard-view";
    
    public AbstractDashboardView(
    		final String viewCaption) {

    	this(viewCaption, new HeaderToolbar(viewCaption));
    }

	public AbstractDashboardView(
			final String viewCaption,
			final Toolbar toolbar) {

		this.viewCaption = viewCaption;
		this.toolbar = toolbar;
		//this.root = new VerticalLayout();
		this.eventPublisher = null;
	}

    @SuppressWarnings("unused")
	public AbstractDashboardView(
			final String viewCaption,
			final ApplicationEventPublisher eventPublisher) {

		this.viewCaption = viewCaption;
        this.toolbar = new HeaderToolbar(viewCaption);
		//this.root = new VerticalLayout();
    	this.eventPublisher = eventPublisher;
    }

	protected abstract Component buildContent();
	
	protected String getTitle() { return this.viewCaption; }

	@Override
	public String getCaption() {

		return this.toolbar.getCaption();
	}

	@Override
	public void setCaption(
			final String caption) {

		this.toolbar.setCaption(caption);
	}

	@Override
	public void setCaptionAsHtml(
			final boolean captionAsHtml) {

		this.toolbar.setCaptionAsHtml(captionAsHtml);
	}

	@Override
	public boolean isCaptionAsHtml() {

		return this.toolbar.isCaptionAsHtml();
	}

	protected boolean isBuilt() {
		return getStyleName().contains(STYLE_DASHBOARD_VIEW);
	}
	
	@PostConstruct
	@SuppressWarnings("unused")
	public void init() {		
		if (!isBuilt()) {
			addStyleName(ValoTheme.PANEL_BORDERLESS);
	        setSizeFull();

	        setMargin(true);
	        addStyleName(STYLE_DASHBOARD_VIEW);
	        //setContent(root);
	        Responsive.makeResponsive(this);

	        if (this.toolbar != null && this.showToolbar) {
				addComponent(this.toolbar/*buildHeader()*/);
			}

	        Component content = buildContent();
	        addComponent(content);
	        setExpandRatio(content, 1);
	        addLayoutClickListener(event -> Optional.ofNullable(eventPublisher).ifPresent(ep -> ep.publishEvent(new CloseOpenWindowsEvent(event))));
		}
	}

	/**
	 * Set whether the toolbar is shown above the dashboard view. This is useful when the toolbar is
	 * on another section of the user interface and used by the dashboard view.
	 * @param show Whether the toolbar should be displayed
	 */
	@SuppressWarnings("unused")
	public void setShowToolbar(
			final boolean show) {

		this.showToolbar = show;
	}

	/**
	 * Whether the toolbar is shown.T his is useful when the toolbar is
	 * 	 * on another section of the user interface and used by the dashboard view.
	 * @return True if the toolbar is shown else false
	 */
	@SuppressWarnings("unused")
	public boolean isShowToolbar() {
		return showToolbar;
	}

	/**
	 * Get the toolbar used by the dashboard view
	 * @return The toolbar
	 */
    public Toolbar getToolbar() {

		return this.toolbar;
	}

	/**
	 * Set the toolbar used by the dashboard view
	 * @param toolbar The toolbar
	 */
	public void setToolbar(
			final Toolbar toolbar) {

    	this.toolbar = toolbar;
	}

    @Override
    public void enter(
            final ViewChangeEvent event) {
    }
}
