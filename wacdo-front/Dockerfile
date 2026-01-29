# Étape 1 : Build Angular
FROM node:20-alpine AS build

WORKDIR /app

# Copier les fichiers de dépendances
COPY package*.json ./

# Installer les dépendances
RUN npm install --legacy-peer-deps

# Copier le code source
COPY . .

# Build production Angular
RUN npm run build -- --output-path=dist/wacdo-front

# Étape 2 : Serve avec Nginx
FROM nginx:alpine

# Copier les fichiers buildés Angular dans le dossier nginx
COPY --from=build /app/dist/wacdo-front/browser/ /usr/share/nginx/html/

# Copier le fichier de config Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exposer ports HTTP et HTTPS si nécessaire
EXPOSE 80

# Lancer Nginx en foreground
ENTRYPOINT ["nginx", "-g", "daemon off;"]
