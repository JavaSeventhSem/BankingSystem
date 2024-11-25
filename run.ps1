$BIN_PATH = "bin"
$MYSQL_CONNECTOR = "lib\mysql-connector-j-9.1.0.jar"

if (!(Test-Path $MYSQL_CONNECTOR)) {
    Write-Host "Error: MySQL connector not found at $MYSQL_CONNECTOR"
    Write-Host "Please ensure you have downloaded the MySQL connector and placed it in the lib folder."
    exit 1
}

try {
    java -cp "$BIN_PATH;$MYSQL_CONNECTOR" Main
} catch {
    Write-Host "Error running the program: $_"
}