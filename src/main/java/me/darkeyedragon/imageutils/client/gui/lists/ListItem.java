package me.darkeyedragon.imageutils.client.gui.lists;

import me.darkeyedragon.imageutils.client.gui.components.Location;

public class ListItem {

    private String title;
    private String description;

    private Location startLocation;
    private Location endLocation;

    private boolean isSelected;

    public ListItem() {
    }

    public ListItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ListItem(String title, String description, Location startLocation, Location endLocation) {
        this.title = title;
        this.description = description;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Get the start location (top left).
     * @return {@link Location Location}
     */
    public Location getStartLocation() {
        return startLocation;
    }


    /**
     * Set the start location (top left).
     * @param startLocation {@link Location Location}
     */
    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }


    /**
     * Get the end location (bottom right).
     * @return {@link Location Location}
     */
    public Location getEndLocation() {
        return endLocation;
    }


    /**
     * @param endLocation {@link Location Location}
     */
    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }


    /**
     * @return true if selected in menu, otherwise false;
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * @param selected set to true to select the element
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}