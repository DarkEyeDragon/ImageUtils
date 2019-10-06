package me.darkeyedragon.imageutils.client.image;

public enum ImageType {

    PNG(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A),
    JPG(0xFF, 0xD8, 0xFF, 0xDB),
    TIFF(0x4D, 0x4D, 0x00, 0x2A);

    final int[] hexValue;

    ImageType(int... hexValue) {
        this.hexValue = hexValue;
    }

    public int[] getHexValue() {
        return hexValue;
    }

    public boolean compare(int... toCompare) {
        if (toCompare.length < hexValue.length) return false;
        for (int i = 0; i < hexValue.length; i++) {
            if (toCompare[i] != hexValue[i]) {
                return false;
            }
        }
        return true;
    }
}
