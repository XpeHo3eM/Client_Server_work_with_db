module xpeho3em.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.net.http;
    requires com.google.gson;

    opens xpeho3em.client to javafx.fxml;
    opens xpeho3em.client.model to javafx.base;
    exports xpeho3em.client;
}