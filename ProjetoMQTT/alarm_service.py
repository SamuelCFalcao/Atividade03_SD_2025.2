import paho.mqtt.client as mqtt
import tkinter as tk
from tkinter import messagebox

def on_message(client, userdata, msg):
    print(f"[ALARME] Tipo: {msg.topic} | Mensagem: {msg.payload.decode()}")

client = mqtt.Client()
client.on_message = on_message
client.connect("localhost", 1883, 60)
client.subscribe("temp/alert/#")

print("[ALARMS] Monitorando alarmes...")

client.loop_forever()

def show_alert(topic, message):
    root = tk.Tk()
    root.withdraw()
    messagebox.showwarning(f"ALERTA: {topic}", message)
    root.destroy()

def on_message(client, userdata, msg):
    alert_msg = f"{msg.payload.decode()} (tópico: {msg.topic})"
    print(f"[ALARME] {alert_msg}")
    show_alert(msg.topic, alert_msg)
