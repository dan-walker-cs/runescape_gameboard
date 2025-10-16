#! /bin/bash

# Variables
# Note: Spring package name defined in build.gradle.kts
VERSION="${1:-}"
ANGULAR_PACKAGE_NAME="hwe_webapp_v${VERSION}.tar.gz"
LOG_FILE="build_${VERSION}.log"

# Error & usage info when missing version arg
if [[ -z "$VERSION" ]]; then
  log "Error: version is required." >&2
  log "Usage: $0 <version>" >&2
  exit 2
fi

# save original stdout to FD 3 (the console)
exec 3>&1

# send all normal output (stdout) and errors (stderr) to the log file
exec >"$LOG_FILE" 2>&1

# use this instead of echo when you want console+log
log() {
  # prints args as a single line to both the log file and the console
  printf '%s\n' "$*" | tee -a "$LOG_FILE" >&3
}

# Move previous application packages to archive on AWS S3
log 'Updating AWS S3 archive from current_version..';
aws s3 mv s3://hwe-app-server-resources/current_version/ s3://hwe-app-server-resources/archive/ \
    --recursive \
    --exclude "*" \
    --include "*.jar" \
    --include "*.tgz" \
    --include "*.tar" \
    --include "*.tar.gz"
log 'Complete.';
    

# Build frontend package
log 'Building frontend application package..';
cd ../hwe_webapp
ng build --configuration production
tar -czvf ${ANGULAR_PACKAGE_NAME} \
    --exclude='**/3rdpartylicenses.txt' \
    --exclude='**/prerendered-routes.json' \
    -C dist hwe_webapp
log 'Complete.';

log 'Uploading frontend application package to S3..';
aws s3 cp ${ANGULAR_PACKAGE_NAME} s3://hwe-app-server-resources/current_version/
log 'Complete.';

log 'Cleaning up..';
rm ${ANGULAR_PACKAGE_NAME}
log 'Complete.';

# Build backend package
log 'Building backend application package..';
cd ../hwe-app
./gradlew clean build bootJar -x test -Pversion="${VERSION}"
log 'Complete.';

log 'Uploading backend application package to S3..';
aws s3 cp ./build/libs/ s3://hwe-app-server-resources/current_version/ \
    --recursive \
    --exclude "*-plain.jar"
log 'Complete.';

log 'Uploading external config files to S3..';
aws s3 cp ./src/main/resources/environment/logback-spring-prod.xml s3://hwe-app-server-resources/current_version/
log 'Complete.';

log "Local build of Application Version ${VERSION} completed."