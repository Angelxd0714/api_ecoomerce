# Usa la imagen oficial de RabbitMQ con la interfaz de gestión (management)
FROM rabbitmq:3-management

# Establece variables de entorno para credenciales predeterminadas
ENV RABBITMQ_DEFAULT_USER=admin
ENV RABBITMQ_DEFAULT_PASS=secret

# Expone los puertos necesarios
EXPOSE 5672  # AMQP (para clientes)
EXPOSE 15672 # Interfaz web de gestión

# Directorio de datos persistente
VOLUME /var/lib/rabbitmq

# Comando de inicio (opcional, ya está definido en la imagen base)
CMD ["rabbitmq-server"]