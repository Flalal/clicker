CREATE USER strapi_user WITH encrypted password 'password';
GRANT ALL PRIVILEGES ON database clicker_db to strapi_user;