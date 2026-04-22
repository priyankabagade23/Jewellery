#!/bin/bash
set -e

# Fix Docker socket permissions if it exists
if [ -S /var/run/docker.sock ]; then
    chmod 666 /var/run/docker.sock || true
fi

# Fix workspace permissions to ensure jenkins user can build
if [ -d /workspace ]; then
    chown -R jenkins:jenkins /workspace || true
fi

# Fix Jenkins workspace directory permissions
mkdir -p /var/jenkins_home/workspace
chown -R jenkins:jenkins /var/jenkins_home/workspace || true

# Switch to jenkins user and start Jenkins
exec su jenkins -c "/bin/tini -- /usr/local/bin/jenkins.sh"
