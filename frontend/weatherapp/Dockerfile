FROM node:20-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
# copies all the files 
COPY . . 
EXPOSE 5173
CMD [ "npm","run","dev","--","--host"]
