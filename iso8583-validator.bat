@ECHO OFF
setlocal EnableExtensions
setlocal EnableDelayedExpansion
java -jar iso8583-validator-0.0.1-SNAPSHOT.jar %1 %2
@ECHO ON