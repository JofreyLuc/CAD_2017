# Projet CAD - Bataille navale

Équipe 3 : Lanoix Alexis, Luc Jofrey, Sonrel Quentin

## Outils/frameworks

  - Utilisation de Swing pour l'interface graphique
  - Build réalisé avec Gradle

## Lancement du projet

  - Afin de lancer le projet, placez vous à la racine et faites :
    `gradlew run` ou `./gradlew run` (rendre `gradlew` exécutable avant si besoin)

  - Afin de lancer les tests du projet, placez vous à la racine et faites :
    `gradlew test` ou `./gradlew test` (rendre `gradlew` exécutable avant si besoin)

## Contenu

  - Le dossier de conception dans "Dossier conception"
  - La structure du projet est une structure d'application Java classique (code et ressources dans "src") :
    - "main/java"      : les classes principales
    - "main/resources" : les ressources principales (images, etc.)
    - "test/java"      : les classes de test

## Fonctionnalités

  - Interface graphique complète avec :
    - Menu principal
    - Ecran de chargement/sauvegarde de partie
    - Ecran de sélection des options du jeu
    - Ecran de jeu
    - Options en jeu
  - 2 époques sélectionnables :
    - Une avec les règles classiques de la bataille navale
    - Une autre où les bateaux disposent de moins de points de vie (comme demandé dans le sujet)
  - 3 stratégies de tirs (ordinateur) sélectionnables (avec changement possible en cours de partie) :
    - Tir entièrement aléatoire
    - Mode chasse/cible (voir détails en tooltip du bouton) avec tir aléatoire
    - Mode chasse/cible (voir détails en tooltip du bouton) avec tir en croix
  - Chargement/sauvegarde de partie (dans le fichier de son choix)

## Détails de conception

  - Pattern MVC pour la structure générale
  - Pattern Strategy pour les époques et pour la technique de tir de l'ordinateur

## Ajout de fonctionnalités au projet d'une autre équique

Équipe 4 : Parwany Aschmat, Mondy Flavien, Nosari Florent

Dépôt GitHub : https://github.com/nosari20/bataille_navale

Notre fork (sera fusionné) : https://github.com/Sudiukil/bataille_navale
