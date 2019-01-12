# Image Utils
Minecraft forge mod that does exactly as it name indicates. Help you with images. Mainly uploading screenshots.

Latest version can be found on [CurseForge](https://minecraft.curseforge.com/projects/image-utils/files)

Changelog can be found  [here](https://github.com/DarkEyeDragon/ImageUtils/blob/1.12.2/changelog.md)

Feel free to join the discord  [here](https://discord.gg/p9YrN53). This is mainly for notifications regarding updates and commits.
#### Default keys:
- F2 Save screenshot (and upload if the override setting is enabled)
- F4 Upload screenshot
- F6 Take partial screenshot.
- F7 Open local screenshot GUI
#### Features:
- View images in-game.
- Direct upload to discord trough discord webhook.
- Direct upload to [Imgur](https://imgur.com/).
- Copy link to clipboard.
- Copy image to clipboard.
- Open in browser.
- Upload to custom image servers using the ShareX config files (more information below).
- Post images directly in a discord server.

#### Planned features:
- Filters
- Overlays/watermarks

#### Screenshots
https://minecraft.curseforge.com/projects/image-utils/images
(will add some directly here later)
#### Changing settings
- you can change your settings by going to mods on the main menu or by going to mod options in the in-game menu.
## Uploading to a custom server
To upload to a custom image server you'll need to enable `use custom server` otherwise it'll just use Imgur as uploader.

after you've enabled `use custom server` you'll see an input box where you can set the upload script. There you need to fill in the name of the script (with extension).
an example could be `darkeyedragon-me.json` or `darkeyedragon-me.sxcu` (.sxcu is the extension of [sharex's](https://getsharex.com) config files).
**Note:** the extension on it's own doesn't really matter. It just has to be in the right format.

#### Config Example
```
{
    "RequestURL": "https://darkeyedragon.is-a-virg.in/upload",
    "FileFormName": "image",
    "URL": "$json:url$",
    "DeletionURL": "$json:del_url$",
	"Arguments":{
		"collection":"some collection string",
		"collection_token":"some collection token"
	}
}
```
| Key        | Description           | Required  |
|---|-------------| :-----:|
| `RequestURL`      | The url to upload the file to | **yes** |
| `FileFormName`      | The name of the image that will be sent. This can be renamed by the upload server to anything. If not provided, default is `image`     |   **no** |
| `URL` | The link to the image that will be shown in-game. add `$json:theKey$` if your server responds with a json file. Otherwise you can remove this key.    |    **no** |
| `DeletionURL` | The deletion link that will be returned. add `$json:theDeletionKey$` if your server responds with a json file. Otherwise you can remove this key.    |    **no** |
| `Arguments` | Additional information that needs to be given. Username, password, secret keys, etc. You can remove this if not needed (Highly recommended to use some form of validation though).    |    **no** |

A tutorial of how to make your own file can be found [here](https://getsharex.com/docs/custom-uploader)
**Note:** Keys specific to shareX are not needed. Example being the `DestinationType`.
**Note:** Not all keys are supported yet. The ones shown in the example will work. But not guarantee is given for the other ones.
more support will be added later.

A good custom uploader is [https://sxcu.net](https://sxcu.net) (the example is for their services)
you can also download their scripts from here: https://sxcu.net/domains and just drop them in your uploader files

### Adding config files
To add a config file you'll need to go to `%appdata%/config/imageutils/uploaders`
and drop your file in there. (If you added a file after you launched the game you can go to the mod's settings and set "reload uploaders" to true and it will reload all you upload scripts.)

#### Config settings explained
* Copy to clipboard: copy the image link to your clipboard as soon as the image is uploaded.\n
* Override default key: Override the default screenshot key (F2). This will allow you to save and upload images.\n
* Use custom server: If set to false it will upload images using Imgur. if set to true it will use the selected upload script from "Name of the script"\n
* Reload uploaders: When set to true it will reload your configs as soon as you close the config window. (will always go back to false after).
* Name of the script: the name of the script you'd like to use to upload images (only works when "Use custom server" is set to true).

Example:
![Change settings](https://darkeyedragon.me/images/tut2.png "change settings")

If you have additional questions you can always join Codevision's discord [here](https://discord.gg/yy8jwdS).
Found a bug? Don't forget to report it under `issues`!!