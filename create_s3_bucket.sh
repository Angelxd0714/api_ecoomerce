#!/bin/bash

BUCKET_NAME="product-images"
REGION="us-east-1" # Cambia a tu región

# Verifica si el bucket ya existe
aws s3api head-bucket --bucket "$BUCKET_NAME" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "El bucket '$BUCKET_NAME' ya existe."
else
    echo "Creando el bucket '$BUCKET_NAME'..."
    ERROR=$(aws s3api create-bucket --bucket "$BUCKET_NAME" --region "$REGION"  2>&1)

    # Verificar si la creación fue exitosa
    if [ $? -eq 0 ]; then
        echo "Bucket '$BUCKET_NAME' creado exitosamente."
    else
        echo "Error al crear el bucket '$BUCKET_NAME':"
        echo "$ERROR" # Mostrar el error capturado
    fi
fi
