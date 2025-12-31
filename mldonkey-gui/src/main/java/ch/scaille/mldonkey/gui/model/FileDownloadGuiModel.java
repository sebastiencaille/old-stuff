package ch.scaille.mldonkey.gui.model;

import javax.annotation.processing.Generated;

import java.util.Arrays;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.gui.mvc.IObjectGuiModel;
import ch.scaille.javabeans.IComponentBinding;
import ch.scaille.javabeans.IComponentLink;
import ch.scaille.javabeans.IComponentChangeSource;
import ch.scaille.javabeans.properties.AbstractProperty;
import ch.scaille.javabeans.properties.Configuration;

import ch.scaille.javabeans.persisters.IPersisterFactory.ObjectHolder;
import ch.scaille.javabeans.persisters.Persisters;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.LongProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.javabeans.properties.SetProperty;
import ch.scaille.javabeans.properties.FloatProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.IntProperty;
import ch.scaille.javabeans.properties.MapProperty;
import org.jspecify.annotations.NullMarked;

@Generated(value = "ch.scaille.gui.mvc.GuiModelGenerator", date = "2025/08/01 12:50", comments = "-sp ch.scaille.mldonkey.model -tp ch.scaille.mldonkey.gui.model -s /home/scaille/src/github/old-stuff/mldonkey-gui/target/classes -t /home/scaille/src/github/old-stuff/mldonkey-gui/src/main/java")
@NullMarked
public class FileDownloadGuiModel extends GuiModel implements IObjectGuiModel<ch.scaille.mldonkey.model.FileDownload> {
   
    private final ObjectHolder<ch.scaille.mldonkey.model.FileDownload> currentObjectProvider = new ObjectHolder<>();

	public static final String AVAILABILITY = "Availability";
	
	public static final String CHUNKS = "Chunks";
	
	public static final String NUMBER_OF_SOURCES = "NumberOfSources";
	
	public static final String WARN_DATA = "WarnData";
	
	public static final String DOWNLOAD_NOTIFIED = "DownloadNotified";
	
	public static final String BLACK_LIST_DATA = "BlackListData";
	
	public static final String HAS_FIRST_BYTE = "HasFirstByte";
	
	public static final String DOWNLOAD_RATE = "DownloadRate";
	
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
	protected final BooleanProperty hasFirstByteProperty;
	protected final FloatProperty downloadRateProperty;
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
	protected final MapProperty<ch.scaille.mldonkey.model.Client, java.lang.String> availabilitiesProperty;
	
	
	protected final AbstractProperty[] allProperties;
	
    public FileDownloadGuiModel(final String prefix, ModelConfiguration.ModelConfigurationBuilder config) {
		super(config.ifNotSet(c->	GuiModel.createErrorProperty(prefix + "FileDownload-Error", c)));
		availabilityProperty = new FloatProperty(prefix + AVAILABILITY, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getAvailability, null)),
			implicitConverters(FileDownload.class, AVAILABILITY, java.lang.Float.class));
		chunksProperty = new ObjectProperty<java.lang.String>(prefix + CHUNKS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getChunks, FileDownload::setChunks)),
			implicitConverters(FileDownload.class, CHUNKS, java.lang.String.class));
		numberOfSourcesProperty = new IntProperty(prefix + NUMBER_OF_SOURCES, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getNumberOfSources, FileDownload::setNumberOfSources)),
			implicitConverters(FileDownload.class, NUMBER_OF_SOURCES, java.lang.Integer.class));
		warnDataProperty = new ObjectProperty<java.util.Collection<java.lang.String>>(prefix + WARN_DATA, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getWarnData, null)),
			implicitConverters(FileDownload.class, WARN_DATA, java.util.Collection.class));
		downloadNotifiedProperty = new BooleanProperty(prefix + DOWNLOAD_NOTIFIED, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::isDownloadNotified, FileDownload::setDownloadNotified)),
			implicitConverters(FileDownload.class, DOWNLOAD_NOTIFIED, java.lang.Boolean.class));
		blackListDataProperty = new ObjectProperty<java.util.Collection<java.lang.String>>(prefix + BLACK_LIST_DATA, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getBlackListData, null)),
			implicitConverters(FileDownload.class, BLACK_LIST_DATA, java.util.Collection.class));
		hasFirstByteProperty = new BooleanProperty(prefix + HAS_FIRST_BYTE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::isHasFirstByte, FileDownload::setHasFirstByte)),
			implicitConverters(FileDownload.class, HAS_FIRST_BYTE, java.lang.Boolean.class));
		downloadRateProperty = new FloatProperty(prefix + DOWNLOAD_RATE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getDownloadRate, FileDownload::setDownloadRate)),
			implicitConverters(FileDownload.class, DOWNLOAD_RATE, java.lang.Float.class));
		lastSeenProperty = new IntProperty(prefix + LAST_SEEN, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getLastSeen, FileDownload::setLastSeen)),
			implicitConverters(FileDownload.class, LAST_SEEN, java.lang.Integer.class));
		nameProperty = new ObjectProperty<java.lang.String>(prefix + NAME, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getName, FileDownload::setName)),
			implicitConverters(FileDownload.class, NAME, java.lang.String.class));
		blackListSizeProperty = new LongProperty(prefix + BLACK_LIST_SIZE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getBlackListSize, null)),
			implicitConverters(FileDownload.class, BLACK_LIST_SIZE, java.lang.Long.class));
		namesProperty = new SetProperty<java.lang.String>(prefix + NAMES, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getNames, null)),
			implicitConverters(FileDownload.class, NAMES, java.util.Set.class));
		immediateAvailabilityProperty = new FloatProperty(prefix + IMMEDIATE_AVAILABILITY, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getImmediateAvailability, null)),
			implicitConverters(FileDownload.class, IMMEDIATE_AVAILABILITY, java.lang.Float.class));
		formatProperty = new ObjectProperty<java.lang.String>(prefix + FORMAT, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getFormat, FileDownload::setFormat)),
			implicitConverters(FileDownload.class, FORMAT, java.lang.String.class));
		shareCheckedProperty = new BooleanProperty(prefix + SHARE_CHECKED, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::isShareChecked, FileDownload::setShareChecked)),
			implicitConverters(FileDownload.class, SHARE_CHECKED, java.lang.Boolean.class));
		downloadedSizeProperty = new LongProperty(prefix + DOWNLOADED_SIZE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getDownloadedSize, FileDownload::setDownloadedSize)),
			implicitConverters(FileDownload.class, DOWNLOADED_SIZE, java.lang.Long.class));
		stateProperty = new ObjectProperty<ch.scaille.mldonkey.protocol.types.FileState>(prefix + STATE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getState, FileDownload::setState)),
			implicitConverters(FileDownload.class, STATE, ch.scaille.mldonkey.protocol.types.FileState.class));
		linksProperty = new ListProperty<java.lang.String>(prefix + LINKS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getLinks, null)),
			implicitConverters(FileDownload.class, LINKS, java.util.List.class));
		idProperty = new IntProperty(prefix + ID, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getId, null)),
			implicitConverters(FileDownload.class, ID, java.lang.Integer.class));
		md4Property = new ObjectProperty<byte[]>(prefix + MD_4, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getMd4, FileDownload::setMd4)),
			implicitConverters(FileDownload.class, MD_4, byte[].class));
		identifiersProperty = new ListProperty<java.lang.String>(prefix + IDENTIFIERS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getIdentifiers, null)),
			implicitConverters(FileDownload.class, IDENTIFIERS, java.util.List.class));
		warningsProperty = new ObjectProperty<ch.scaille.mldonkey.model.WarningLevel>(prefix + WARNINGS, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getWarnings, FileDownload::setWarnings)),
			implicitConverters(FileDownload.class, WARNINGS, ch.scaille.mldonkey.model.WarningLevel.class));
		fileSizeProperty = new LongProperty(prefix + FILE_SIZE, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getFileSize, FileDownload::setFileSize)),
			implicitConverters(FileDownload.class, FILE_SIZE, java.lang.Long.class));
		availabilitiesProperty = new MapProperty<ch.scaille.mldonkey.model.Client, java.lang.String>(prefix + AVAILABILITIES, this).configureTyped(
			Configuration.persistent(currentObjectProvider, Persisters.persister(FileDownload::getAvailabilities, null)),
			implicitConverters(FileDownload.class, AVAILABILITIES, java.util.Map.class));
		
		allProperties = new AbstractProperty[]{availabilityProperty, chunksProperty, numberOfSourcesProperty, warnDataProperty, downloadNotifiedProperty, blackListDataProperty, hasFirstByteProperty, downloadRateProperty, lastSeenProperty, nameProperty, blackListSizeProperty, namesProperty, immediateAvailabilityProperty, formatProperty, shareCheckedProperty, downloadedSizeProperty, stateProperty, linksProperty, idProperty, md4Property, identifiersProperty, warningsProperty, fileSizeProperty, availabilitiesProperty};
    }
            
    public FileDownloadGuiModel(ModelConfiguration.ModelConfigurationBuilder config) {
    	this("", config);
    }

	public Class<?> getContainerClass() {
		return ch.scaille.mldonkey.model.FileDownload.class;
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
	public BooleanProperty getHasFirstByteProperty() {
	    return hasFirstByteProperty;
	}
	public FloatProperty getDownloadRateProperty() {
	    return downloadRateProperty;
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
	public MapProperty<ch.scaille.mldonkey.model.Client, java.lang.String> getAvailabilitiesProperty() {
	    return availabilitiesProperty;
	}
	

    @Override
    public void load() {
    	try {
    		getPropertySupport().transmitChangesOnlyToComponent();
			Arrays.stream(allProperties).forEach(p -> p.load(this));
		} finally {
			getPropertySupport().transmitChangesBothWays();
		}
    }

    @Override
    public void save() {
		Arrays.stream(allProperties).forEach(AbstractProperty::save);
    }

    @Override
    public void setCurrentObject(final ch.scaille.mldonkey.model.FileDownload value) {
        currentObjectProvider.setObject(value);
    }

    public IComponentBinding<ch.scaille.mldonkey.model.FileDownload> loadBinding() {
        return new IComponentBinding<>() {
        
            @Override
            public void addComponentValueChangeListener(final IComponentLink<ch.scaille.mldonkey.model.FileDownload> link) {
                // nope
            }
            
            @Override
			public void removeComponentValueChangeListener() {
				  // nope
			}
            
            @Override
            public void setComponentValue(final IComponentChangeSource source, final ch.scaille.mldonkey.model.FileDownload value) {
                if (value != null) {
                    setCurrentObject(value);
                    load();
                }
            }
        };
    }
}
