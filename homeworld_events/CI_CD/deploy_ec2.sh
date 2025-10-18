#! /bin/bash

# Variables
S3_SOURCE="s3://hwe-app-server-resources/current_version/"
EC2_DEST="/app"
VERSION_INFO_FILE="VERSION_INFO.txt"
BASE_FRONTEND_PREFIX="hwe_webapp"
BASE_BACKEND_PREFIX="hwe-app"
TEMP_LOG_FILE="/app/log/deploy/deploy.log"

# -- Logging Config --
# Error & usage info when missing version arg
if [[ -z "${1:-}" ]]; then
  echo "Error: version is required." >&2
  echo "Usage: $0 <version>" >&2
  exit 2
fi

# Grab the version argument & normalize
VERSION="v${1#v}"

# save original stdout to FD 3 (the console)
exec 3>&1

# send all normal output (stdout) and errors (stderr) to the log file
exec >"$TEMP_LOG_FILE" 2>&1

# use this instead of echo when you want console+log
log() {
  # prints args as a single line to both the log file and the console
  printf '%s\n' "$*" | tee -a "$TEMP_LOG_FILE" >&3
}
# xx Logging Config xx

# Access working dir
cd ${EC2_DEST}

# Stop services
log "Stopping services.."
sudo systemctl stop nginx
sudo systemctl stop hwe-app
log "Complete."

# Delete outdated application packages
log "Removing outdated application packages.."
rm *.tar.gz *.jar 
sudo rm -rf ${BASE_FRONTEND_PREFIX}
log "Complete."

# Copy files from S3 to EC2
log "Retrieving deployment files from S3.."
aws s3 cp ${S3_SOURCE} ${EC2_DEST} \
    --recursive \
    --exclude "*" \
    --include "*.jar" \
    --include "*.tgz" \
    --include "*.tar" \
    --include "*.tar.gz"
log "Complete."

# Extract frontend TAR
log "Extracting frontend TAR.."
tar -xvzf *.tar.gz
log "Complete."

# Update version log
log "Updating version log info.."
rm ${VERSION_INFO_FILE}
echo "VERSION=${VERSION}" > VERSION_INFO.txt
log "Complete."

# Strip version from backend package name
log "Renaming application packages for NGINX compatibility.."
mv ${BASE_BACKEND_PREFIX}*.jar ${BASE_BACKEND_PREFIX}.jar
log "Complete."

# Give nginx access to webapp [needs to run as root]
log "Updating webapp directory permissions.."
sudo chown -R nginx:nginx ${BASE_FRONTEND_PREFIX}
log "Complete."

# Start services
log "Starting services.."
sudo systemctl start nginx
sudo systemctl start hwe-app
log "Complete."

# Add version from previous step to this script's logfile
log "Finalizing logfile.."
FINAL_LOG_FILE="/app/log/deploy/deploy_${VERSION}.log"
mv ${TEMP_LOG_FILE} ${FINAL_LOG_FILE}
log "Complete."

log "Remote deployment of Application Version ${VERSION} completed."

# Cleanup
rm ${TEMP_LOG_FILE}
