// File generated from template 2023/10/14 03:10:40
package ch.scaille.mldonkey.model;

import java.util.Arrays;
import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.gui.mvc.IComponentBinding;
import ch.scaille.gui.mvc.IComponentLink;
import ch.scaille.gui.mvc.IObjectGuiModel;
import ch.scaille.gui.mvc.factories.Persisters;
import ch.scaille.gui.mvc.persisters.ObjectProviderPersister;
import ch.scaille.gui.mvc.properties.AbstractProperty;
import ch.scaille.gui.mvc.properties.Configuration;
import ch.scaille.gui.mvc.properties.IntProperty;
import ch.scaille.gui.mvc.properties.ObjectProperty;

public class FileQueryGuiModel extends GuiModel implements IObjectGuiModel<FileQuery> {
   
    private final ObjectProviderPersister.CurrentObjectProvider<FileQuery> currentObjectProvider = new ObjectProviderPersister.CurrentObjectProvider<>();

	public static final String RESULTS = "Results";
	
	public static final String ID = "Id";
	
	public static final String SEARCH_TEXT = "SearchText";
	
	

	protected final ObjectProperty<ch.scaille.gui.model.ListModel<ch.scaille.mldonkey.model.FileQueryResult>> resultsProperty;
	protected final IntProperty idProperty;
	protected final ObjectProperty<java.lang.String> searchTextProperty;
	
	
	protected final AbstractProperty[] allProperties;
	
    public FileQueryGuiModel(final String prefix, ModelConfiguration config) {
		super(config.ifNotSet(()->	GuiModel.createErrorProperty(prefix + "FileQuery-Error", config)));
		resultsProperty = new ObjectProperty<ch.scaille.gui.model.ListModel<ch.scaille.mldonkey.model.FileQueryResult>>(prefix + RESULTS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileQuery::getResults, null)),
			implicitConverters(FileQuery.class, RESULTS, ch.scaille.gui.model.ListModel.class));
		idProperty = new IntProperty(prefix + ID, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileQuery::getId, FileQuery::setId)),
			implicitConverters(FileQuery.class, ID, java.lang.Integer.class));
		searchTextProperty = new ObjectProperty<java.lang.String>(prefix + SEARCH_TEXT, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileQuery::getSearchText, FileQuery::setSearchText)),
			implicitConverters(FileQuery.class, SEARCH_TEXT, java.lang.String.class));
		
		allProperties = new AbstractProperty[]{resultsProperty, idProperty, searchTextProperty};
    }
            
    public FileQueryGuiModel(ModelConfiguration config) {
    	this("", config);
    }

	public Class<?> getContainerClass() {
		return FileQuery.class;
	}

	public ObjectProperty<ch.scaille.gui.model.ListModel<ch.scaille.mldonkey.model.FileQueryResult>> getResultsProperty() {
	    return resultsProperty;
	}
	public IntProperty getIdProperty() {
	    return idProperty;
	}
	public ObjectProperty<java.lang.String> getSearchTextProperty() {
	    return searchTextProperty;
	}
	

    @Override
    public void load() {
    	try {
    		getPropertySupport().transmitAllToComponentOnly();
			Arrays.stream(allProperties).forEach(p -> p.load(this));
		} finally {
			getPropertySupport().enableAllTransmit();
		}
    }

    @Override
    public void save() {
		Arrays.stream(allProperties).forEach(AbstractProperty::save);
    }

    @Override
    public void setCurrentObject(final FileQuery value) {
        currentObjectProvider.setObject(value);
    }

    public IComponentBinding<FileQuery> loadBinding() {
        return new IComponentBinding<FileQuery>() {
        
            @Override
            public void addComponentValueChangeListener(final IComponentLink<FileQuery> link) {
                // nope
            }
            
            @Override
			public void removeComponentValueChangeListener() {
				  // nope
			}
            
            @Override
            public void setComponentValue(final AbstractProperty source, final FileQuery value) {
                if (value != null) {
                    setCurrentObject(value);
                    load();
                }
            }
        };
    }
}
