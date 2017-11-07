package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String SOCIAL_MEDIA_URL_PREFIX = "https://";

    private static Boolean browserMode = true;
    private static final String FXML = "BrowserPanel.fxml";

    private Address lastAddress;
    private String lastUrl;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    @FXML
    private Label field;

    @FXML
    private Label value;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    //@@author seankwekjk
    private void loadSocialPage(ReadOnlyPerson pers) {
        loadPage(SOCIAL_MEDIA_URL_PREFIX + pers.getSocialMedia());
        bindSocial(pers);
        bindField("Social Media");
    }

    //@@author hymss
    /**
    * Loads the address of the contact select and corresponding google maps page.
    * @param pers
     */
    private void loadPersonPage(ReadOnlyPerson pers) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX
                + pers.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));
        bindAddress(pers);
        bindField("Address");
    }

    /**
    * Bind address to the BrowserPanel
     */
    private void bindAddress(ReadOnlyPerson pers) {
        value.textProperty().bind(Bindings.convert(pers.addressProperty()));
    }

    //@@author seankwekjk
    /**
     * Bind social media to the BrowserPanel
     */
    private void bindSocial(ReadOnlyPerson pers) {
        value.textProperty().bind(Bindings.convert(pers.socialProperty()));
    }

    /**
     * Bind type of field to the BrowserPanel
     */
    private void bindField(String type) {
        field.setText(type);
    }

    public static Boolean getBrowserMode() {
        return browserMode;
    }

    public static void setBrowserMode() {
        browserMode = !browserMode;
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }
    //@@author

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    //@@author seankwekjk
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (browserMode) {
            loadPersonPage(event.getNewSelection().person);
        }   else {
            loadSocialPage(event.getNewSelection().person);
        }
        lastAddress = event.getNewSelection().person.getAddress();
        lastUrl = event.getNewSelection().person.getSocialMedia();
    }
}
