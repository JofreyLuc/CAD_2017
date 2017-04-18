# Projet CAD - Bataille navale
Équipe 3 : Lanoix Alexis, Luc Jofrey, Sonrel Quentin

- Outils/frameworks :
  - Utilisation de Swing pour l'interface graphique
  - Build réalisé avec Gradle

- Lancement du projet :
  - Afin de lancer le projet, placez vous à la racine et, en ligne de commande, faites :
    gradlew run
    
  - Afin de lancer les tests du projet, placez vous à la racine et, en ligne de commande, faites :
    gradlew test

- Contenu :
  - Le dossier de conception dans "Dossier conception"
  - La structure du projet est une structure d'application Java classique (code et ressources dans "src") :
    - "main/java"      : les classes principales
    - "main/resources" : les ressources principales (images, etc.)
    - "test/java"      : les classes de test

- Détails de conception :
  - Pattern Strategy pour les époques et pour la technique de tir de l'ordinateur
