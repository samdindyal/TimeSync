rm *.class
javac TimeSyncServer.java
jar cfm TimeSyncServer.jar Server/Manifest TimeSyncServer.class TimeSyncServerUI.class TimeSyncServerRuntime.class TimeSyncLibrary.class ColourScheme.class DateTimePanel.class res 
rm *.class
