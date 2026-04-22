FROM jenkins/jenkins:lts-jdk21

USER root

# Install Maven and Docker
RUN apt-get update && \
    apt-get install -y maven docker.io && \
    rm -rf /var/lib/apt/lists/* && \
    getent group docker || groupadd -g 999 docker && \
    usermod -aG docker jenkins

# Create entrypoint script to fix Docker permissions
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
