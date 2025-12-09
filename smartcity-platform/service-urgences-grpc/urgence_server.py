from concurrent import futures
import grpc
import uuid
import urgence_pb2
import urgence_pb2_grpc
from grpc_reflection.v1alpha import reflection   # LIGNE OBLIGATOIRE

# Stockage en mémoire
alert_status = {}

class UrgenceService(urgence_pb2_grpc.UrgenceServiceServicer):
    def CreateAlert(self, request, context):
        alert_id = str(uuid.uuid4())
        alert_status[alert_id] = {"type": request.type, "status": "EN_ATTENTE"}
        print(f"Alerte créée : {alert_id} → {request.type}")
        return urgence_pb2.AlertResponse(
            alertId=alert_id,
            message="Alerte reçue",
            estimatedTime=7
        )

    def GetAlertStatus(self, request, context):
        info = alert_status.get(request.alertId)
        if not info:
            return urgence_pb2.StatusResponse(status="INCONNUE")
        return urgence_pb2.StatusResponse(status=info["status"])

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    urgence_pb2_grpc.add_UrgenceServiceServicer_to_server(UrgenceService(), server)

    # ACTIVATION DE LA REFLECTION (SINON grpcurl REFUSE TOUT)
    SERVICE_NAMES = (
        urgence_pb2.DESCRIPTOR.services_by_name['UrgenceService'].full_name,
        reflection.SERVICE_NAME,
    )
    reflection.enable_server_reflection(SERVICE_NAMES, server)

    server.add_insecure_port('0.0.0.0:50051')
    print("gRPC Urgence → port 50051 (reflection ON)")
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    serve()