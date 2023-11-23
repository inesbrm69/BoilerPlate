# BoilerPlate Postman

**Attention : Ne pas oublier de mettre le token à chaque requêtes (l'insérer dans Authorization)**

### Pour allumer le serveur MongoDB :
(Si vous avez aucun problème avec le démarrage de MongoDB Compass vous n'êtes pas concernés par cette partie)
```
    cd C:\Program Files\MongoDB\Server\7.0\bin
```
````
    .\mongod.exe --dbpath .\data
````
### Les fichiers de données de la base de données (MongoDB) se situe dans le dossier
````
    resources/mongodb/todolist
````
### Pour créer un utilisateur
l'URL : http://localhost:8080/api/v1/auth/register
````
{
    "firstname" : "Julien",
    "lastname" : "Porte",
    "email" : "julienporte@email.com",
    "password" : "123",
    "roles" : "ADMIN"
}
````
### Pour la connexion d'un utilisateur et retourne un token
l'URL : http://localhost:8080/api/v1/auth/authenticate
```
{
    "email" : "julienporte@email.com",
    "password" : "123"
}
```
### Pour la liste de tous les articles 
L'URL : http://localhost:8080/api/v1/auth/articles

### Pour trouver un article avec son id
l'URL : http://localhost:8080/api/v1/auth/articles/{id_que_vous_voulez_tester}

### Pour trouver l'article avec son id et son nom
l'URL : http://localhost:8080/api/v1/auth/articles/{id_souhaité}/{l'article_de_votre_choix}

### Créer un article
**Requête POST**

l'URL : http://localhost:8080/api/v1/auth/articles
````
{
    "name": "Téléphone",
    "price" : 900
}
````
### Mettre à jour un article
**Requête PUT**

l'URL : http://localhost:8080/api/v1/auth/articles/update/{id_article}
````
{
        "name": "Sac",
        "price": 250
}
````
### Supprimer un article 
**Requête DELETE**

l'URL : http://localhost:8080/api/v1/auth/articles/delete/2