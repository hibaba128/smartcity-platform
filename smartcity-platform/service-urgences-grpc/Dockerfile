# Utilisation d'une image Python spécifique et légère pour la stabilité
FROM python:3.10-slim

# Définition du répertoire de travail dans le conteneur
WORKDIR /app

# Copie des fichiers de code et du fichier de dépendances
COPY requirements.txt .
COPY . .

# Installation des dépendances Python
RUN pip install --no-cache-dir -r requirements.txt

# Le service Urgences utilise le port 5000 (Flask par défaut)
EXPOSE 5000

# Commande pour démarrer l'application (supposant que le point d'entrée est 'app.py' ou similaire)
CMD ["python", "app.py"]