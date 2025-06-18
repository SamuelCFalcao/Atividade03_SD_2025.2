import paho.mqtt.client as mqtt
from collections import deque

fila_temperaturas = deque(maxlen=2)  # armazena as últimas 2 médias
valores_recebidos = []

def calcular_media():
    if valores_recebidos:
        return sum(valores_recebidos) / len(valores_recebidos)
    return 0

def on_message(client, userdata, msg):
    payload = msg.payload.decode()
    sensor_id, temp_str = payload.split(":")
    temp = float(temp_str)
    valores_recebidos.append(temp)

    if len(valores_recebidos) >= 2:
        media = calcular_media()
        fila_temperaturas.append(media)
        print(f"[CAT] Média atual: {media:.2f}")

        if len(fila_temperaturas) == 2:
            diff = abs(fila_temperaturas[1] - fila_temperaturas[0])
            if diff >= 5:
                client.publish("temp/alert/repentina", f"Variação rápida: {diff:.2f}")
        if media >= 200:
            client.publish("temp/alert/alta", f"Média alta: {media:.2f}")

        valores_recebidos.clear()

client = mqtt.Client()
client.on_message = on_message
client.connect("localhost", 1883, 60)
client.subscribe("temperatura/leitura")

print("[CAT] Serviço iniciado. Aguardando dados...")

client.loop_forever()