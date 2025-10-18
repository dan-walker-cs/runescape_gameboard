#! /bin/bash

# Variables
S3_SOURCE="s3://hwe-app-server-resources/current_version/"
EC2_DEST="/app"
VERSION_INFO_FILE="VERSION_INFO.txt"
BASE_FRONTEND_PREFIX="hwe_webapp"
BASE_BACKEND_PREFIX="hwe-app"
LOG_DIR="/app/log/deploy"

# -- Logging Config --
# Error & usage info when missing version arg
if [[ -z "${1:-}" ]]; then
  echo "Error: version is required." >&2
  echo "Usage: $0 <version>" >&2
  exit 2
fi

# Grab the version argument & normalize
VERSION="v${1#v}"

# Create the logging directory, if it doesn't exist & use the version info in the logfile naming.
mkdir -p "$LOG_DIR"
LOG_FILE="${LOG_DIR}/deploy_${VERSION}.log"

# Send ALL output to both console and the final log file
exec > >(tee -a "$LOG_FILE") 2>&1

# Timestamped banner
echo "============================================================"
echo "Deploy started: $(date -Is)  |  Version: ${VERSION}"
echo "Log file: $LOG_FILE"
echo "============================================================"

# Error trap
trap 'status=$?; echo "ERROR at line $LINENO (exit $status). See $LOG_FILE"; exit $status' ERR

# xx Logging Config xx

# Access working dir
cd ${EC2_DEST}

# Stop services
echo "Stopping services.."
sudo systemctl stop nginx
sudo systemctl stop hwe-app
echo "Complete."

# Delete outdated application packages
echo "Removing outdated application packages.."
rm -f *.tar.gz *.jar 
sudo rm -rf ${BASE_FRONTEND_PREFIX}
echo "Complete."

# Copy files from S3 to EC2
echo "Retrieving deployment files from S3.."
aws s3 cp ${S3_SOURCE} ${EC2_DEST} \
    --recursive \
    --exclude "*" \
    --include "*.jar" \
    --include "*.tgz" \
    --include "*.tar" \
    --include "*.tar.gz"
echo "Complete."

# Extract frontend TAR
echo "Extracting frontend TAR.."
tar -xvzf *.tar.gz
echo "Complete."

# Update version log
echo "Updating version log info.."
rm -f ${VERSION_INFO_FILE}
echo "VERSION=${VERSION}" > VERSION_INFO.txt
echo "Complete."

# Strip version from backend package name
echo "Renaming application packages for NGINX compatibility.."
mv ${BASE_BACKEND_PREFIX}*.jar ${BASE_BACKEND_PREFIX}.jar
echo "Complete."

# Give nginx access to webapp [needs to run as root]
echo "Updating webapp directory permissions.."
sudo chown -R nginx:nginx ${BASE_FRONTEND_PREFIX}
echo "Complete."

# Start services
echo "Starting services.."
sudo systemctl start nginx
sudo systemctl start hwe-app
echo "Complete."

# Timestamped banner
echo "============================================================"
echo "Deploy completed: $(date -Is)  |  Version: ${VERSION}"
echo "============================================================"
