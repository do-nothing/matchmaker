@echo off
taskkill /f /im runapp.exe
if not exist %1% (
	git clone server:/home/lijun/repos/%1%.git
)
pushd %1%
if not "%2%" == "" (
	git pull
	git reset --hard %2%
)
runapp.exe
popd

exit