# TPFINAL
#  Système de Gestion d'Événements Distribué

##  Description du Projet

Ce projet implémente un système complet de gestion d'événements en utilisant les concepts avancés de la Programmation Orientée Objet (POO). Il s'agit du **TP3 - POO** .

###  Objectifs Pédagogiques

- **Héritage et Polymorphisme** : Classes abstraites et concrètes
- **Design Patterns** : Observer, Singleton, Factory, Strategy
- **Gestion d'exceptions** : Exceptions personnalisées
- **Collections génériques** : Utilisation avancée des collections Java
- **Sérialisation** : JSON/XML avec Jackson et JAXB
- **Programmation asynchrone** : CompletableFuture
- **Tests unitaires** : JUnit 5 avec couverture > 70%

##  Architecture du Système

### Diagramme de Classes (UML)

```
┌─────────────────┐
│   <<abstract>>  │
│    Evenement    │
├─────────────────┤
│ - id: String    │
│ - nom: String   │
│ - date: LocalDT │
│ - lieu: String  │
│ - capaciteMax   │
│ - participants  │
├─────────────────┤
│ + ajouterPart() │
│ + annuler()     │
│ + afficherDet() │
└─────────┬───────┘
          │
    ┌─────┴─────┐
    │           │
┌───▼────┐  ┌───▼────┐
│Confér. │  │Concert │
│        │  │        │
│- theme │  │-artiste│
│- inter │  │- genre │
└────────┘  └────────┘

┌─────────────────┐    ┌─────────────────┐
│   Participant   │    │ NotificationSvc │
├─────────────────┤    │   <<interface>> │
│ - id: String    │    ├─────────────────┤
│ - nom: String   │    │ + envoyer()     │
│ - email: String │    └─────────────────┘
└─────────┬───────┘            ▲
          │                    │
      ┌───▼────┐               │
      │Organis.│      ┌────────┴────────┐
      │        │      │EmailNotifService│
      │- evnts │      └─────────────────┘
      └────────┘

┌─────────────────┐
│ GestionEvnts    │
│  <<Singleton>>  │
├─────────────────┤
│ - evenements    │
│ - instance      │
├─────────────────┤
│ + getInstance() │
│ + ajouter()     │
│ + rechercher()  │
└─────────────────┘
```

##  Structure du Projet

```
src/
├── main/java/
│   ├── model/                    # Classes métier
│   │   ├── Evenement.java       # Classe abstraite de base
│   │   ├── Conference.java      # Événement spécialisé
│   │   ├── Concert.java         # Événement spécialisé
│   │   ├── Participant.java     # Utilisateurs du système
│   │   ├── Organisateur.java    # Spécialisation de Participant
│   │   └── Intervenant.java     # Intervenants des conférences
│   │
│   ├── service/                  # Services et logique métier
│   │   ├── NotificationService.java      # Interface de notification
│   │   ├── EmailNotificationService.java # Implémentation concrète
│   │   ├── GestionEvenements.java        # Singleton de gestion
│   │   └── SerializationService.java     # Sérialisation JSON/XML
│   │
│   ├── observer/                 # Pattern Observer
│   │   ├── EvenementObservable.java      # Interface observable
│   │   ├── ParticipantObserver.java      # Interface observateur
│   │   ├── EvenementObservableImpl.java  # Implémentation
│   │   ├── ConferenceObservable.java     # Conférence observable
│   │   ├── ConcertObservable.java        # Concert observable
│   │   └── ParticipantObserverImpl.java  # Participant observateur
│   │
│   ├── factory/                  # Pattern Factory
│   │   └── EvenementFactory.java         # Factory pour événements
│   │
│   ├── exception/               # Exceptions personnalisées
│   │   ├── CapaciteMaxAtteinteException.java
│   │   └── EvenementDejaExistantException.java
│   │
│   ├── utils/                   # Utilitaires
│   │   └── EvenementAnalyzer.java        # Analyse avec Streams
│   │
│   └── GestionEvenementsDemo.java        # Classe principale de démonstration
│
└── test/java/                   # Tests unitaires
    ├── ConferenceTest.java
    ├── ConcertTest.java
    ├── GestionEvenementsTest.java
    ├── ObserverPatternTest.java
    ├── SerializationTest.java
    ├── EvenementFactoryTest.java
    ├── PerformanceTest.java
    └── GestionEvenementsTestSuite.java
```

## Installation et Utilisation

### Prérequis

- **Java 11+** (OpenJDK ou Oracle JDK)
- **Maven 3.6+**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code avec extensions Java)

### Installation

1. **Cloner le repository**
   ```bash
   git clone [url-du-repo]
   cd gestion-evenements
   ```

2. **Compiler le projet**
   ```bash
   mvn clean compile
   ```

3. **Exécuter les tests**
   ```bash
   mvn test
   ```

4. **Générer le rapport de couverture**
   ```bash
   mvn clean test jacoco:report
   ```
   Le rapport sera disponible dans `target/site/jacoco/index.html`

5. **Exécuter la démonstration**
   ```bash
   mvn exec:java -Dexec.mainClass="GestionEvenementsDemo"
   ```

6. **Créer un JAR exécutable**
   ```bash
   mvn clean package
   java -jar target/gestion-evenements-1.0.0.jar
   ```

##  Fonctionnalités Implémentées

###  Design Patterns

1. **Singleton** - `GestionEvenements`
   - Instance unique pour la gestion centralisée
   - Thread-safe avec synchronisation

2. **Observer** - Système de notifications
   - Participants notifiés automatiquement
   - Événements modifiés ou annulés

3. **Factory** - `EvenementFactory`
   - Création d'événements selon le type
   - Support des événements observables

4. **Strategy** - Services de notification
   - Interface commune, implémentations multiples
   - Injection de dépendance

### Concepts POO Avancés

1. **Héritage et Polymorphisme**
   - Classe abstraite `Evenement`
   - Spécialisations `Conference` et `Concert`
   - Méthodes abstraites et concrètes

2. **Interfaces et Composition**
   - `NotificationService` pour la stratégie
   - `EvenementObservable` et `ParticipantObserver`

3. **Exceptions Personnalisées**
   - `CapaciteMaxAtteinteException`
   - `EvenementDejaExistantException`
   - Gestion robuste des erreurs

###  Collections et Streams

```java
// Exemples d'utilisation des Streams
List<Evenement> evenementsFuturs = evenements.stream()
    .filter(e -> e.getDate().isAfter(LocalDateTime.now()))
    .sorted(Comparator.comparing(Evenement::getDate))
    .collect(Collectors.toList());

Map<String, List<Evenement>> parType = evenements.stream()
    .collect(Collectors.groupingBy(e -> e.getClass().getSimpleName()));
```

###  Programmation Asynchrone

```java
// Notifications asynchrones avec CompletableFuture
CompletableFuture<Void> future = emailService.envoyerNotificationAsync(
    participant, "Rappel: Votre événement commence demain !"
);
```

###  Sérialisation JSON/XML

```java
// Sauvegarde et chargement automatiques
serializationService.sauvegarderEvenementsJSON(evenements, "evenements.json");
List<Evenement> evenements = serializationService.chargerEvenementsJSON("evenements.json");
```

## Tests et Qualité

### Couverture de Tests

Le projet atteint **>70% de couverture de code** avec des tests pour :

- **Tests unitaires** de toutes les classes métier
- **Tests d'intégration** des design patterns
- **Tests de performance** avec timeouts
- **Tests de sérialisation** JSON/XML
- **Tests d'exceptions** personnalisées

### Exécution des Tests

```bash
# Tous les tests
mvn test

# Tests spécifiques
mvn test -Dtest=ConferenceTest
mvn test -Dtest=GestionEvenementsTestSuite

# Tests avec rapport de couverture
mvn clean test jacoco:report
```

### Métriques de Qualité

- **Couverture de code** : >70% (JaCoCo)
- **Tests unitaires** : 50+ tests
- **Performance** : <1s pour 1000 événements
- **Documentation** : JavaDoc complet

##  Exemples d'Utilisation

### Création d'Événements

```java
// Utilisation de la Factory
Conference conference = (Conference) EvenementFactory.creerEvenement(
    EvenementFactory.TypeEvenement.CONFERENCE,
    "CONF001", "IA 2025", LocalDateTime.of(2025, 6, 15, 14, 0),
    "Campus ENSPY", 100, "Intelligence Artificielle"
);

// Gestion centralisée
GestionEvenements gestion = GestionEvenements.getInstance();
gestion.ajouterEvenement(conference);
```

### Pattern Observer en Action

```java
// Événement observable
ConferenceObservable confObs = new ConferenceObservable(...);

// Participant observateur
ParticipantObserverImpl participant = new ParticipantObserverImpl(...);

// Inscription automatique + notification
confObs.ajouterParticipant(participant);
// → " Notification pour Alice: Vous êtes inscrit à..."

// Modification + notification automatique
confObs.modifierEvenement("Nouveau nom", nouvelleDage, nouveauLieu);
// → " Notification pour Alice: L'événement a été modifié..."
```

### Recherches Avancées avec Streams

```java
// Recherche multi-critères
List<Evenement> resultats = gestion.obtenirTousLesEvenements().stream()
    .filter(e -> e.getDate().isAfter(LocalDateTime.now()))
    .filter(e -> e.getLieu().contains("Campus"))
    .filter(e -> e.getNombreParticipants() < e.getCapaciteMax())
    .sorted(Comparator.comparing(Evenement::getDate))
    .limit(10)
    .collect(Collectors.toList());
```

##  Configuration et Personnalisation

### Services de Notification

```java
// Changer le service de notification
EmailNotificationService emailService = new EmailNotificationService();
gestion.setNotificationService(emailService);

// Ou implémenter un nouveau service
public class SMSNotificationService implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        // Implémentation SMS
    }
}
```

### Sérialisation Personnalisée

```java
// Configuration Jackson pour JSON
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JavaTimeModule());
mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
```

##  Analyse et Métriques

### Statistiques du Système

```java
// Affichage automatique des statistiques
gestion.afficherStatistiques();
```

Sortie exemple :
```
=== STATISTIQUES ===
Nombre total d'événements: 15
Concerts: 8
Conférences: 7
Total participants: 342
Taux d'occupation moyen: 68.4%
```

### Analyse Avancée avec EvenementAnalyzer

```java
EvenementAnalyzer.analyserEvenements(gestion.obtenirTousLesEvenements());
```

##  Gestion d'Erreurs

### Exceptions Métier

```java
try {
    conference.ajouterParticipant(participant);
} catch (CapaciteMaxAtteinteException e) {
    // Gérer le dépassement de capacité
    notificationService.envoyerNotification(
        "Événement complet: " + e.getMessage()
    );
}

try {
    gestion.ajouterEvenement(evenement);
} catch (EvenementDejaExistantException e) {
    // Gérer le doublon
    System.err.println("Événement déjà existant: " + e.getMessage());
}
```

##  Performance et Optimisation

### Tests de Performance

- **1000 événements** ajoutés/supprimés en <1s
- **1000 participants** notifiés en <1s
- **Sérialisation** de gros volumes optimisée
- **Recherches** indexées par Map pour O(1)

### Programmation Asynchrone

```java
// Notifications en parallèle
List<CompletableFuture<Void>> futures = participants.stream()
    .map(p -> emailService.envoyerNotificationAsync(p, message))
    .collect(Collectors.toList());

CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
```

##  Contribution et Développement

### Standards de Code

- **Java 11+** avec features modernes
- **Conventions** Oracle Java Code Style
- **Documentation** JavaDoc complète
- **Tests** obligatoires pour nouveaux features

### Extensibilité

Le système est conçu pour être facilement extensible :

1. **Nouveaux types d'événements** : Hériter d'`Evenement`
2. **Nouveaux services** : Implémenter les interfaces
3. **Nouveaux observateurs** : Implémenter `ParticipantObserver`
4. **Nouveaux formats** : Étendre `SerializationService`

##  Références et Documentation

### Design Patterns Utilisés

- **Singleton** : Garantit une instance unique de `GestionEvenements`
- **Observer** : Notifications automatiques aux participants
- **Factory** : Création d'objets selon le type
- **Strategy** : Services de notification interchangeables

### Technologies Utilisées

- **Java 11** : Streams, Optional, LocalDateTime
- **JUnit 5** : Tests unitaires modernes
- **Jackson** : Sérialisation JSON haute performance
- **Maven** : Gestion de dépendances et build
- **JaCoCo** : Mesure de couverture de code

##  Objectifs Pédagogiques Atteints

###  Concepts POO Maîtrisés

- [x] **Héritage** multi-niveaux avec classe abstraite
- [x] **Polymorphisme** avec méthodes abstraites/concrètes
- [x] **Encapsulation** avec getters/setters appropriés
- [x] **Composition** vs héritage selon les besoins

###  Design Patterns Implémentés

- [x] **Observer** pour les notifications temps réel
- [x] **Singleton** thread-safe pour la gestion centralisée
- [x] **Factory** pour l'instanciation contrôlée
- [x] **Strategy** pour les services interchangeables

###  Techniques Avancées Java

- [x] **Collections génériques** avec type safety
- [x] **Streams et Lambdas** pour le traitement fonctionnel
- [x] **Exceptions personnalisées** pour la gestion d'erreurs
- [x] **Sérialisation** JSON/XML transparente
- [x] **Programmation asynchrone** avec CompletableFuture

###  Qualité et Tests

- [x] **Tests unitaires** exhaustifs (>70% couverture)
- [x] **Tests d'intégration** des patterns
- [x] **Tests de performance** avec métriques
- [x] **Documentation** complète et examples



##  Contact et Support

**Auteur** : TAPAMO STELLA LA FORTUNE
**Email** : stellatapamo90@icloud.com
**Date** :26 Mai 2025
**Projet** : TP#3 - POO - ENSPY

**Repository** : https://github.com/stlwesh/TPFINAL
**Documentation** : Ce README + JavaDoc générée
**Tests** : Rapport JaCoCo dans `target/site/jacoco/`

