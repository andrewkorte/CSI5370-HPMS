FROM postgres:latest

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=csi5370
ENV POSTGRES_PASSWORD=mypassword
ENV POSTGRES_DB=HMPS

# Copy the initialization script to the container
COPY init.sql /docker-entrypoint-initdb.d/

# Expose the PostgreSQL default port
EXPOSE 5432