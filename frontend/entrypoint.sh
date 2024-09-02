#!/bin/sh

# Substituir variáveis de ambiente no arquivo de configuração
envsubst '${BACKEND_IP}' < /etc/nginx/templates/nginx.conf.template > /etc/nginx/conf.d/default.conf

# Executar o comando do container (nginx)
exec "$@"