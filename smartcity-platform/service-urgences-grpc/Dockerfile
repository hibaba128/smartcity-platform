FROM python:3.10-slim
WORKDIR /app
RUN pip install --no-cache-dir grpcio==1.76.0 grpcio-tools==1.76.0 protobuf==6.31.1
COPY . .
RUN pip install --no-cache-dir grpcio==1.76.0 grpcio-tools==1.76.0 protobuf==6.31.1 grpcio-reflection
EXPOSE 50051
# Debug : on voit bien le fichier
RUN ls -la /app

# LANCE LE BON NOM DE FICHIER (SANS "s" !)
CMD ["python", "urgence_server.py"]