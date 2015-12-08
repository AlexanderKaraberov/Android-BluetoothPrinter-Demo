# Android-BluetoothPrinter-Demo

Demo project shows how to print custom barcodes using Zonerich AB-330M Bluetooth printer. I use [this manual](http://www.mangohelp.com/wp-content/uploads/2010/10/AB-320M-AB-330M-en-qq.pdf) for print commands (GS, K, W, etc.). Commands have to be sent as single bytes after GS command before each one. For example: `GS` -> `set height command (K)` -> `height value`. Barcode is represented as POJO object in the app. 
