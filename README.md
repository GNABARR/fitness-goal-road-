# Proto Sirius

Exemple de structure de projet full stack moderne.

## Stack technique

- **Database** : PostgreSQL (Docker)
- **Backend** : Java 17, Spring Boot, Maven, JPA
- **Frontend** : React, TypeScript, Vite, Tailwind CSS

## Architecture

```
proto-sirius/
├── backend/
│   └── src/main/java/com/protosirius/backend/
│       ├── controller/    # Points d'entrée REST
│       ├── service/       # Logique métier
│       ├── repository/    # Accès données (JPA)
│       └── entity/        # Entités persistées
└── frontend/
    └── src/
        ├── modules/       # Modules fonctionnels
        │   └── <module>/
        │       ├── views/       # Pages/écrans
        │       ├── components/  # Composants UI
        │       └── api/         # Appels backend
        ├── router.tsx     # Configuration des routes
        └── Navbar.tsx     # Navigation
```

## Modules inclus

- **hello** : Affichage et édition d'un message du jour (MOTD) persisté en base
- **time** : Affichage de l'heure courante

## Lancement

```bash
./start.sh
```

Ou manuellement :

```bash
# 1. Base de données
docker-compose up -d

# 2. Backend (port 3000)
cd backend && ./mvnw spring-boot:run

# 3. Frontend (port 8080)
cd frontend && npm install && npm run dev
```

## Licence

MIT
