# Define the path to your Java executable
$javaExec = "C:\Program Files\Java\jdk-18.0.2\bin\java.exe"

# Define the name of the Java class you want to run
# $className = "java Machine2.GetFile1"

# Run the Java class
Start-Process -FilePath $javaExec -ArgumentList "java Machine2.GetFile1"
Start-Process -FilePath $javaExec -ArgumentList "java Machine3.GetFile2"
Start-Process -FilePath $javaExec -ArgumentList "java Machine4.GetFile3"
# Start-Process -FilePath $javaExec -ArgumentList "java Machine2.GetFile1"

# powershell -NoExit