break > .\sources.list
for /f %%A in ('forfiles /s /m *.java /c "cmd /c echo @relpath"') do echo %%~A >> .\sources.list

javac @sources.list @params.list
