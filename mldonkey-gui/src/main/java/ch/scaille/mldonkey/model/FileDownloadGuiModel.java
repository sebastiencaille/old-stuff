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
import ch.scaille.gui.mvc.properties.BooleanProperty;
import ch.scaille.gui.mvc.properties.LongProperty;
import ch.scaille.gui.mvc.properties.FloatProperty;
import ch.scaille.gui.mvc.properties.MapProperty;
import ch.scaille.gui.mvc.properties.IntProperty;
import ch.scaille.gui.mvc.properties.SetProperty;
import ch.scaille.gui.mvc.properties.ListProperty;
import ch.scaille.gui.mvc.properties.ObjectProperty;

public class FileDownloadGuiModel extends GuiModel implements IObjectGuiModel<FileDownload> {
   
    private final ObjectProviderPersister.CurrentObjectProvider<FileDownload> currentObjectProvider = new ObjectProviderPersister.CurrentObjectProvider<>();

	public static final String AVAILABILITY = "Availability";
	
	public static final String CHUNKS = "Chunks";
	
	public static final String NUMBER_OF_SOURCES = "NumberOfSources";
	
	public static final String WARN_DATA = "WarnData";
	
	public static final String DOWNLOAD_NOTIFIED = "DownloadNotified";
	
	public static final String BLACK_LIST_DATA = "BlackListData";
	
	public static final String DOWNLOAD_RATE = "DownloadRate";
	
	public static final String HAS_FIRST_BYTE = "HasFirstByte";
	
	public static final String LAST_SEEN = "LastSeen";
	
	public static final String NAME = "Name";
	
	public static final String BLACK_LIST_SIZE = "BlackListSize";
	
	public static final String NAMES = "Names";
	
	public static final String IMMEDIATE_AVAILABILITY = "ImmediateAvailability";
	
	public static final String FORMAT = "Format";
	
	public static final String SHARE_CHECKED = "ShareChecked";
	
	public static final String DOWNLOADED_SIZE = "DownloadedSize";
	
	public static final String STATE = "State";
	
	public static final String LINKS = "Links";
	
	public static final String ID = "Id";
	
	public static final String MD_4 = "Md4";
	
	public static final String IDENTIFIERS = "Identifiers";
	
	public static final String WARNINGS = "Warnings";
	
	public static final String FILE_SIZE = "FileSize";
	
	public static final String AVAILABILITIES = "Availabilities";
	
	

	protected final FloatProperty availabilityProperty;
	protected final ObjectProperty<java.lang.String> chunksProperty;
	protected final IntProperty numberOfSourcesProperty;
	protected final ObjectProperty<java.util.Collection<java.lang.String>> warnDataProperty;
	protected final BooleanProperty downloadNotifiedProperty;
	protected final ObjectProperty<java.util.Collection<java.lang.String>> blackListDataProperty;
	protected final FloatProperty downloadRateProperty;
	protected final BooleanProperty hasFirstByteProperty;
	protected final IntProperty lastSeenProperty;
	protected final ObjectProperty<java.lang.String> nameProperty;
	protected final LongProperty blackListSizeProperty;
	protected final SetProperty<java.lang.String> namesProperty;
	protected final FloatProperty immediateAvailabilityProperty;
	protected final ObjectProperty<java.lang.String> formatProperty;
	protected final BooleanProperty shareCheckedProperty;
	protected final LongProperty downloadedSizeProperty;
	protected final ObjectProperty<ch.scaille.mldonkey.protocol.types.FileState> stateProperty;
	protected final ListProperty<java.lang.String> linksProperty;
	protected final IntProperty idProperty;
	protected final ObjectProperty<byte[]> md4Property;
	protected final ListProperty<java.lang.String> identifiersProperty;
	protected final ObjectProperty<ch.scaille.mldonkey.model.WarningLevel> warningsProperty;
	protected final LongProperty fileSizeProperty;
	protected final MapProperty<ch.scaille.mldonkey.model.Client,java.lang.String> availabilitiesProperty;
	
	
	protected final AbstractProperty[] allProperties;
	
    public FileDownloadGuiModel(final String prefix, ModelConfiguration config) {
		super(config.ifNotSet(()->	GuiModel.createErrorProperty(prefix + "FileDownload-Error", config)));
		availabilityProperty = new FloatProperty(prefix + AVAILABILITY, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getAvailability, null)),
			implicitConverters(FileDownload.class, AVAILABILITY, java.lang.Float.class));
		chunksProperty = new ObjectProperty<java.lang.String>(prefix + CHUNKS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getChunks, FileDownload::setChunks)),
			implicitConverters(FileDownload.class, CHUNKS, java.lang.String.class));
		numberOfSourcesProperty = new IntProperty(prefix + NUMBER_OF_SOURCES, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getNumberOfSources, FileDownload::setNumberOfSources)),
			implicitConverters(FileDownload.class, NUMBER_OF_SOURCES, java.lang.Integer.class));
		warnDataProperty = new ObjectProperty<java.util.Collection<java.lang.String>>(prefix + WARN_DATA, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getWarnData, null)),
			implicitConverters(FileDownload.class, WARN_DATA, java.util.Collection.class));
		downloadNotifiedProperty = new BooleanProperty(prefix + DOWNLOAD_NOTIFIED, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::isDownloadNotified, FileDownload::setDownloadNotified)),
			implicitConverters(FileDownload.class, DOWNLOAD_NOTIFIED, java.lang.Boolean.class));
		blackListDataProperty = new ObjectProperty<java.util.Collection<java.lang.String>>(prefix + BLACK_LIST_DATA, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getBlackListData, null)),
			implicitConverters(FileDownload.class, BLACK_LIST_DATA, java.util.Collection.class));
		downloadRateProperty = new FloatProperty(prefix + DOWNLOAD_RATE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getDownloadRate, FileDownload::setDownloadRate)),
			implicitConverters(FileDownload.class, DOWNLOAD_RATE, java.lang.Float.class));
		hasFirstByteProperty = new BooleanProperty(prefix + HAS_FIRST_BYTE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::isHasFirstByte, FileDownload::setHasFirstByte)),
			implicitConverters(FileDownload.class, HAS_FIRST_BYTE, java.lang.Boolean.class));
		lastSeenProperty = new IntProperty(prefix + LAST_SEEN, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getLastSeen, FileDownload::setLastSeen)),
			implicitConverters(FileDownload.class, LAST_SEEN, java.lang.Integer.class));
		nameProperty = new ObjectProperty<java.lang.String>(prefix + NAME, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getName, FileDownload::setName)),
			implicitConverters(FileDownload.class, NAME, java.lang.String.class));
		blackListSizeProperty = new LongProperty(prefix + BLACK_LIST_SIZE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getBlackListSize, null)),
			implicitConverters(FileDownload.class, BLACK_LIST_SIZE, java.lang.Long.class));
		namesProperty = new SetProperty<java.lang.String>(prefix + NAMES, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getNames, null)),
			implicitConverters(FileDownload.class, NAMES, java.util.Set.class));
		immediateAvailabilityProperty = new FloatProperty(prefix + IMMEDIATE_AVAILABILITY, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getImmediateAvailability, null)),
			implicitConverters(FileDownload.class, IMMEDIATE_AVAILABILITY, java.lang.Float.class));
		formatProperty = new ObjectProperty<java.lang.String>(prefix + FORMAT, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getFormat, FileDownload::setFormat)),
			implicitConverters(FileDownload.class, FORMAT, java.lang.String.class));
		shareCheckedProperty = new BooleanProperty(prefix + SHARE_CHECKED, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::isShareChecked, FileDownload::setShareChecked)),
			implicitConverters(FileDownload.class, SHARE_CHECKED, java.lang.Boolean.class));
		downloadedSizeProperty = new LongProperty(prefix + DOWNLOADED_SIZE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getDownloadedSize, FileDownload::setDownloadedSize)),
			implicitConverters(FileDownload.class, DOWNLOADED_SIZE, java.lang.Long.class));
		stateProperty = new ObjectProperty<ch.scaille.mldonkey.protocol.types.FileState>(prefix + STATE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getState, FileDownload::setState)),
			implicitConverters(FileDownload.class, STATE, ch.scaille.mldonkey.protocol.types.FileState.class));
		linksProperty = new ListProperty<java.lang.String>(prefix + LINKS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getLinks, null)),
			implicitConverters(FileDownload.class, LINKS, java.util.List.class));
		idProperty = new IntProperty(prefix + ID, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getId, null)),
			implicitConverters(FileDownload.class, ID, java.lang.Integer.class));
		md4Property = new ObjectProperty<byte[]>(prefix + MD_4, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getMd4, FileDownload::setMd4)),
			implicitConverters(FileDownload.class, MD_4, byte[].class));
		identifiersProperty = new ListProperty<java.lang.String>(prefix + IDENTIFIERS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getIdentifiers, null)),
			implicitConverters(FileDownload.class, IDENTIFIERS, java.util.List.class));
		warningsProperty = new ObjectProperty<ch.scaille.mldonkey.model.WarningLevel>(prefix + WARNINGS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getWarnings, FileDownload::setWarnings)),
			implicitConverters(FileDownload.class, WARNINGS, ch.scaille.mldonkey.model.WarningLevel.class));
		fileSizeProperty = new LongProperty(prefix + FILE_SIZE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getFileSize, FileDownload::setFileSize)),
			implicitConverters(FileDownload.class, FILE_SIZE, java.lang.Long.class));
		availabilitiesProperty = new MapProperty<ch.scaille.mldonkey.model.Client,java.lang.String>(prefix + AVAILABILITIES, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.getSet(FileDownload::getAvailabilities, null)),
			implicitConverters(FileDownload.class, AVAILABILITIES, java.util.Map.class));
		
		allProperties = new AbstractProperty[]{availabilityProperty, chunksProperty, numberOfSourcesProperty, warnDataProperty, downloadNotifiedProperty, blackListDataProperty, downloadRateProperty, hasFirstByteProperty, lastSeenProperty, nameProperty, blackListSizeProperty, namesProperty, immediateAvailabilityProperty, formatProperty, shareCheckedProperty, downloadedSizeProperty, stateProperty, linksProperty, idProperty, md4Property, identifiersProperty, warningsProperty, fileSizeProperty, availabilitiesProperty};
    }
            
    public FileDownloadGuiModel(ModelConfiguration config) {
    	this("", config);
    }

	public Class<?> getContainerClass() {
		return FileDownload.class;
	}

	public FloatProperty getAvailabilityProperty() {
	    return availabilityProperty;
	}
	public ObjectProperty<java.lang.String> getChunksProperty() {
	    return chunksProperty;
	}
	public IntProperty getNumberOfSourcesProperty() {
	    return numberOfSourcesProperty;
	}
	public ObjectProperty<java.util.Collection<java.lang.String>> getWarnDataProperty() {
	    return warnDataProperty;
	}
	public BooleanProperty getDownloadNotifiedProperty() {
	    return downloadNotifiedProperty;
	}
	public ObjectProperty<java.util.Collection<java.lang.String>> getBlackListDataProperty() {
	    return blackListDataProperty;
	}
	public FloatProperty getDownloadRateProperty() {
	    return downloadRateProperty;
	}
	public BooleanProperty getHasFirstByteProperty() {
	    return hasFirstByteProperty;
	}
	public IntProperty getLastSeenProperty() {
	    return lastSeenProperty;
	}
	public ObjectProperty<java.lang.String> getNameProperty() {
	    return nameProperty;
	}
	public LongProperty getBlackListSizeProperty() {
	    return blackListSizeProperty;
	}
	public SetProperty<java.lang.String> getNamesProperty() {
	    return namesProperty;
	}
	public FloatProperty getImmediateAvailabilityProperty() {
	    return immediateAvailabilityProperty;
	}
	public ObjectProperty<java.lang.String> getFormatProperty() {
	    return formatProperty;
	}
	public BooleanProperty getShareCheckedProperty() {
	    return shareCheckedProperty;
	}
	public LongProperty getDownloadedSizeProperty() {
	    return downloadedSizeProperty;
	}
	public ObjectProperty<ch.scaille.mldonkey.protocol.types.FileState> getStateProperty() {
	    return stateProperty;
	}
	public ListProperty<java.lang.String> getLinksProperty() {
	    return linksProperty;
	}
	public IntProperty getIdProperty() {
	    return idProperty;
	}
	public ObjectProperty<byte[]> getMd4Property() {
	    return md4Property;
	}
	public ListProperty<java.lang.String> getIdentifiersProperty() {
	    return identifiersProperty;
	}
	public ObjectProperty<ch.scaille.mldonkey.model.WarningLevel> getWarningsProperty() {
	    return warningsProperty;
	}
	public LongProperty getFileSizeProperty() {
	    return fileSizeProperty;
	}
	public MapProperty<ch.scaille.mldonkey.model.Client,java.lang.String> getAvailabilitiesProperty() {
	    return availabilitiesProperty;
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
    public void setCurrentObject(final FileDownload value) {
        currentObjectProvider.setObject(value);
    }

    public IComponentBinding<FileDownload> loadBinding() {
        return new IComponentBinding<FileDownload>() {
        
            @Override
            public void addComponentValueChangeListener(final IComponentLink<FileDownload> link) {
                // nope
            }
            
            @Override
			public void removeComponentValueChangeListener() {
				  // nope
			}
            
            @Override
            public void setComponentValue(final AbstractProperty source, final FileDownload value) {
                if (value != null) {
                    setCurrentObject(value);
                    load();
                }
            }
        };
    }
}
