$MYSQL_CONNECTOR = "lib\mysql-connector-j-8.0.33.jar"

Write-Host "Compiling the Banking System..."


if (!(Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin"
}


Write-Host "Cleaning previous compilation..."
Remove-Item -Path "bin\*" -Recurse -ErrorAction SilentlyContinue

# Compile files in order
Write-Host "1. Compiling exceptions..."
javac -d bin src/exceptions/InvalidAgeException.java
javac -d bin src/exceptions/InvalidAmountException.java
javac -d bin src/exceptions/InsufficientBalanceException.java

Write-Host "2. Compiling utils..."
javac -d bin -cp bin src/utils/Config.java

Write-Host "3. Compiling models..."
javac -d bin -cp bin src/models/BankAccount.java

Write-Host "4. Compiling database..."
javac -d bin -cp "bin;$MYSQL_CONNECTOR" src/database/DatabaseConnection.java
javac -d bin -cp "bin;$MYSQL_CONNECTOR" src/database/BankDAO.java

Write-Host "5. Compiling Main..."
javac -d bin -cp "bin;$MYSQL_CONNECTOR" src/Main.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!"
} else {
    Write-Host "Compilation failed!"
}