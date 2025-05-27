package Interface;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.*;
import Services.*;
import Exception.*;
import modele.Concert;
import modele.Conference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.io.File;
import Services.GestionEvenement;
/**
 * 🎨 Application JavaFX principale pour la gestion d'événements
 */
public class GestionEvenementApp extends Application {

    // Services
    private GestionEvenement gestionEvenements;
    private SerializationService serializationService;

    // Composants UI principaux
    private TableView<Evenement> tableEvenements;
    private ObservableList<Evenement> evenementsData;
    private TextArea zoneNotifications;
    private PieChart graphiqueRepartition;
    private Label labelStatistiques;

    @Override
    public void start(Stage primaryStage) {
        // Initialiser les services
        gestionEvenements = GestionEvenement.getInstance();
        serializationService = new SerializationService();

        // Configuration de la fenêtre principale
        primaryStage.setTitle("🎪 Système de Gestion d'Événements - ENSPY TP#3");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);

        // Créer l'interface
        BorderPane root = creerInterfacePrincipale();

        // Style CSS
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Message de bienvenue
        afficherNotification("🎉 Application démarrée ! Bienvenue dans le système de gestion d'événements.");

        // Charger les données existantes
        chargerDonneesInitiales();
    }

    /**
     * 🏗️ Créer l'interface principale
     */
    private BorderPane creerInterfacePrincipale() {
        BorderPane root = new BorderPane();

        // En-tête
        root.setTop(creerBarreMenu());

        // Centre : TableView des événements
        root.setCenter(creerTableEvenements());

        // Droite : Panneau de contrôle
        root.setRight(creerPanneauControle());

        // Bas : Zone de notifications
        root.setBottom(creerZoneNotifications());

        return root;
    }

    /**
     * 📋 Créer la barre de menu
     */
    private MenuBar creerBarreMenu() {
        MenuBar menuBar = new MenuBar();

        // Menu Fichier
        Menu menuFichier = new Menu("📁 Fichier");

        MenuItem itemNouveau = new MenuItem("Nouveau Événement");
        itemNouveau.setOnAction(e -> ouvrirDialogNouvelEvenement());

        MenuItem itemImporter = new MenuItem("Importer JSON");
        itemImporter.setOnAction(e -> importerFichierJSON());

        MenuItem itemExporter = new MenuItem("Exporter JSON");
        itemExporter.setOnAction(e -> exporterFichierJSON());

        MenuItem itemQuitter = new MenuItem("Quitter");
        itemQuitter.setOnAction(e -> System.exit(0));

        menuFichier.getItems().addAll(itemNouveau, new SeparatorMenuItem(),
                itemImporter, itemExporter, new SeparatorMenuItem(), itemQuitter);

        // Menu Événements
        Menu menuEvenements = new Menu("🎪 Événements");

        MenuItem itemConference = new MenuItem("Nouvelle Conférence");
        itemConference.setOnAction(e -> creerConference());

        MenuItem itemConcert = new MenuItem("Nouveau Concert");
        itemConcert.setOnAction(e -> creerConcert());

        MenuItem itemSupprimer = new MenuItem("Supprimer Sélectionné");
        itemSupprimer.setOnAction(e -> supprimerEvenementSelectionne());

        menuEvenements.getItems().addAll(itemConference, itemConcert, new SeparatorMenuItem(), itemSupprimer);

        // Menu Participants
        Menu menuParticipants = new Menu("👥 Participants");

        MenuItem itemInscrire = new MenuItem("Inscrire Participant");
        itemInscrire.setOnAction(e -> inscrireParticipant());

        MenuItem itemVoirParticipants = new MenuItem("Voir Participants");
        itemVoirParticipants.setOnAction(e -> voirParticipants());

        menuParticipants.getItems().addAll(itemInscrire, itemVoirParticipants);

        // Menu Aide
        Menu menuAide = new Menu("❓ Aide");

        MenuItem itemAPropos = new MenuItem("À propos");
        itemAPropos.setOnAction(e -> afficherAPropos());

        menuAide.getItems().add(itemAPropos);

        menuBar.getMenus().addAll(menuFichier, menuEvenements, menuParticipants, menuAide);

        return menuBar;
    }

    /**
     * 📊 Créer la table des événements
     */
    private VBox creerTableEvenements() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        // Titre
        Label titre = new Label("📅 Liste des Événements");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titre.setTextFill(Color.DARKBLUE);

        // TableView
        tableEvenements = new TableView<>();
        evenementsData = FXCollections.observableArrayList();
        tableEvenements.setItems(evenementsData);

        // Colonnes
        TableColumn<Evenement, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        colId.setPrefWidth(80);

        TableColumn<Evenement, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        colNom.setPrefWidth(200);

        TableColumn<Evenement, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
        colType.setPrefWidth(100);

        TableColumn<Evenement, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        colDate.setPrefWidth(120);

        TableColumn<Evenement, String> colLieu = new TableColumn<>("Lieu");
        colLieu.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLieu()));
        colLieu.setPrefWidth(150);

        TableColumn<Evenement, String> colParticipants = new TableColumn<>("Participants");
        colParticipants.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getNombreParticipants() + "/" + cellData.getValue().getCapaciteMax()));
        colParticipants.setPrefWidth(100);

        tableEvenements.getColumns().addAll(colId, colNom, colType, colDate, colLieu, colParticipants);

        // Double-clic pour voir les détails
        tableEvenements.setRowFactory(tv -> {
            TableRow<Evenement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    voirDetailsEvenement(row.getItem());
                }
            });
            return row;
        });

        container.getChildren().addAll(titre, tableEvenements);
        VBox.setVgrow(tableEvenements, Priority.ALWAYS);

        return container;
    }

    /**
     * 🎛️ Créer le panneau de contrôle à droite
     */
    private VBox creerPanneauControle() {
        VBox panneau = new VBox(15);
        panneau.setPadding(new Insets(10));
        panneau.setPrefWidth(300);
        panneau.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");

        // Titre
        Label titre = new Label("🎛️ Panneau de Contrôle");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Boutons d'action
        VBox boutons = creerBoutonsAction();

        // Statistiques
        VBox stats = creerPanneauStatistiques();

        // Graphique
        VBox graphique = creerGraphiqueRepartition();

        panneau.getChildren().addAll(titre, boutons, stats, graphique);

        return panneau;
    }

    /**
     * 🔘 Créer les boutons d'action
     */
    private VBox creerBoutonsAction() {
        VBox container = new VBox(8);

        Button btnNouvelEvenement = new Button("➕ Nouvel Événement");
        btnNouvelEvenement.setPrefWidth(250);
        btnNouvelEvenement.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnNouvelEvenement.setOnAction(e -> ouvrirDialogNouvelEvenement());

        Button btnInscrireParticipant = new Button("👤 Inscrire Participant");
        btnInscrireParticipant.setPrefWidth(250);
        btnInscrireParticipant.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnInscrireParticipant.setOnAction(e -> inscrireParticipant());

        Button btnVoirDetails = new Button("🔍 Voir Détails");
        btnVoirDetails.setPrefWidth(250);
        btnVoirDetails.setOnAction(e -> {
            Evenement selectionne = tableEvenements.getSelectionModel().getSelectedItem();
            if (selectionne != null) {
                voirDetailsEvenement(selectionne);
            } else {
                afficherAlerte("Aucun événement sélectionné", "Veuillez sélectionner un événement dans la liste.");
            }
        });

        Button btnSupprimer = new Button("🗑️ Supprimer");
        btnSupprimer.setPrefWidth(250);
        btnSupprimer.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnSupprimer.setOnAction(e -> supprimerEvenementSelectionne());

        Button btnActualiser = new Button("🔄 Actualiser");
        btnActualiser.setPrefWidth(250);
        btnActualiser.setOnAction(e -> actualiserTableau());

        container.getChildren().addAll(btnNouvelEvenement, btnInscrireParticipant,
                btnVoirDetails, btnSupprimer, btnActualiser);

        return container;
    }

    /**
     * 📊 Créer le panneau de statistiques
     */
    private VBox creerPanneauStatistiques() {
        VBox container = new VBox(5);

        Label titreStats = new Label("📊 Statistiques");
        titreStats.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        labelStatistiques = new Label("Chargement...");
        labelStatistiques.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-border-color: #ddd;");

        container.getChildren().addAll(titreStats, labelStatistiques);

        return container;
    }

    /**
     * 📈 Créer le graphique de répartition
     */
    private VBox creerGraphiqueRepartition() {
        VBox container = new VBox(5);

        Label titreGraphique = new Label("📈 Répartition");
        titreGraphique.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        graphiqueRepartition = new PieChart();
        graphiqueRepartition.setPrefSize(250, 200);

        container.getChildren().addAll(titreGraphique, graphiqueRepartition);

        return container;
    }

    /**
     * 💬 Créer la zone de notifications
     */
    private VBox creerZoneNotifications() {
        VBox container = new VBox(5);
        container.setPadding(new Insets(10));
        container.setPrefHeight(120);

        Label titre = new Label("💬 Notifications");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        zoneNotifications = new TextArea();
        zoneNotifications.setEditable(false);
        zoneNotifications.setPrefRowCount(4);
        zoneNotifications.setStyle("-fx-control-inner-background: #ffffcc;");

        container.getChildren().addAll(titre, zoneNotifications);
        VBox.setVgrow(zoneNotifications, Priority.ALWAYS);

        return container;
    }

    /**
     * ➕ Ouvrir le dialog pour créer un nouvel événement
     */
    private void ouvrirDialogNouvelEvenement() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nouvel Événement");
        dialog.setHeaderText("Choisissez le type d'événement à créer");

        ButtonType btnConference = new ButtonType("📚 Conférence");
        ButtonType btnConcert = new ButtonType("🎵 Concert");
        ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(btnConference, btnConcert, btnAnnuler);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (result.get() == btnConference) {
                creerConference();
            } else if (result.get() == btnConcert) {
                creerConcert();
            }
        }
    }

    /**
     * 📚 Dialog pour créer une conférence
     */
    private void creerConference() {
        Dialog<Conference> dialog = new Dialog<>();
        dialog.setTitle("Nouvelle Conférence");
        dialog.setHeaderText("Créer une nouvelle conférence");

        // Boutons
        ButtonType btnCreer = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnCreer, ButtonType.CANCEL);

        // Formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("CONF001");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom de la conférence");
        TextField lieuField = new TextField();
        lieuField.setPromptText("Lieu");
        TextField capaciteField = new TextField();
        capaciteField.setPromptText("100");
        TextField themeField = new TextField();
        themeField.setPromptText("Thème de la conférence");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(java.time.LocalDate.now().plusDays(7));
        TextField heureField = new TextField();
        heureField.setPromptText("14:00");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Heure:"), 0, 3);
        grid.add(heureField, 1, 3);
        grid.add(new Label("Lieu:"), 0, 4);
        grid.add(lieuField, 1, 4);
        grid.add(new Label("Capacité:"), 0, 5);
        grid.add(capaciteField, 1, 5);
        grid.add(new Label("Thème:"), 0, 6);
        grid.add(themeField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Conversion du résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnCreer) {
                try {
                    String[] heureMinute = heureField.getText().split(":");
                    LocalDateTime dateTime = datePicker.getValue().atTime(
                            Integer.parseInt(heureMinute[0]),
                            Integer.parseInt(heureMinute[1])
                    );

                    return new Conference(
                            idField.getText(),
                            nomField.getText(),
                            dateTime,
                            lieuField.getText(),
                            Integer.parseInt(capaciteField.getText()),
                            themeField.getText()
                    );
                } catch (Exception e) {
                    afficherAlerte("Erreur de saisie", "Vérifiez vos données : " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Conference> result = dialog.showAndWait();

        result.ifPresent(conference -> {
            try {
                gestionEvenements.ajouterEvenement(conference);
                actualiserInterface();
                afficherNotification("✅ Conférence '" + conference.getNom() + "' créée avec succès !");
            } catch (EvenementDejaExistantException e) {
                afficherAlerte("Erreur", e.getMessage());
            }
        });
    }

    /**
     * 🎵 Dialog pour créer un concert
     */
    private void creerConcert() {
        Dialog<Concert> dialog = new Dialog<>();
        dialog.setTitle("Nouveau Concert");
        dialog.setHeaderText("Créer un nouveau concert");

        // Boutons
        ButtonType btnCreer = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnCreer, ButtonType.CANCEL);

        // Formulaire similaire mais pour Concert
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("CONC001");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du concert");
        TextField lieuField = new TextField();
        lieuField.setPromptText("Lieu");
        TextField capaciteField = new TextField();
        capaciteField.setPromptText("500");
        TextField artisteField = new TextField();
        artisteField.setPromptText("Nom de l'artiste");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre musical");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(java.time.LocalDate.now().plusDays(7));
        TextField heureField = new TextField();
        heureField.setPromptText("20:00");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Heure:"), 0, 3);
        grid.add(heureField, 1, 3);
        grid.add(new Label("Lieu:"), 0, 4);
        grid.add(lieuField, 1, 4);
        grid.add(new Label("Capacité:"), 0, 5);
        grid.add(capaciteField, 1, 5);
        grid.add(new Label("Artiste:"), 0, 6);
        grid.add(artisteField, 1, 6);
        grid.add(new Label("Genre:"), 0, 7);
        grid.add(genreField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Conversion du résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnCreer) {
                try {
                    String[] heureMinute = heureField.getText().split(":");
                    LocalDateTime dateTime = datePicker.getValue().atTime(
                            Integer.parseInt(heureMinute[0]),
                            Integer.parseInt(heureMinute[1])
                    );

                    return new Concert(
                            idField.getText(),
                            nomField.getText(),
                            dateTime,
                            lieuField.getText(),
                            Integer.parseInt(capaciteField.getText()),
                            artisteField.getText(),
                            genreField.getText()
                    );
                } catch (Exception e) {
                    afficherAlerte("Erreur de saisie", "Vérifiez vos données : " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Concert> result = dialog.showAndWait();

        result.ifPresent(concert -> {
            try {
                gestionEvenements.ajouterEvenement(concert);
                actualiserInterface();
                afficherNotification("✅ Concert '" + concert.getNom() + "' créé avec succès !");
            } catch (EvenementDejaExistantException e) {
                afficherAlerte("Erreur", e.getMessage());
            }
        });
    }

    /**
     * 👤 Dialog pour inscrire un participant
     */
    private void inscrireParticipant() {
        Evenement evenementSelectionne = tableEvenements.getSelectionModel().getSelectedItem();

        if (evenementSelectionne == null) {
            afficherAlerte("Aucun événement sélectionné",
                    "Veuillez d'abord sélectionner un événement dans la liste.");
            return;
        }

        Dialog<Participant> dialog = new Dialog<>();
        dialog.setTitle("Inscription Participant");
        dialog.setHeaderText("Inscrire un participant à : " + evenementSelectionne.getNom());

        ButtonType btnInscrire = new ButtonType("Inscrire", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnInscrire, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("P001");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du participant");
        TextField emailField = new TextField();
        emailField.setPromptText("email@example.com");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnInscrire) {
                return new Participant(idField.getText(), nomField.getText(), emailField.getText());
            }
            return null;
        });

        Optional<Participant> result = dialog.showAndWait();

        result.ifPresent(participant -> {
            try {
                evenementSelectionne.ajouterParticipant(participant);
                actualiserInterface();
                afficherNotification("✅ " + participant.getNom() + " inscrit à " + evenementSelectionne.getNom());
            } catch (CapaciteMaxAtteinteException e) {
                afficherAlerte("Capacité atteinte", e.getMessage());
            }
        });
    }

    /**
     * 🔍 Voir les détails d'un événement
     */
    private void voirDetailsEvenement(Evenement evenement) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Détails de l'événement");
        dialog.setHeaderText(evenement.getNom());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        // Informations de base
        content.getChildren().addAll(
                new Label("ID: " + evenement.getId()),
                new Label("Type: " + evenement.getClass().getSimpleName()),
                new Label("Date: " + evenement.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))),
                new Label("Lieu: " + evenement.getLieu()),
                new Label("Capacité: " + evenement.getNombreParticipants() + "/" + evenement.getCapaciteMax())
        );

        // Informations spécifiques
        if (evenement instanceof Conference) {
            Conference conf = (Conference) evenement;
            content.getChildren().add(new Label("Thème: " + conf.getTheme()));
        } else if (evenement instanceof Concert) {
            Concert concert = (Concert) evenement;
            content.getChildren().addAll(
                    new Label("Artiste: " + concert.getArtiste()),
                    new Label("Genre: " + concert.getGenreMusical())
            );
        }

        // Liste des participants
        if (!evenement.getParticipants().isEmpty()) {
            content.getChildren().add(new Label("\nParticipants:"));
            ListView<String> listeParticipants = new ListView<>();
            ObservableList<String> participants = FXCollections.observableArrayList();

            evenement.getParticipants().forEach(p ->
                    participants.add(p.getNom() + " (" + p.getEmail() + ")")
            );

            listeParticipants.setItems(participants);
            listeParticipants.setPrefHeight(150);
            content.getChildren().add(listeParticipants);
        }

        dialog.getDialogPane().setContent(content);
        dialog.showAndWait();
    }

    /**
     * 👥 Voir tous les participants
     */
    private void voirParticipants() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Tous les Participants");
        dialog.setHeaderText("Liste complète des participants");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        TableView<Participant> tableParticipants = new TableView<>();
        ObservableList<Participant> participantsData = FXCollections.observableArrayList();

        // Collecter tous les participants
        gestionEvenements.obtenirTousLesEvenements().forEach(evenement ->
                participantsData.addAll(evenement.getParticipants())
        );

        tableParticipants.setItems(participantsData);

        // Colonnes
        TableColumn<Participant, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));

        TableColumn<Participant, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));

        TableColumn<Participant, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));

        tableParticipants.getColumns().addAll(colId, colNom, colEmail);
        tableParticipants.setPrefSize(500, 300);

        dialog.getDialogPane().setContent(tableParticipants);
        dialog.showAndWait();
    }

    /**
     * 📥 Importer depuis un fichier JSON
     */
    private void importerFichierJSON() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer Événements");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers JSON", "*.json")
        );

        File fichier = fileChooser.showOpenDialog(null);

        if (fichier != null) {
            try {
                List<Evenement> evenements = serializationService.chargerEvenementsJSON(fichier.getAbsolutePath());

                int ajouts = 0;
                for (Evenement event : evenements) {
                    try {
                        gestionEvenements.ajouterEvenement(event);
                        ajouts++;
                    } catch (EvenementDejaExistantException e) {
                        // Ignorer les doublons
                    }
                }

                actualiserInterface();
                afficherNotification("📥 " + ajouts + " événements importés depuis " + fichier.getName());

            } catch (Exception e) {
                afficherAlerte("Erreur d'importation", "Impossible de lire le fichier : " + e.getMessage());
            }
        }
    }

    /**
     * 📤 Exporter vers un fichier JSON
     */
    private void exporterFichierJSON() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter Événements");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers JSON", "*.json")
        );
        fileChooser.setInitialFileName("evenements_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ".json");

        File fichier = fileChooser.showSaveDialog(null);

        if (fichier != null) {
            try {
                serializationService.sauvegarderEvenementsJSON(
                        gestionEvenements.obtenirTousLesEvenements(),
                        fichier.getAbsolutePath()
                );

                afficherNotification("📤 Événements exportés vers " + fichier.getName());

            } catch (Exception e) {
                afficherAlerte("Erreur d'exportation", "Impossible d'écrire le fichier : " + e.getMessage());
            }
        }
    }

    /**
     * 🗑️ Supprimer l'événement sélectionné
     */
    private void supprimerEvenementSelectionne() {
        Evenement selectionne = tableEvenements.getSelectionModel().getSelectedItem();

        if (selectionne == null) {
            afficherAlerte("Aucun événement sélectionné",
                    "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Supprimer l'événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer '" + selectionne.getNom() + "' ?");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            gestionEvenements.supprimerEvenement(selectionne.getId());
            actualiserInterface();
            afficherNotification("🗑️ Événement '" + selectionne.getNom() + "' supprimé");
        }
    }

    /**
     * ℹ️ Afficher la boîte À propos
     */
    private void afficherAPropos() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Système de Gestion d'Événements");
        alert.setContentText(
                "TP#3 - Programmation Orientée Objet\n" +
                        "ENSPY - 2025\n\n" +
                        "Fonctionnalités:\n" +
                        "• Gestion d'événements (Conférences, Concerts)\n" +
                        "• Inscription de participants\n" +
                        "• Sérialisation JSON\n" +
                        "• Interface graphique moderne\n" +
                        "• Design Patterns (Singleton, Observer, Factory)\n\n" +
                        "Développé avec JavaFX"
        );
        alert.showAndWait();
    }

    /**
     * 🔄 Actualiser l'interface complète
     */
    private void actualiserInterface() {
        actualiserTableau();
        actualiserStatistiques();
        actualiserGraphique();
    }

    /**
     * 🔄 Actualiser le tableau
     */
    private void actualiserTableau() {
        evenementsData.clear();
        evenementsData.addAll(gestionEvenements.obtenirTousLesEvenements());
    }

    /**
     * 📊 Actualiser les statistiques
     */
    private void actualiserStatistiques() {
        List<Evenement> evenements = gestionEvenements.obtenirTousLesEvenements();

        int totalEvenements = evenements.size();
        long conferences = evenements.stream().filter(e -> e instanceof Conference).count();
        long concerts = evenements.stream().filter(e -> e instanceof Concert).count();
        int totalParticipants = evenements.stream().mapToInt(Evenement::getNombreParticipants).sum();
        int capaciteTotale = evenements.stream().mapToInt(Evenement::getCapaciteMax).sum();

        double tauxOccupation = capaciteTotale > 0 ? (double) totalParticipants / capaciteTotale * 100 : 0;

        String stats = String.format(
                "Total événements: %d\n" +
                        "Conférences: %d\n" +
                        "Concerts: %d\n" +
                        "Participants: %d\n" +
                        "Taux occupation: %.1f%%",
                totalEvenements, conferences, concerts, totalParticipants, tauxOccupation
        );

        labelStatistiques.setText(stats);
    }

    /**
     * 📈 Actualiser le graphique
     */
    private void actualiserGraphique() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        List<Evenement> evenements = gestionEvenements.obtenirTousLesEvenements();

        long conferences = evenements.stream().filter(e -> e instanceof Conference).count();
        long concerts = evenements.stream().filter(e -> e instanceof Concert).count();

        if (conferences > 0) {
            pieChartData.add(new PieChart.Data("Conférences", conferences));
        }
        if (concerts > 0) {
            pieChartData.add(new PieChart.Data("Concerts", concerts));
        }

        graphiqueRepartition.setData(pieChartData);
    }

    /**
     * 💬 Afficher une notification
     */
    private void afficherNotification(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String notification = "[" + timestamp + "] " + message + "\n";
        zoneNotifications.appendText(notification);

        // Faire défiler vers le bas
        zoneNotifications.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * ⚠️ Afficher une alerte
     */
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * 📊 Charger des données initiales pour la démonstration
     */
    private void chargerDonneesInitiales() {
        try {
            // Créer quelques événements de démonstration
            Conference conf1 = new Conference("CONF001", "Intelligence Artificielle 2025",
                    LocalDateTime.of(2025, 6, 15, 14, 0), "Amphithéâtre ENSPY", 150, "IA et Machine Learning");

            Conference conf2 = new Conference("CONF002", "Blockchain & Web3",
                    LocalDateTime.of(2025, 7, 10, 9, 0), "Centre Innovation", 100, "Cryptomonnaies");

            Concert concert1 = new Concert("CONC001", "Jazz Night",
                    LocalDateTime.of(2025, 7, 20, 20, 0), "Palais des Congrès", 500, "Sarah Johnson Quartet", "Jazz");

            Concert concert2 = new Concert("CONC002", "Rock Festival",
                    LocalDateTime.of(2025, 8, 15, 18, 0), "Stade Municipal", 2000, "Electric Storm", "Rock");

            // Ajouter au système
            gestionEvenements.ajouterEvenement(conf1);
            gestionEvenements.ajouterEvenement(conf2);
            gestionEvenements.ajouterEvenement(concert1);
            gestionEvenements.ajouterEvenement(concert2);

            // Ajouter quelques participants
            Participant alice = new Participant("P001", "Alice Dupont", "alice@enspy.cm");
            Participant bob = new Participant("P002", "Bob Martin", "bob@enspy.cm");
            Participant charlie = new Participant("P003", "Charlie Bernard", "charlie@email.com");

            conf1.ajouterParticipant(alice);
            conf1.ajouterParticipant(bob);
            concert1.ajouterParticipant(alice);
            concert1.ajouterParticipant(charlie);

            actualiserInterface();
            afficherNotification("📊 Données de démonstration chargées !");

        } catch (Exception e) {
            afficherNotification("⚠️ Erreur lors du chargement des données de démonstration : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
