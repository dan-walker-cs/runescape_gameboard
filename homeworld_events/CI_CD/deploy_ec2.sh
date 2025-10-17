#! /bin/bash

# Variables
S3_SOURCE="s3://hwe-app-server-resources/current_version/"
EC2_DEST="/app"
VERSION_INFO_FILE="VERSION_INFO.txt"
BASE_FRONTEND_PREFIX="hwe_webapp"
BASE_BACKEND_PREFIX="hwe-app"

# Assume app role & access working dir
sudo su hwe-admin
cd ${EC2_DEST}

# Stop services
sudo systemctl stop nginx
sudo systemctl stop hwe-app

# Delete outdated application packages
rm *.tar.gz *.jar 
rm -r ${BASE_FRONTEND_PREFIX}

# Copy files from S3 to EC2
aws s3 cp ${S3_SOURCE} ${EC2_DEST} \
    --recursive \
    --exclude "*" \
    --include "*.jar" \
    --include "*.tgz" \
    --include "*.tar" \
    --include "*.tar.gz"

# Extract frontend TAR
tar -xvzf *.tar.gz

# Update version log
rm ${VERSION_INFO_FILE}
echo "VERSION=$(basename ${BASE_FRONTEND_PREFIX}_v*.tar.gz | sed -E 's/.*_(v[0-9.]+)\.tar\.gz/\1/')" > VERSION_INFO.txt

# Strip version from backend package name
mv ${BASE_BACKEND_PREFIX}*.jar ${BASE_BACKEND_PREFIX}.jar

# Give nginx access to webapp [needs to run as root]
sudo chown -R nginx:nginx ${BASE_FRONTEND_PREFIX}

# Start services
sudo systemctl start nginx
sudo systemctl start hwe-app
