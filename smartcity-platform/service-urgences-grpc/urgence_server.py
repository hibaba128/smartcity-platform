from concurrent import futures
import grpc
import uuid
import urgence_pb2
import urgence_pb2_grpc

# Stockage simple en mémoire
alert_status = {}

class UrgenceService(urgence_pb2_grpc.UrgenceServiceServicer):

    def CreateAlert(self, request, context):
        alert_id = str(uuid.uuid4())

        alert_status[alert_id] = {
            "type": request.type,
            "status": "EN_ATTENTE"
        }

        response = urgence_pb2.AlertResponse(
            alertId=alert_id,
            message="Alerte reçue et prise en charge",
            estimatedTime=7
        )
        return response

    def GetAlertStatus(self, request, context):
        info = alert_status.get(request.alertId, None)

        if not info:
            return urgence_pb2.StatusResponse(status="INCONNUE")

        return urgence_pb2.StatusResponse(status=info["status"])

    def ListenAlerts(self, request, context):
        for alert_id, info in alert_status.items():
            yield urgence_pb2.AlertStream(
                alertId=alert_id,
                type=info["type"],
                status=info["status"]
            )


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    urgence_pb2_grpc.add_UrgenceServiceServicer_to_server(UrgenceService(), server)

    print("🚑 Urgence gRPC server running on port 50051 ...")
    server.add_insecure_port("[::]:50051")
    server.start()
    server.wait_for_termination()

if __name__ == "__main__":
    serve()
