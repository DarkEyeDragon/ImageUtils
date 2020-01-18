package me.darkeyedragon.imageutils.client.image;

import java.util.List;

public enum ImageType {

    //Usful link: https://en.wikipedia.org/wiki/List_of_file_signatures

    //Decimal      137,  80,   78,   71,    13,   10,   26,  10
    PNG(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A),
    //Decimal:     255,  216,  255,  216
    JPG(0xFF, 0xD8, 0xFF, 0xDB),

    JPG1(0xFF, 0xD8, 0xFF, 0xE0, 0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00, 0x01),

    JPG2(0xFF, 0xD8, 0xFF, 0xEE),

    //Decimal:      77,   77,    0,   42
    TIFF(0x4D, 0x4D, 0x00, 0x2A);

    final int[] hexValue;

    ImageType(int... hexValue) {
        this.hexValue = hexValue;
    }

    public int[] getHexValue() {
        return hexValue;
    }

    /*public boolean compare(int... toCompare) {
        if (toCompare.length < hexValue.length) return false;
        for (int i = 0; i < hexValue.length; i++) {
            if (toCompare[i] != hexValue[i]) {
                return false;
            }
        }
        return true;
    }*/
    public boolean compare(List<Integer> toCompare) {
        if (toCompare.size() < hexValue.length) return false;
        for (int i = 0; i < toCompare.size(); i++) {
            if ((toCompare.get(i) != hexValue[i]) && hexValue[i] != -1) {
                return false;
            }
        }
        return true;
    }
}
