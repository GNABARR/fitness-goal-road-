# Fitness Goal Road

## Objectif
Fitness Goal Road est une application qui accompagne les athlètes dans l’atteinte de leurs objectifs fitness.
Elle permet notamment :
- le suivi du profil et des statistiques (BMI, BMR/TDEE),
- le suivi et la recommandation d’entraînements,
- le calcul des apports nutritionnels journaliers (calories et macronutriments) adaptés à l’objectif.

---

## Work Items par membre

### GNABAR ISMAIL — Calcul des apports journaliers (Nutrition)

     - je décris ici le contenus de chaque branch ( user story ) fait dans la release R4 de rattrapage :

     - us1-donnees-primaires : Création de l'entité Ingredient et de son repository avec un chargeur automatique qui peuple la table au démarrage.

     - us2-algorithmes : Mise en place du service qui calcule la quantité d'un ensemble ingrédients pour atteindre les macros cibles, chaque un seul au debus et une correction et équilibrage en cas de dépassement.

     - us3-frontend-travail : Création de l'interface qui envoie la demande de plan alimentaire et affiche la réponse calculée par le backend.

     - us4-login-signin : Ajout de l'authentification (full stack), liaison du compte au plan nutritionnel, et sauvegarde/affichage de l'historique nutritionnel du user. ( la us été pas fait proprement avant )

### MOHAMED EL GHAZRANI — Entraînements & Recommandation
- **Mock (2)** : création des mocks des athlètes et des séances d'entraînement *(done)*
- **Algorithme (3)** : conception et implémentation de l’algorithme de calcul des calories brûlées *(done)*
- **GUI (3)** : interface pour consulter le catalogue complet des exercices (filtre : catégorie / difficulté / durée)
- **Algorithme (3)** : conception de l’algorithme de recommandation d’entraînement *(done)*
- **Test (1)** : tests de l’algorithme de recommandation *(done)*
- **GUI (3)** : interface d'enregistrement des séances (ajout dynamique d’exercices, durée, intensité, objectifs)
- **Algorithme (3)** : conception de l’algorithme de filtrage dynamique (objectifs, disponibilité…) *(done)*
- **Test (1)** : tests de l’algorithme de filtrage dynamique *(done)*
- **GUI (3)** : interface historique des séances avec visualisation graphique *(done)*

---

### MARZOUK Aymane — Compte athlète & Statistiques
- **IHM (2)** : gestion compte athlète — affichage
- **GUI (3)** : gestion compte athlète — modification
- **IHM (2)** : visualisation statistiques — interface liste
- **GUI (3)** : visualisation statistiques — graphiques
- **GUI (2)** : visualisation statistiques — historique
- **IHM (2)** : calcul BMR/TDEE — saisie données
- **Algorithme (3)** : calcul BMR/TDEE — algorithme
- **Algorithme (2)** : calcul BMI — saisie et calcul
- **Algorithme (2)** : calcul BMI — interprétation avancée