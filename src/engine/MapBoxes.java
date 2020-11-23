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

    public void updateMap() {
        pane.getChildren().clear();

        TextField mapPointEast = new TextField(" ");
        mapPointEast.setFont(Font.font("Arial", 2));
        mapPointEast.setPrefSize(prefWidth, prefHeight);

        TextField mapPointSouth = new TextField(" ");
        mapPointSouth.setFont(Font.font("Arial", 2));
        mapPointSouth.setPrefSize(prefWidth, prefHeight);

        TextField mapPointWest = new TextField(" ");
        mapPointWest.setFont(Font.font("Arial", 2));
        mapPointWest.setPrefSize(prefWidth, prefHeight);

        TextField mapPointNorth = new TextField(" ");
        mapPointNorth.setFont(Font.font("Arial", 2));
        mapPointNorth.setPrefSize(prefWidth, prefHeight);

        setCenterBox();
        setPlayerBox();

        if (location.getLocationToEast() != null) {
            mapPointEast.setLayoutX((halfOfPaneWidth - (prefWidth / 2)) + prefWidth);
            mapPointEast.setLayoutY(halfofPaneHeight - (prefHeight / 2));
            colorRedIfMonster(location.getLocationToEast(), mapPointEast);
            pane.getChildren().add(mapPointEast);
        }

        if (location.getLocationToSouth() != null) {
            mapPointSouth.setLayoutX(halfOfPaneWidth - (prefWidth / 2));
            mapPointSouth.setLayoutY((halfofPaneHeight - (prefHeight / 2)) + prefHeight);
            colorRedIfMonster(location.getLocationToSouth(), mapPointSouth);
            pane.getChildren().add(mapPointSouth);
        }

        if (location.getLocationToWest() != null) {
            mapPointWest.setLayoutX((halfOfPaneWidth - (prefWidth / 2)) - prefWidth);
            mapPointWest.setLayoutY(halfofPaneHeight - (prefHeight / 2));
            colorRedIfMonster(location.getLocationToWest(), mapPointWest);
            pane.getChildren().add(mapPointWest);
        }

        if (location.getLocationToNorth() != null) {
            mapPointNorth.setLayoutX(halfOfPaneWidth - (prefWidth / 2));
            mapPointNorth.setLayoutY((halfofPaneHeight - (prefHeight / 2)) - prefHeight);
            colorRedIfMonster(location.getLocationToNorth(), mapPointNorth);
            pane.getChildren().add(mapPointNorth);
        }
    }

//    public void checkSidesForLocation(TextField thisMapBox, Location thisLocation) {
//        thisMapBox.getLayoutX(); //width
//        thisMapBox.getLayoutY(); //height
//
//        if (thisLocation.getLocationToEast() != null) {
//
//        }
//    }

    public void setPlayerBox() {
        TextField playerPoint = new TextField(" ");
        playerPoint.setFont(Font.font("Arial", 2));
        playerPoint.setPrefSize((prefWidth / 2), (prefHeight / 2));
        playerPoint.setStyle("-fx-background-color: blue;");
        playerPoint.setLayoutX(halfOfPaneWidth - (prefWidth / 4));
        playerPoint.setLayoutY(halfofPaneHeight - (prefHeight / 4));
        pane.getChildren().add(playerPoint);
    }

    public void colorRedIfMonster(Location thisLocation, TextField mapPoint) {
        if (thisLocation.getMonsterLivingHere() != null) {
            mapPoint.setStyle("-fx-background-color: red;");
        }
    }

    public void setCenterBox() {
        TextField mapPoint = new TextField(" ");
        mapPoint.setFont(Font.font("Arial", 2));
        mapPoint.setPrefSize(prefWidth, prefHeight);
        mapPoint.setLayoutX(halfOfPaneWidth - (prefWidth / 2));
        mapPoint.setLayoutY(halfofPaneHeight - (prefHeight / 2));

        if (location.getMonsterLivingHere() != null) {
            mapPoint.setStyle("-fx-background-color: red;");
        }

        pane.getChildren().add(mapPoint);
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
}
