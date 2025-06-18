import paho.mqtt.client as mqtt
import random
import time

client = mqtt.Client()
client.connect("localhost", 1883, 60)

sensor_id = "sensor_1"

while True:
    temperatura = random.uniform(180, 220)  # valores variando em torno de 200
    payload = f"{sensor_id}:{temperatura:.2f}"
    client.publish("temperatura/leitura", payload)
    print(f"Enviado: {payload}")
    time.sleep(60)  # a cada 60 segundos