cd biblioteca/
curl -s http://localhost:8080
if [ $? -eq 0 ]; then
    docker compose down > /dev/null
fi

docker compose up -d > /dev/null
if [ $? -eq 0 ]; then
    cd ../cliente/
    sleep 5
    mvn spring-boot:run
fi