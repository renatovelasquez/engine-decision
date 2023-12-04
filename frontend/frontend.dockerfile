FROM node:20-alpine
RUN mkdir -p /app
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
RUN npm run build
CMD [ "npm", "start" ]