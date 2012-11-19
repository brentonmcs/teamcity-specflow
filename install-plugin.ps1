Write-Host "Stopping TC service"
Stop-Service teamcity

$pluginFolder = "C:\ProgramData\JetBrains\TeamCity\plugins"
Write-Host "Purging plugin folder"
Get-ChildItem $pluginFolder | Remove-Item -Recurse

Write-Host "Installing plugin"
Copy-Item ".\dist\specFlowPlugin.zip" $pluginFolder

$logFolder = "C:\TeamCity\logs"
Write-Host "Cleaning log folder"
Get-ChildItem $logFolder | Remove-Item

Write-Host "Starting TC service"
Start-Service teamcity