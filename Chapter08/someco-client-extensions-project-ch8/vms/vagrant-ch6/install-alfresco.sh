#!/bin/sh

echo "Installing of Alfresco Community 201606"
sudo su -
cd /vagrant

echo "  Installing pre-requisites..."
yum -y install fontconfig libSM libICE libXrender libXext cups-libs libGLU zip nano

if [ ! -f alfresco-community-installer-201612-linux-x64.bin ];
then
  echo "  Downloading... (801M)"
  wget -q http://dl.alfresco.com/release/community/201612-build-00014/alfresco-community-installer-201612-linux-x64.bin
  chmod +x alfresco-community-installer-201612-linux-x64.bin
else
  echo "  Installer already downloaded"
fi

echo "  Installing Alfresco..."
./alfresco-community-installer-201612-linux-x64.bin --optionfile install-options.txt

echo "  Enabling CORS..."
cd /opt/alfresco-community/modules/platform/
if [ ! -f enablecors-1.0.jar ];
then
  wget http://artifacts.alfresco.com/nexus/service/local/repositories/releases/content/org/alfresco/enablecors/1.0/enablecors-1.0.jar
else
  echo "  The CORS module is already installed."
fi

echo "  Installing API Explorer..."
cd /opt/alfresco-community/tomcat/webapps/
if [ ! -f api-explorer.war ];
then
  echo "  Downloading..."
  curl https://artifacts.alfresco.com/nexus/service/local/repositories/releases/content/org/alfresco/api-explorer/1.4/api-explorer-1.4.war > api-explorer.war
else
  echo "  Webapp already installed"
fi

echo "  Enabling Tomcat Console..."
if ! grep -q "manager-gui" "/opt/alfresco-community/tomcat/conf/tomcat-users.xml"; then
  sed -i "s/<\/tomcat-users>/  <role rolename=\"manager-gui\"\/>\n  <user username=\"admin\" password=\"admin\" roles=\"manager-gui\"\/>\n<\/tomcat-users>/" /opt/alfresco-community/tomcat/conf/tomcat-users.xml
fi

echo "  Starting Alfresco..."
service alfresco start