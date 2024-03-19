module GongQianyun_Source_Code {
    opens dms.game.background;
    opens dms.game.food;
    opens dms.game.music;
    opens dms.game.objects;
    opens dms.game.snake;
    opens dms.controller to javafx.fxml;

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jlayer;
    requires junit;
    requires javafx.swing;

    exports dms.game;
    exports dms.controller;
    exports dms.scene;
    opens dms.scene to javafx.fxml;
    exports dms;
    opens dms to javafx.fxml;

}