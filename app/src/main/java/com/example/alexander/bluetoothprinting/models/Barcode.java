package com.example.alexander.bluetoothprinting.models;

/**
 * Created by alexander on 12/9/15.
 */
public class Barcode {

    private int value;
    private int width;
    private int height;
    private int horizontalTab;
    private BarcodeSystem barcodeSystem;

    public int getHeightCommandCode() {
        return 104;
    }

    public int getWidthCommandCode() {
        return 119;
    }

    public int getPrintCodeCommand() {
        return 107;
    }

    public int getWidth() {
        return width;

    }

    public int getHeight() {
        return height;
    }

    public int getHorizontalTab() {
        return horizontalTab;
    }

    public BarcodeSystem getBarcodeSystem() {
        return barcodeSystem;
    }

    public int getValue() {
        return value;
    }

    public static BarcodeBuilder builder() {
        return new Barcode().new BarcodeBuilder();
    }

    @Override
    public String toString() {
        return "Barcode{" +
                "width=" + width +
                ", height=" + height +
                ", horizontalTab=" + horizontalTab +
                ", barcodeSystem=" + barcodeSystem +
                '}';
    }

    public class BarcodeBuilder {

        private BarcodeBuilder() {}

        public Barcode build() {
            return Barcode.this;
        }

        public BarcodeBuilder setValue(int value) {

            Barcode.this.value = value;
            return this;
        }

        public BarcodeBuilder setWidth(int width) {

            Barcode.this.width = width;
            return this;
        }

        public BarcodeBuilder setHeight(int height) {

            Barcode.this.height = height;
            return this;
        }

        public BarcodeBuilder setHorizontalTab(int horizontalTab) {

            Barcode.this.horizontalTab = horizontalTab;
            return this;
        }

        public BarcodeBuilder setCodeSystem(BarcodeSystem codeSystem) {

            Barcode.this.barcodeSystem = codeSystem;
            return this;
        }
    }
}
