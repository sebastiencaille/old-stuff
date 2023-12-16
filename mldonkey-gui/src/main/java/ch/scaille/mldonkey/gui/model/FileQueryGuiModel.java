package ch.scaille.mldonkey.gui.model;

import javax.annotation.processing.Generated;

import java.util.Arrays;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.gui.mvc.IObjectGuiModel;
import ch.scaille.javabeans.IComponentBinding;
import ch.scaille.javabeans.IComponentLink;
import ch.scaille.javabeans.properties.AbstractProperty;
import ch.scaille.javabeans.properties.Configuration;
import ch.scaille.javabeans.persisters.IPersisterFactory.ObjectHolder;
import ch.scaille.javabeans.persisters.Persisters;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.javabeans.properties.IntProperty;
import ch.scaille.mldonkey.model.FileQuery;

@Generated(value = "ch.scaille.gui.mvc.GuiModelGenerator", date = "2023/12/10 21:22", comments = "-sp ch.scaille.mldonkey.model -tp ch.scaille.mldonkey.gui.model -s /home/scaille/src/github/old-stuff/mldonkey-gui/target/classes -t /home/scaille/src/github/old-stuff/mldonkey-gui/src/main/java")
public class FileQueryGuiModel extends GuiModel implements IObjectGuiModel<ch.scaille.mldonkey.model.FileQuery> {
   
    private final ObjectHolder<ch.scaille.mldonkey.model.FileQuery> currentObjectProvider = new ObjectHolder<>();

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
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileQuery::getResults, null)),
			implicitConverters(FileQuery.class, RESULTS, ch.scaille.gui.model.ListModel.class));
		idProperty = new IntProperty(prefix + ID, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileQuery::getId, FileQuery::setId)),
			implicitConverters(FileQuery.class, ID, java.lang.Integer.class));
		searchTextProperty = new ObjectProperty<java.lang.String>(prefix + SEARCH_TEXT, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileQuery::getSearchText, FileQuery::setSearchText)),
			implicitConverters(FileQuery.class, SEARCH_TEXT, java.lang.String.class));
		
		allProperties = new AbstractProperty[]{resultsProperty, idProperty, searchTextProperty};
    }
            
    public FileQueryGuiModel(ModelConfiguration config) {
    	this("", config);
    }

	public Class<?> getContainerClass() {
		return ch.scaille.mldonkey.model.FileQuery.class;
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
    public void setCurrentObject(final ch.scaille.mldonkey.model.FileQuery value) {
        currentObjectProvider.setObject(value);
    }

    public IComponentBinding<ch.scaille.mldonkey.model.FileQuery> loadBinding() {
        return new IComponentBinding<>() {
        
            @Override
            public void addComponentValueChangeListener(final IComponentLink<ch.scaille.mldonkey.model.FileQuery> link) {
                // nope
            }
            
            @Override
			public void removeComponentValueChangeListener() {
				  // nope
			}
            
            @Override
            public void setComponentValue(final AbstractProperty source, final ch.scaille.mldonkey.model.FileQuery value) {
                if (value != null) {
                    setCurrentObject(value);
                    load();
                }
            }
        };
    }
}
