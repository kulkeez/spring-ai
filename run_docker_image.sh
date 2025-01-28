#!/bin/bash
echo "Launching chatgpt-service Docker Container ..."
sudo docker run --rm --name chatgpt-service -d --net="host" kulkeez/chatgpt-service:0.1
