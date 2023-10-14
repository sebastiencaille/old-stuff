/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

public class MlClientKind extends AbstractMlTypeContainer implements IMlType {
	private final MlInt8 clientType = new MlInt8();
	private final MlString name = new MlString();
	private final MlInt8 hostData = new MlInt8();
	private final MlMd4 hashIdentifier = new MlMd4();
	private final MlInt32 ipAddress = new MlInt32();
	private final MlInt8 geoIP = new MlInt8();
	private final MlInt16 port = new MlInt16();

	@Override
	protected boolean hasValue(final IMlType type) {
		final byte tt = this.clientType.value();
		if (type == this.name || type == this.hashIdentifier || type == this.hostData) {
			return tt == 1;
		}
		if (type == this.ipAddress || type == this.geoIP || type == this.port) {
			return tt == 1 || tt == 0;
		}
		return super.hasValue(type);
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.clientType, this.name, this.hostData, this.hashIdentifier, this.ipAddress,
				this.geoIP, this.port };
	}

	@Override
	public String toString() {
		return "ClientKind " + this.clientType.value();
	}

	public int getGeoIp() {
		return this.geoIP.value() & 255;
	}
}
