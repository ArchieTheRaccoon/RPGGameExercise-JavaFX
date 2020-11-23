package engine;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class MapBoxes {
    Location location;
    Pane pane;
    private final int prefWidth = 30;
    private final int prefHeight = 30;
    private final double halfOfPaneWidth = 147.5;
    private final double halfofPaneHeight = 50;
    private TextField centerField;

    public void updateMap() {
        pane.getChildren().clear();
        pane.getChildren().removeAll();

        setCenterBox();
        checkAllDirections(centerField, getLocation());
        setPlayerBox();
    }

    public void checkEast(TextField thisMapBox, Location thisLocation) {
        if (thisLocation != null) {
            if (thisLocation.getLocationToEast() != null) {
                if (thisLocation.getLocationToEast().isPlayerHasBeenHere()) {
                    TextField mapPointEast = new TextField(" ");
                    mapPointEast.setFont(Font.font("Arial", 2));
                    mapPointEast.setPrefSize(prefWidth, prefHeight);
                    mapPointEast.setLayoutX(thisMapBox.getLayoutX() + prefWidth);
                    mapPointEast.setLayoutY(thisMapBox.getLayoutY());
                    pane.getChildren().add(mapPointEast);
                    checkWest(mapPointEast, thisLocation.getLocationToWest());
                    checkNorth(mapPointEast, thisLocation.getLocationToNorth());
                    checkSouth(mapPointEast, thisLocation.getLocationToSouth());
                }
            }
        }
    }

    public void checkSouth(TextField thisMapBox, Location thisLocation) {
        if (thisLocation != null) {
            if (thisLocation.getLocationToSouth() != null) {
                if (thisLocation.getLocationToSouth().isPlayerHasBeenHere()) {
                    TextField mapPointSouth = new TextField(" ");
                    mapPointSouth.setFont(Font.font("Arial", 2));
                    mapPointSouth.setPrefSize(prefWidth, prefHeight);
                    mapPointSouth.setLayoutX(thisMapBox.getLayoutX());
                    mapPointSouth.setLayoutY(thisMapBox.getLayoutY() + prefHeight);
                    pane.getChildren().add(mapPointSouth);
                    checkNorth(mapPointSouth, thisLocation.getLocationToNorth());
                    checkEast(mapPointSouth, thisLocation.getLocationToEast());
                    checkWest(mapPointSouth, thisLocation.getLocationToWest());
                }
            }
        }
    }

    public void checkWest(TextField thisMapBox, Location thisLocation) {
        if (thisLocation != null) {
            if (thisLocation.getLocationToWest() != null) {
                if (thisLocation.getLocationToWest().isPlayerHasBeenHere()) {
                    TextField mapPointWest = new TextField(" ");
                    mapPointWest.setFont(Font.font("Arial", 2));
                    mapPointWest.setPrefSize(prefWidth, prefHeight);
                    mapPointWest.setLayoutX(thisMapBox.getLayoutX() - prefWidth);
                    mapPointWest.setLayoutY(thisMapBox.getLayoutY());
                    pane.getChildren().add(mapPointWest);
                    checkSouth(mapPointWest, thisLocation.getLocationToSouth());
                    checkEast(mapPointWest, thisLocation.getLocationToEast());
                    checkNorth(mapPointWest, thisLocation.getLocationToNorth());
                }
            }
        }
    }

    public void checkNorth(TextField thisMapBox, Location thisLocation) {
        if (thisLocation != null) {
            if (thisLocation.getLocationToNorth() != null) {
                if (thisLocation.getLocationToNorth().isPlayerHasBeenHere()) {
                    TextField mapPointNorth = new TextField(" ");
                    mapPointNorth.setFont(Font.font("Arial", 2));
                    mapPointNorth.setPrefSize(prefWidth, prefHeight);
                    mapPointNorth.setLayoutX(thisMapBox.getLayoutX());
                    mapPointNorth.setLayoutY(thisMapBox.getLayoutY() - prefHeight);
                    pane.getChildren().add(mapPointNorth);
                    checkEast(mapPointNorth, thisLocation.getLocationToEast());
                    checkSouth(mapPointNorth, thisLocation.getLocationToSouth());
                    checkWest(mapPointNorth, thisLocation.getLocationToWest());
                }
            }
        }
    }

    public void setPlayerBox() {
        TextField playerPoint = new TextField(" ");
        playerPoint.setFont(Font.font("Arial", 2));
        playerPoint.setPrefSize((prefWidth / 2), (prefHeight / 2));
        playerPoint.setStyle("-fx-background-color: blue;");
        playerPoint.setLayoutX(halfOfPaneWidth - (prefWidth / 4));
        playerPoint.setLayoutY(halfofPaneHeight - (prefHeight / 4));
        pane.getChildren().add(playerPoint);
    }

    public void checkAllDirections(TextField thisMapBox, Location thisLocation) {
        checkSouth(thisMapBox, thisLocation);
        checkNorth(thisMapBox, thisLocation);
        checkWest(thisMapBox, thisLocation);
        checkEast(thisMapBox, thisLocation);
    }

    public void colorRedIfMonster(Location thisLocation, TextField mapPoint) {
        if (thisLocation.getMonsterLivingHere() != null) {
            mapPoint.setStyle("-fx-background-color: red;");
        }
    }

    public void setCenterBox() {
        centerField = new TextField(" ");
        centerField.setFont(Font.font("Arial", 2));
        centerField.setPrefSize(prefWidth, prefHeight);
        centerField.setLayoutX(halfOfPaneWidth - (prefWidth / 2));
        centerField.setLayoutY(halfofPaneHeight - (prefHeight / 2));

        colorRedIfMonster(location, centerField);

        pane.getChildren().add(centerField);
    }

    public MapBoxes(Location currentLocation, Pane currentPane) {
        location = currentLocation;
        pane = currentPane;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public TextField getCenterField() {
        return centerField;
    }

    public void setCenterField(TextField centerField) {
        this.centerField = centerField;
    }

    public void clearPane() {
        pane.getChildren().clear();
    }
}
