## Version 1.0.2
This version is experimental and should only be downloaded for testing. Hence why there isn't a download yet.
##### Changes
- Added 'options' to local screenshots which allow you to rename a file (more to be added).
- Re-arranged buttons on local screenshots
- Some internal changes to properly deal with the renaming.
- Made the Screenshot key (F2) save images of the main thread. Fixing the short lag spikes when taking them.

##### Bug fixes
- Fixed crashes related to empty screenshot folder.
- Fixed crashes related to non image files in screenshot folder (will now ignore those).

##### Known issues
- When saving a screenshot with the screenshot key(F2). A "screenshot canceled" message will pop up.
followed by a message "Screenshot saved as: <currentDate>_<currentTime>.png".
I had to cancel the screenshot event in order to make it async (so it doesn't lag). However doing this displays the "screenshot canceled" message.
I will PR a fix to Forge for this. But in the meantime you'll have to live with it. As i cannot fix it without any hacks.
## Version 1.0.1
Changes:
- Confirm gui now looks bit different.
- Local screenshots will now automatically select the first screenshots when loaded.

##### Bug fixes
- Confirm gui now shows correctly when in-game.

**Note:** First release was labeled as 1.2.0 in gradle. This has been changed to version 1.0.1 now. Version notifications in-game should not be affected by this.

## Version 1.0.0
- Initial release.