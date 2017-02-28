echo "Installing of Alfresco Community 201606"
sudo su -
cd /vagrant

echo "  Installing pre-requisites..."
yum -y install libreoffice zip nano

if [ ! -f alfresco-community-installer-201606-EA-linux-x64.bin ];
then
  echo "  Downloading... (801M)"
  wget http://dl.alfresco.com/release/community/201606-EA-build-00011/alfresco-community-installer-201606-EA-linux-x64.bin
  chmod +x alfresco-community-installer-201606-EA-linux-x64.bin
else
  echo "  Installer already downloaded"
fi

echo "  Installing Alfresco..."
./alfresco-community-installer-201606-EA-linux-x64.bin --optionfile install-options.txt

echo "  Enabling CORS..."
cd /opt/alfresco-community/tomcat/webapps/
if [ ! -d alfresco ];
then
  unzip -q alfresco.war -d alfresco
  sed -i "102s/.*/   <filter>/" alfresco/WEB-INF/web.xml
  sed -i "111s/.*/         <param-value>*<\/param-value>/" alfresco/WEB-INF/web.xml
  sed -i "123s/.*/         <param-value>*<\/param-value>/" alfresco/WEB-INF/web.xml
  sed -i "133s/.*/   <\/filter>/" alfresco/WEB-INF/web.xml
  sed -i "145s/.*/   <filter-mapping>/" alfresco/WEB-INF/web.xml
  sed -i "151s/.*/   <\/filter-mapping>/" alfresco/WEB-INF/web.xml
  cd alfresco
  zip -q -r ../alfresco.war *
else
  echo "  The Alfresco webapp is already expanded."
fi

echo "  Installing API Explorer..."
cd /opt/alfresco-community/tomcat/webapps/
if [ ! -f api-explorer.war ];
then
  echo "  Downloading..."
  curl https://artifacts.alfresco.com/nexus/service/local/repositories/releases/content/org/alfresco/api-explorer/1.1/api-explorer-1.1.war > api-explorer.war
else
  echo "  Webapp already installed"
fi

echo "  Enabling Tomcat Console..."
if ! grep -q "manager-gui" "/opt/alfresco-community/tomcat/conf/tomcat-users.xml"; then
  sed -i "s/<\/tomcat-users>/  <role rolename=\"manager-gui\"\/>\n  <user username=\"admin\" password=\"admin\" roles=\"manager-gui\"\/>\n<\/tomcat-users>/" /opt/alfresco-community/tomcat/conf/tomcat-users.xml
fi

echo "  Starting Alfresco..."
service alfresco start