# Android-BluetoothPrinter-Demo

Demo project shows how to print custom barcodes using Zonerich AB-330M Bluetooth printer. I use [this manual's](http://www.mangohelp.com/wp-content/uploads/2010/10/AB-320M-AB-330M-en-qq.pdf) Chapter 11 for printer specific commands (GS, K, W, etc.). Commands have to be sent as single bytes after GS command before each one. For example: `GS` -> `set height command (K)` -> `height value` or `GS` -> `set barcode code system` -> `barcode length` -> `print barcode`. Barcode is represented as POJO object in the app. 
