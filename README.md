# ScreenshotUploader
Minecraft forge mod

Press F4 (default key) to upload an image directly to imgur.

Current features:
- Direct upload to imgur
- Copy link to clipboard (configrable)

Aditionally you can override the default screenshot key to save a screenshot locally and upload it aswell.

Planned features:
- Add custom server support to upload to your own server
- Filters
- Overlays/watermarks

##Changing settings
- you can change your settings by going to mods on the main menu or by going to mod options in the in-game menu.

#Uploading to a custom server
Once you've gotten to mod settings there will be a "Server settings" button

##Client configuration
Link: link to the upload script
Post values: add POST data to your request for servers that require some form of authentication.
everything before `=` is considered as the `key` and everything after as `value`
you can get the `key` like this in PHP for example: `$_POST["key"]` and that would return the `value`
**NOTE:** `{image}` is required as this will send the image data. However you can choose whatever `key` you want.
**NOTE:** dont use spaces between ´=´ they will be added.
##Example:
```
image={image} //send post data with image as key and {image} as value
key=2DAC1D297E6E7E348E5CE1884A7FC //send post data with key as key and 2DAC1D297E6E7E348E5CE1884A7FC as value
```
if you are using some image hosting service make sure to look up what data you need to send.
If you use your own upload script you can pretty much do whatever you want.

##Server configuration PHP

**NOTE:** PLEASE for the love of your own sanity, USE VALIDATION or anyone could upload anything to your server
**NOTE:** I will assume you have at least basic knowlege about PHP.
If you are using the settings from the previous section your script *could* look like this:

```
<?php
/**
 * Created by PhpStorm.
 * User: DarkEyeDragon
 * Date: 4-4-2018
 * Time: 03:21
 */

$key = "2DAC1D297E6E7E348E5CE1884A7FC"; //this can be anything, just make sure its the same as the one you set in the client

if(isset($_FILES["image"])){ //check if there is a key named image
    if($_POST["key"] === $key) {
        $target_dir = "images/"; //directory to upload to (MUST EXIST!)
        $file_name = str_replace(".", "-", uniqid("img", false)); //get an unique id
        $target_file = $target_dir . basename($file_name . ".jpg"); //add the extention
        
        $fp = fopen('log.txt', 'a'); //create a file
        fwrite($fp, $_FILES["image"]); //write image data to the file to see if it comes in correctly

        if (move_uploaded_file($_FILES["screenshot"]["tmp_name"], $target_file)) {
            fwrite($fp, "The file " . basename($_FILES["screenshot"]["name"]) . " has been uploaded.");
            header("Location: http://$_SERVER[HTTP_HOST]/$target_file"); //send the url to the image back to the client
        } else {
            fwrite($fp, "Sorry, there was an error uploading your file.");
            http_response_code (500); //send an internal error back
        }
    }else{
      http_response_code (401); //send unauthorized error back
    }
}
else
    http_response_code (400); //send and invalid request error back
```
