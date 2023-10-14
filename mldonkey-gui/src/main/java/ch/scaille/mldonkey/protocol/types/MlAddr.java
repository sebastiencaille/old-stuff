/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

public class MlAddr extends AbstractMlTypeContainer implements IMlType {
	private final MlInt8 addressType = new MlInt8();
	private final MlInt32 ipAddress = new MlInt32();
	private final MlInt8 geoIP = new MlInt8();
	private final MlString nameAddress = new MlString();
	private final MlBool blocked = new MlBool();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.addressType, this.ipAddress, this.geoIP, this.nameAddress, this.blocked };
	}

	@Override
	protected boolean hasValue(final IMlType type) {
		if (type == this.ipAddress) {
			return this.addressType.value() == 0;
		}
		if (type == this.geoIP) {
			return this.addressType.value() == 0 || this.addressType.value() == 1;
		}
		if (type == this.nameAddress) {
			return this.addressType.value() == 1;
		}
		return super.hasValue(type);
	}

	public int getGeoIp() {
		return this.geoIP.value() & 255;
	}
}
