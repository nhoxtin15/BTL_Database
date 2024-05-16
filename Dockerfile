# Use the official MySQL 8.0 image from Docker Hub
FROM mysql:8.0

# Environment variables for MySQL root password
ENV MYSQL_ROOT_PASSWORD=nhoxtin1
ENV MYSQL_DATABASE=hotpot
# Copy SQL scripts to initialize database
COPY ./SqlStatment/hocdatabaselao.sql /docker-entrypoint-initdb.d/
